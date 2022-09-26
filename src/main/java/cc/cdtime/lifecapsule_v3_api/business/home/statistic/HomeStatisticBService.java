package cc.cdtime.lifecapsule_v3_api.business.home.statistic;

import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.noteSend.INoteSendMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HomeStatisticBService implements IHomeStatisticBService{
    private final IUserMiddle iUserMiddle;
    private final INoteSendMiddle iNoteSendMiddle;

    public HomeStatisticBService(IUserMiddle iUserMiddle,
                                 INoteSendMiddle iNoteSendMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iNoteSendMiddle = iNoteSendMiddle;
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
        Integer totalReceive = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("totalReceive", totalReceive);
        //收到未读总数
        qIn.put("unread", true);
        Integer totalReceiveUnread = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("totalReceiveUnread", totalReceiveUnread);

        //我发送的笔记总数
        qIn = new HashMap();
        qIn.put("sendUserId", userView.getUserId());
        Integer totalSend = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("totalSend", totalSend);
        //我发送对方未读
        qIn.put("unread", true);
        Integer totalSendUnread = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("totalSendUnread", totalSendUnread);

        return out;
    }
}
