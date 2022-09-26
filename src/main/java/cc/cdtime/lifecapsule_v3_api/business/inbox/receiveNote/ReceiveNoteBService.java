package cc.cdtime.lifecapsule_v3_api.business.inbox.receiveNote;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLogView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
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
public class ReceiveNoteBService implements IReceiveNoteBService {
    private final IUserMiddle iUserMiddle;
    private final INoteSendMiddle iNoteSendMiddle;
    private final ISecurityMiddle iSecurityMiddle;

    public ReceiveNoteBService(IUserMiddle iUserMiddle,
                               INoteSendMiddle iNoteSendMiddle,
                               ISecurityMiddle iSecurityMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iNoteSendMiddle = iNoteSendMiddle;
        this.iSecurityMiddle = iSecurityMiddle;
    }

    @Override
    public Map listReceiveNote(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Integer offset = (pageIndex - 1) * pageSize;
        qIn = new HashMap();
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        qIn.put("receiveUserId", userView.getUserId());
        ArrayList<NoteSendLogView> noteSendLogViews = iNoteSendMiddle.listNoteSendLog(qIn);
        Integer total = iNoteSendMiddle.totalNoteSendLog(qIn);
        qIn.put("unread", true);
        Integer totalUnread = iNoteSendMiddle.totalNoteSendLog(qIn);

        Map out = new HashMap();
        out.put("receiveNoteList", noteSendLogViews);
        out.put("totalReceiveNote", total);
        out.put("unreadReceiveNote", totalUnread);

        return out;
    }

    @Override
    public Map getMyReceiveNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");
        String sendLogId = in.get("sendLogId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteSendLogView noteSendLogView = iNoteSendMiddle.getNoteSendLog(sendLogId, false, userView.getUserId());

        if (noteSendLogView.getUserEncodeKey() != null) {
            /**
             * 获取用户临时上传的加密笔记AES秘钥的AES秘钥
             */
            String strAESKey = null;
            if (keyToken != null) {
                strAESKey = iSecurityMiddle.takeNoteAES(keyToken, encryptKey);
                //用AES秘钥加密笔记内容的AES秘钥
                String outCode = GogoTools.encryptAESKey(noteSendLogView.getUserEncodeKey(), strAESKey);
                noteSendLogView.setUserEncodeKey(outCode);
            }
        }

        /**
         * 设置阅读时间
         */
        if (noteSendLogView.getReadTime() == null) {
            if (noteSendLogView.getReceiveUserId().equals(userView.getUserId())) {
                qIn = new HashMap();
                Date readTime = new Date();
                qIn.put("readTime", readTime);
                qIn.put("sendLogId", sendLogId);
                iNoteSendMiddle.updateNoteSendLog(qIn);
                noteSendLogView.setReadTime(readTime);
            }
        }

        Map out = new HashMap();
        out.put("receiveNote", noteSendLogView);

        return out;
    }
}
