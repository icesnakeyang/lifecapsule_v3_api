package cc.cdtime.lifecapsule_v3_api.business.noteSend;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLog;
import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLogView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.noteSend.INoteSendMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.security.ISecurityMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class NoteSendBService implements INoteSendBService {
    private final IUserMiddle iUserMiddle;
    private final INoteMiddle iNoteMiddle;
    private final INoteSendMiddle iNoteSendMiddle;
    private final ISecurityMiddle iSecurityMiddle;

    public NoteSendBService(IUserMiddle iUserMiddle,
                            INoteMiddle iNoteMiddle,
                            INoteSendMiddle iNoteSendMiddle,
                            ISecurityMiddle iSecurityMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iNoteSendMiddle = iNoteSendMiddle;
        this.iSecurityMiddle = iSecurityMiddle;
    }

    @Override
    public void sendNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String phone = (String) in.get("phone");
        String email = (String) in.get("email");
        String noteContent = (String) in.get("noteContent");
        String title = (String) in.get("title");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        UserView receiver = null;
        qIn = new HashMap();
        if (phone != null) {
            qIn.put("phone", phone);
            receiver = iUserMiddle.getUser(qIn, true, false);
            if (receiver == null) {
                qIn = new HashMap();
                qIn.put("loginName", phone);
                receiver = iUserMiddle.getUser(qIn, true, false);
            }
        } else {
            if (email != null) {
                qIn.put("email", email);
                receiver = iUserMiddle.getUser(qIn, true, false);
                if (receiver == null) {
                    qIn = new HashMap();
                    qIn.put("loginName", email);
                    receiver = iUserMiddle.getUser(qIn, true, false);
                }
            }
        }

        NoteSendLog noteSendLog = new NoteSendLog();
        noteSendLog.setSendLogId(GogoTools.UUID32());
        noteSendLog.setSendTime(new Date());
        noteSendLog.setSendUserId(userView.getUserId());
        if (receiver != null) {
            noteSendLog.setReceiveUserId(receiver.getUserId());
        }
        noteSendLog.setSendPhone(phone);
        noteSendLog.setSendEmail(email);
        noteSendLog.setNoteContent(noteContent);
        noteSendLog.setTitle(title);
        noteSendLog.setTriggerType(ESTags.INSTANT_MESSAGE.toString());
        iNoteSendMiddle.createNoteSendLog(noteSendLog);
    }

    @Override
    public Map searchUser(Map in) throws Exception {
        String token = in.get("token").toString();
        String searchKey = in.get("searchKey").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
//        qIn.put("phone", searchKey);
//        qIn.put("email", searchKey);
        qIn.put("loginName", searchKey);
        ArrayList<UserView> userViews = iUserMiddle.listUser(qIn);

        Map out = new HashMap();
        out.put("users", userViews);

        return out;
    }

    @Override
    public Map loadMyNoteSendStatistic(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = (String) in.get("noteId");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Map out = new HashMap();

        //我收到的笔记总数
        qIn = new HashMap();
        qIn.put("receiveUserId", userView.getUserId());
        qIn.put("noteId", noteId);
        Integer totalReceive = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("totalReceive", totalReceive);
        //收到未读总数
        qIn.put("unread", true);
        Integer totalReceiveUnread = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("totalReceiveUnread", totalReceiveUnread);

        //我发送的笔记总数
        qIn = new HashMap();
        qIn.put("sendUserId", userView.getUserId());
        qIn.put("noteId", noteId);
        Integer totalSend = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("totalSend", totalSend);
        //我发送对方未读
        qIn.put("unread", true);
        Integer totalSendUnread = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("totalSendUnread", totalSendUnread);

        return out;
    }

    @Override
    public Map listNoteReceiveLog(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = (String) in.get("noteId");
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Map out = new HashMap();

        //我收到的笔记列表
        qIn = new HashMap();
        qIn.put("receiveUserId", userView.getUserId());
        qIn.put("noteId", noteId);
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        ArrayList<NoteSendLogView> views = iNoteSendMiddle.listNoteSendLog(qIn);
        Integer total = iNoteSendMiddle.totalNoteSendLog(qIn);
        ArrayList logs = new ArrayList();
//        for(int i=0;i<views.size();i++){
//            Map log=new HashMap();
//            log.put("title", views.get(i).getTitle());
//            log.put("sendUserId", views.get(i).getSendUserId());
//            qIn=new HashMap();
//            qIn.put("userId", views.get(i).getSendUserId());
//            UserView sender=iUserMiddle.getUser(qIn, false,false);
//            log.put("sendUserId", sender.getUserId());
//            log.put("sendUserNickname", sender.getNickname());
//            log.put("sendUserEmail", sender.getEmail());
//            log.put("sendTime", views.get(i).getSendTime());
//            log.put("readTime", views.get(i).getReadTime());
//            logs.add(log);
//        }
        out.put("receiveNoteList", views);
        out.put("totalReceiveNote", total);
        //收到未读总数
        qIn.put("unread", true);
        Integer totalReceiveUnread = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("totalReceiveNoteUnread", totalReceiveUnread);

        return out;
    }

    /**
     * App端用户读取自己发送的笔记列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map listNoteSendLogSend(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Map out = new HashMap();

        //我发送的笔记列表
        qIn = new HashMap();
        qIn.put("sendUserId", userView.getUserId());
        ArrayList<NoteSendLogView> views = iNoteSendMiddle.listNoteSendLog(qIn);
        Integer total = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("sendNoteList", views);
        out.put("totalSendNote", total);
        //未读总数
        qIn.put("unread", true);
        Integer totalSendNoteUnread = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("totalSendNoteUnread", totalSendNoteUnread);

        return out;
    }

    @Override
    public Map getNoteSendLog(Map in) throws Exception {
        String token = in.get("token").toString();
        String sendLogId = (String) in.get("sendLogId");

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

        Map out = new HashMap();

        //我收到的笔记列表
        NoteSendLogView noteSendLogView = iNoteSendMiddle.getNoteSendLog(sendLogId, false, userView.getUserId());
        if (noteSendLogView.getTriggerType() != null) {
            if (noteSendLogView.getTriggerType().equals(ESTags.TIMER_TYPE_PRIMARY.toString()) ||
                    noteSendLogView.getTriggerType().equals(ESTags.TIMER_TYPE_DATETIME.toString())) {
                if (noteSendLogView.getUserEncodeKey() != null) {
                    //用AES秘钥加密笔记内容的AES秘钥
                    String outCode = GogoTools.encryptAESKey(noteSendLogView.getUserEncodeKey(), strAESKey);
                    noteSendLogView.setUserEncodeKey(outCode);
                }
            }
        }
        out.put("noteSendLog", noteSendLogView);

        return out;
    }

    @Override
    public void deleteSendNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String sendLogId = in.get("sendLogId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteSendLogView noteSendLogView = iNoteSendMiddle.getNoteSendLog(sendLogId, false, userView.getUserId());

        iNoteSendMiddle.deleteNoteSendLog(sendLogId);
    }
}
