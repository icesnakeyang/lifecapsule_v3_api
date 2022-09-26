package cc.cdtime.lifecapsule_v3_api.business.inbox.reply;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLogView;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.NoteTrigger;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.noteSend.INoteSendMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.security.ISecurityMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.trigger.ITriggerMiddle;
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
    private final ISecurityMiddle iSecurityMiddle;
    private final ITriggerMiddle iTriggerMiddle;

    public ReplyNoteBService(IUserMiddle iUserMiddle,
                             INoteSendMiddle iNoteSendMiddle,
                             INoteMiddle iNoteMiddle,
                             ISecurityMiddle iSecurityMiddle,
                             ITriggerMiddle iTriggerMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iNoteSendMiddle = iNoteSendMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iSecurityMiddle = iSecurityMiddle;
        this.iTriggerMiddle = iTriggerMiddle;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void replyReceiveNote(Map in) throws Exception {
        String token = in.get("token").toString();
//        String pType = in.get("pType").toString();
        String pid = in.get("pid").toString();
        String keyToken = in.get("keyToken").toString();
        String encryptKey = in.get("encryptKey").toString();
        String title = in.get("title").toString();
        String content = in.get("content").toString();

        /**
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String strAESKey = null;
        String privateKey = iSecurityMiddle.getRSAKey(keyToken);
        strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);
        iSecurityMiddle.deleteRSAKey(keyToken);

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * 读取要回复的父笔记，note_send_log
         */
        NoteSendLogView noteSendLogView = iNoteSendMiddle.getNoteSendLog(pid, false, userView.getUserId());

        /**
         * 回复内容也是一篇笔记，先创建回复笔记
         */
        NoteInfo noteInfo = new NoteInfo();
        noteInfo.setNoteId(GogoTools.UUID32());
        noteInfo.setCreateTime(new Date());
        noteInfo.setTitle(title);
        noteInfo.setEncrypt(1);
        noteInfo.setContent(content);
        noteInfo.setUserEncodeKey(strAESKey);
        noteInfo.setUserId(userView.getUserId());
        iNoteMiddle.createNoteInfo(noteInfo);

        /**
         * 创建trigger
         */
        NoteTrigger noteTrigger = new NoteTrigger();
        noteTrigger.setTriggerId(GogoTools.UUID32());
        noteTrigger.setCreateTime(new Date());
        noteTrigger.setUserId(userView.getUserId());
        noteTrigger.setTriggerType(ESTags.INSTANT_MESSAGE.toString());
        noteTrigger.setStatus(ESTags.ACTIVE.toString());
        noteTrigger.setTitle(title);
        noteTrigger.setNoteId(noteInfo.getNoteId());
        noteTrigger.setFromName(noteSendLogView.getToEmail());
        noteTrigger.setToUserId(noteSendLogView.getSendUserId());
        noteTrigger.setRefPid(pid);
        noteTrigger.setUserEncodeKey(strAESKey);
        noteTrigger.setNoteContent(content);
        iTriggerMiddle.createTrigger(noteTrigger);
    }
}
