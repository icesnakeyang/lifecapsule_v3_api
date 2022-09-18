package cc.cdtime.lifecapsule_v3_api.business.reply;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmailView;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLog;
import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLogView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.noteSend.INoteSendMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReplyNoteBService implements IReplyNoteBService {
    private final IUserMiddle iUserMiddle;
    private final INoteSendMiddle iNoteSendMiddle;
    private final INoteMiddle iNoteMiddle;

    public ReplyNoteBService(IUserMiddle iUserMiddle,
                             INoteSendMiddle iNoteSendMiddle,
                             INoteMiddle iNoteMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iNoteSendMiddle = iNoteSendMiddle;
        this.iNoteMiddle = iNoteMiddle;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createReplyNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String pType = in.get("pType").toString();
        String pid = in.get("pid").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);
        in.put("userId", userView.getUserId());

        if (pType.equals(ESTags.MY_NOTE_INFO.toString())) {
            /**
             * 回复自己的笔记
             */
            replyMyNote(in);
        } else {
            if (pType.equals(ESTags.NOTE_SEND_LOG.toString())) {
                /**
                 * 回复别人发送给我的笔记
                 */
                NoteSendLogView noteSendLogView = iNoteSendMiddle.getNoteSendLog(pid, false, null);
                in.put("pUserId", noteSendLogView.getSendUserId());
                in.put("sendEmail", userView.getEmail());
                qIn = new HashMap();
                qIn.put("userId", noteSendLogView.getSendUserId());
                UserEmailView userEmailView = iUserMiddle.getUserEmail(qIn, false, null);
                in.put("receiveEmail", userEmailView.getEmail());
                replyNoteSend(in);
            } else {
                if (pType.equals(ESTags.FORUM_NOTE.toString())) {
                    /**
                     * 回复论坛里的笔记
                     */
                    replyForumNote(in);
                }
            }
        }
    }

    private void replyMyNote(Map in) throws Exception {

    }

    private void replyNoteSend(Map in) throws Exception {
        String userId = in.get("userId").toString();
        String sendLogId = in.get("pid").toString();
        String pUserId = in.get("pUserId").toString();
        String content = in.get("content").toString();
        String receiveEmail = in.get("receiveEmail").toString();
        String title = (String) in.get("title");

        /**
         * 回复内容也是一篇笔记，先创建回复笔记
         */
        NoteInfo noteInfo = new NoteInfo();
        noteInfo.setNoteId(GogoTools.UUID32());
        noteInfo.setCreateTime(new Date());
        noteInfo.setTitle(title);
        noteInfo.setEncrypt(0);
        noteInfo.setContent(content);
        noteInfo.setUserId(userId);
        iNoteMiddle.createNoteInfo(noteInfo);

        /**
         * 发送note_send_log
         */
        NoteSendLog noteSendLog = new NoteSendLog();
        noteSendLog.setSendLogId(GogoTools.UUID32());
        noteSendLog.setNoteContent(content);
        noteSendLog.setSendTime(new Date());
        noteSendLog.setSendUserId(userId);
        noteSendLog.setReceiveUserId(pUserId);
        noteSendLog.setTitle(title);
        noteSendLog.setToEmail(receiveEmail);
        noteSendLog.setTriggerType(ESTags.REPLY_SEND_LOG.toString());
        noteSendLog.setRefPid(sendLogId);
        iNoteSendMiddle.createNoteSendLog(noteSendLog);
    }

    private void replyForumNote(Map in) throws Exception {

    }
}
