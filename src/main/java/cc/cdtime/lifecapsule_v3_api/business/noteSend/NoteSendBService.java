package cc.cdtime.lifecapsule_v3_api.business.noteSend;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLog;
import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLogView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.noteSend.INoteSendMiddle;
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

    public NoteSendBService(IUserMiddle iUserMiddle, INoteMiddle iNoteMiddle, INoteSendMiddle iNoteSendMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iNoteSendMiddle = iNoteSendMiddle;
    }

    @Override
    public void sendNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String receiveUserId = in.get("receiveUserId").toString();
        String noteContent = in.get("noteContent").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        UserView receiver = iUserMiddle.getUser(qIn, true, false);
        if (receiver == null) {
            //发送的用户不存在
            throw new Exception("10038");
        }

        NoteSendLog noteSendLog = new NoteSendLog();
        noteSendLog.setSendLogId(GogoTools.UUID32());
        noteSendLog.setSendTime(new Date());
        noteSendLog.setSendUserId(userView.getUserId());
        noteSendLog.setReceiveUserId(receiveUserId);
        noteSendLog.setNoteContent(noteContent);
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

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Map out = new HashMap();

        //我收到的笔记列表
        qIn = new HashMap();
        qIn.put("receiveUserId", userView.getUserId());
        qIn.put("noteId", noteId);
        ArrayList<NoteSendLogView> views = iNoteSendMiddle.listNoteSendLog(qIn);
        Integer total = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("receiveNoteList", views);
        out.put("totalReceiveNote", total);
        //收到未读总数
        qIn.put("unread", true);
        Integer totalReceiveUnread = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("totalReceiveNoteUnread", totalReceiveUnread);

        return out;
    }

    @Override
    public Map getNoteSendLog(Map in) throws Exception {
        String token = in.get("token").toString();
        String sendLogId = (String) in.get("sendLogId");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Map out = new HashMap();

        //我收到的笔记列表
        NoteSendLogView noteSendLogView = iNoteSendMiddle.getNoteSendLog(sendLogId);
        out.put("noteSendLog", noteSendLogView);

        return out;
    }
}
