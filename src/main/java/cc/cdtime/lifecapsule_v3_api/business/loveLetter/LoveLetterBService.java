package cc.cdtime.lifecapsule_v3_api.business.loveLetter;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoveLetterBService implements ILoveLetterBService {
    private final IUserMiddle iUserMiddle;
    private final INoteMiddle iNoteMiddle;
    private final ISecurityMiddle iSecurityMiddle;
    private final ITriggerMiddle iTriggerMiddle;

    public LoveLetterBService(IUserMiddle iUserMiddle,
                              INoteMiddle iNoteMiddle,
                              ISecurityMiddle iSecurityMiddle,
                              ITriggerMiddle iTriggerMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iSecurityMiddle = iSecurityMiddle;
        this.iTriggerMiddle = iTriggerMiddle;
    }

    @Override
    public Map listLoveLetter(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("noteType", ESTags.LOVE_LETTER);
        qIn.put("userId", userView.getUserId());
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        ArrayList<NoteView> noteViews = iNoteMiddle.listNote(qIn);

        Map out = new HashMap();
        out.put("noteList", noteViews);

        return out;
    }

    @Override
    public Map getLoveLetter(Map in) throws Exception {
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

        qIn = new HashMap();
        qIn.put("noteId", noteId);
        TriggerView triggerView = iTriggerMiddle.getTrigger(qIn, true, userView.getUserId());
        if (triggerView != null) {
            if (triggerView.getToEmail() != null) {
                noteMap.put("toEmail", triggerView.getToEmail());
            }
            if (triggerView.getToName() != null) {
                noteMap.put("toName", triggerView.getToName());
            }
            if (triggerView.getFromName() != null) {
                noteMap.put("fromName", triggerView.getFromName());
            }
            if (triggerView.getTriggerTime() != null) {
                noteMap.put("triggerTime", triggerView.getTriggerTime());
            }
            noteMap.put("triggerType", triggerView.getTriggerType());
        }

        Map out = new HashMap();
        out.put("note", noteMap);


        return out;
    }

    @Override
    public void saveLoveLetter(Map in) throws Exception {
        String token = in.get("token").toString();
        String content = in.get("content").toString();
        String toEmail = (String) in.get("toEmail");
        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");
        String title = in.get("title").toString();
        String toName = (String) in.get("toName");
        String fromName = (String) in.get("fromName");
        String noteId = (String) in.get("noteId");
        Date sendDateTime = (Date) in.get("sendDateTime");

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

        if (toEmail != null) {
            toEmail = toEmail.toLowerCase();
        }

        if (noteId == null) {
            /**
             * 创建笔记
             */
            NoteInfo noteInfo = new NoteInfo();
            noteInfo.setNoteType(ESTags.LOVE_LETTER.toString());
            noteId = GogoTools.UUID32();
            noteInfo.setNoteId(noteId);
            noteInfo.setContent(content);
            noteInfo.setCreateTime(new Date());
            noteInfo.setUserId(userView.getUserId());
            noteInfo.setStatus(ESTags.ACTIVE.toString());
            noteInfo.setUserEncodeKey(strAESKey);
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
         * 查询当前noteId，是否有trigger
         * 一封情书只能有一个trigger
         * 创建trigger
         */
        if (toEmail != null && !toEmail.equals("")) {
            qIn = new HashMap();
            qIn.put("noteId", noteId);
            qIn.put("userId", userView.getUserId());
            TriggerView triggerView = iTriggerMiddle.getTrigger(qIn, true, null);
            if (triggerView != null) {
                //发送人不变
                qIn = new HashMap();
                qIn.put("toEmail", toEmail);
                if (toName != null) {
                    //修改toName
                    if (triggerView.getToName() == null) {
                        qIn.put("toName", toName);
                    } else {
                        if (!toName.equals(triggerView.getToName())) {
                            qIn.put("toName", toName);
                        }
                    }
                }
                if (fromName != null) {
                    //修改fromName
                    if (triggerView.getFromName() == null) {
                        qIn.put("fromName", fromName);
                    } else {
                        if (!fromName.equals(triggerView.getFromName())) {
                            qIn.put("fromName", fromName);
                        }
                    }
                }
                if (sendDateTime != null) {
                    qIn.put("triggerTime", sendDateTime);
                }
                if (qIn.size() > 0) {
                    qIn.put("triggerId", triggerView.getTriggerId());
                    iTriggerMiddle.updateNoteTrigger(qIn);
                }
            } else {
                /**
                 * 该toEmail还没有设置trigger，添加一个
                 */
                NoteTrigger noteTrigger = new NoteTrigger();
                noteTrigger.setTriggerId(GogoTools.UUID32());
                noteTrigger.setNoteId(noteId);
                noteTrigger.setCreateTime(new Date());
                if (sendDateTime != null) {
                    noteTrigger.setTriggerType(ESTags.TIMER_TYPE_DATETIME.toString());
                } else {
                    noteTrigger.setTriggerType(ESTags.TIMER_TYPE_PRIMARY.toString());
                }
                noteTrigger.setUserEncodeKey(strAESKey);
                noteTrigger.setToEmail(toEmail);
                noteTrigger.setToName(toName);
                noteTrigger.setStatus(ESTags.ACTIVE.toString());
                noteTrigger.setFromName(fromName);
                noteTrigger.setNoteContent(content);
                noteTrigger.setUserId(userView.getUserId());
                noteTrigger.setTitle(title);
                noteTrigger.setTriggerTime(sendDateTime);
                iTriggerMiddle.createTrigger(noteTrigger);
            }
        }
    }

    @Override
    public void deleteLoveLetter(Map in) throws Exception {
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
}
