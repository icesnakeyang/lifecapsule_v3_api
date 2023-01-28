package cc.cdtime.lifecapsule_v3_api.business.future;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmailView;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.NoteTrigger;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.TriggerView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.security.ISecurityMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.trigger.ITriggerMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class FutureBService implements IFutureBService {
    private final IUserMiddle iUserMiddle;
    private final INoteMiddle iNoteMiddle;
    private final ISecurityMiddle iSecurityMiddle;
    private final ITriggerMiddle iTriggerMiddle;

    public FutureBService(IUserMiddle iUserMiddle,
                          INoteMiddle iNoteMiddle,
                          ISecurityMiddle iSecurityMiddle,
                          ITriggerMiddle iTriggerMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iSecurityMiddle = iSecurityMiddle;
        this.iTriggerMiddle = iTriggerMiddle;
    }

    @Override
    public Map listFuture(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        qIn.put("noteType", ESTags.FUTURE);
        qIn.put("userId", userView.getUserId());
        ArrayList<NoteView> noteViews = iNoteMiddle.listNote(qIn);

        Map out = new HashMap();
        out.put("noteList", noteViews);

        return out;
    }

    @Override
    public Map getFuture(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();
        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");

        /**
         * 获取用户临时上传的加密笔记AES秘钥的AES秘钥
         */
        String strAESKey = null;
        if (keyToken != null) {
            strAESKey = iSecurityMiddle.takeNoteAES(keyToken, encryptKey);
        }

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteDetail(noteId, false, userView.getUserId());

        Map noteMap = new HashMap();

        /**
         * 读取userEncodeKey
         */
        if (noteView.getUserEncodeKey() != null) {
            if (strAESKey == null) {
                //查询秘钥错误
                throw new Exception("10037");
            }
            String data = noteView.getUserEncodeKey();
            //用AES秘钥加密笔记内容的AES秘钥
            String outCode = GogoTools.encryptAESKey(data, strAESKey);
            noteMap.put("userEncodeKey", outCode);
//            noteView.setUserEncodeKey(outCode);
        } else {
            noteMap.put("userEncodeKey", null);
//            noteView.setUserEncodeKey(null);
        }
        noteMap.put("content", noteView.getContent());
        noteMap.put("title", noteView.getTitle());
        noteMap.put("noteId", noteView.getNoteId());
        noteMap.put("createTime", noteView.getCreateTime());

        Map out = new HashMap();
        out.put("note", noteMap);

        /**
         * 读取trigger
         */
        qIn=new HashMap();
        qIn.put("noteId", noteId);
        TriggerView triggerView=iTriggerMiddle.getTrigger(qIn, true,userView.getUserId());
        if(triggerView!=null){
            Map triggerMap=new HashMap();
            triggerMap.put("triggerId", triggerView.getTriggerId());
            triggerMap.put("triggerType", triggerView.getTriggerType());
            triggerMap.put("triggerTime", triggerView.getTriggerTime());
            triggerMap.put("toEmail", triggerView.getToEmail());
            triggerMap.put("toName", triggerView.getToName());
            triggerMap.put("fromName", triggerView.getFromName());
            out.put("trigger", triggerMap);
        }

        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveFuture(Map in) throws Exception {
        String token = in.get("token").toString();
        String content = in.get("content").toString();
        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");
        String title = (String) in.get("title");
        String noteId = (String) in.get("noteId");
        String toEmail = (String) in.get("toEmail");
        String toName = (String) in.get("toName");
        String fromName = (String) in.get("fromName");
        Date sendTime = (Date) in.get("sendTime");

        /**
         * 查看当前用户信息
         */
        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String strAESKey = null;
        String privateKey = iSecurityMiddle.getRSAKey(keyToken);
        strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);
        iSecurityMiddle.deleteRSAKey(keyToken);

        String newNoteId = null;

        /**
         * 创建或者修改笔记
         */
        if (noteId == null) {
            /**
             * 创建笔记
             */
            NoteInfo noteInfo = new NoteInfo();
            noteInfo.setNoteType(ESTags.FUTURE.toString());
            newNoteId = GogoTools.UUID32();
            noteInfo.setNoteId(newNoteId);
            noteInfo.setContent(content);
            noteInfo.setCreateTime(new Date());
            noteInfo.setUserId(userView.getUserId());
            noteInfo.setStatus(ESTags.ACTIVE.toString());
            noteInfo.setUserEncodeKey(strAESKey);
            if (title == null) {
                title = content.substring(0, 50);
            }
            noteInfo.setTitle(title);

            iNoteMiddle.createNoteInfo(noteInfo);
        } else {
            /**
             * 修改笔记
             */
            NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());
            qIn = new HashMap();
            qIn.put("noteId", noteId);
            qIn.put("title", title);
            qIn.put("content", content);
            qIn.put("encodeKey", strAESKey);
            iNoteMiddle.updateNoteInfo(qIn);
        }

        /**
         * 创建或者修改触发器
         */
        if (toEmail != null && !toEmail.equals("") && sendTime!=null) {
            if (noteId == null) {
                /**
                 * 创建trigger
                 */
                NoteTrigger noteTrigger = new NoteTrigger();
                noteTrigger.setTriggerId(GogoTools.UUID32());
                noteTrigger.setToEmail(toEmail);
                noteTrigger.setFromName(fromName);
                noteTrigger.setUserEncodeKey(strAESKey);
                noteTrigger.setNoteContent(content);
                noteTrigger.setTriggerTime(sendTime);
                noteTrigger.setUserId(userView.getUserId());
                noteTrigger.setTitle(title);
                noteTrigger.setNoteId(newNoteId);
                saveTrigger(noteTrigger);
            } else {
                /**
                 * 检查是否有trigger，没有创建，有就修改
                 */
                qIn = new HashMap();
                qIn.put("noteId", noteId);
                TriggerView triggerView = iTriggerMiddle.getTrigger(qIn, true, userView.getUserId());
                if (triggerView == null) {
                    /**
                     * 创建trigger
                     */
                    NoteTrigger noteTrigger = new NoteTrigger();
                    noteTrigger.setTriggerId(GogoTools.UUID32());
                    noteTrigger.setToEmail(toEmail);
                    noteTrigger.setFromName(fromName);
                    noteTrigger.setUserEncodeKey(strAESKey);
                    noteTrigger.setNoteContent(content);
                    noteTrigger.setTriggerTime(sendTime);
                    noteTrigger.setUserId(userView.getUserId());
                    noteTrigger.setTitle(title);
                    noteTrigger.setToName(toName);
                    noteTrigger.setNoteId(noteId);
                    saveTrigger(noteTrigger);
                } else {
                    /**
                     * 修改trigger
                     */
                    triggerView.setTriggerTime(sendTime);
                    triggerView.setUserEncodeKey(strAESKey);
                    triggerView.setNoteContent(content);
                    triggerView.setToEmail(toEmail);
                    triggerView.setToName(toName);
                    triggerView.setFromName(fromName);
                    triggerView.setTitle(title);
                    iTriggerMiddle.updateNoteTrigger2(triggerView);
                }
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteFuture(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteDetail(noteId, false, userView.getUserId());

        iNoteMiddle.deleteNote(noteView.getNoteId());
        qIn = new HashMap();
        qIn.put("noteId", noteId);
        TriggerView triggerView = iTriggerMiddle.getTrigger(qIn, true, userView.getUserId());
        if (triggerView != null) {
            iTriggerMiddle.deleteTrigger(triggerView.getTriggerId());
        }
    }

    @Override
    public Map listFutureTrigger(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        qIn.put("noteId", noteId);
        ArrayList<TriggerView> triggerViews = iTriggerMiddle.listTrigger(qIn);

        Map out = new HashMap();
        out.put("triggerList", triggerViews);

        return out;
    }

    private void saveTrigger(NoteTrigger noteTrigger) throws Exception {
        noteTrigger.setTriggerType(ESTags.TIMER_TYPE_DATETIME.toString());
        noteTrigger.setCreateTime(new Date());
        noteTrigger.setStatus(ESTags.ACTIVE.toString());
        Map qIn = new HashMap();
        qIn.put("email", noteTrigger.getToEmail());
        UserEmailView userEmailView = iUserMiddle.getUserEmail(qIn, true, null);
        if (userEmailView == null) {
        } else {
            noteTrigger.setToUserId(userEmailView.getUserId());
        }
        iTriggerMiddle.createTrigger(noteTrigger);
    }
}
