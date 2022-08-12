package cc.cdtime.lifecapsule_v3_api.app.noteSend;

import cc.cdtime.lifecapsule_v3_api.business.noteSend.INoteSendBService;
import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLogView;
import cc.cdtime.lifecapsule_v3_api.middle.noteSend.INoteSendMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AppNoteSendBService implements IAppNoteSendBService {
    private final INoteSendBService iNoteSendBService;
    private final INoteSendMiddle iNoteSendMiddle;

    public AppNoteSendBService(INoteSendBService iNoteSendBService,
                               INoteSendMiddle iNoteSendMiddle) {
        this.iNoteSendBService = iNoteSendBService;
        this.iNoteSendMiddle = iNoteSendMiddle;
    }

    @Override
    public void sendNote(Map in) throws Exception {
        iNoteSendBService.sendNote(in);
    }

    @Override
    public Map searchUser(Map in) throws Exception {
        Map out = iNoteSendBService.searchUser(in);
        return out;
    }

    @Override
    public Map loadMyNoteSendStatistic(Map in) throws Exception {
        Map out = iNoteSendBService.loadMyNoteSendStatistic(in);
        return out;
    }

    @Override
    public Map listMyNoteReceiveLog(Map in) throws Exception {
        Map out = iNoteSendBService.listNoteReceiveLog(in);
        return out;
    }

    @Override
    public Map getMyReceiveNote(Map in) throws Exception {
        Map out = iNoteSendBService.getNoteSendLog(in);
        /**
         * 设置阅读时间
         */
        NoteSendLogView noteSendLogView = (NoteSendLogView) out.get("noteSendLog");
        if (noteSendLogView != null) {
            if (noteSendLogView.getReadTime() == null) {
                Map qIn = new HashMap();
                qIn.put("sendLogId", noteSendLogView.getSendLogId());
                qIn.put("readTime", new Date());
                iNoteSendMiddle.updateNoteSendLog(qIn);
            }
        }
        return out;
    }

    @Override
    public Map listMyNoteSendLogSend(Map in) throws Exception {
        Map out = iNoteSendBService.listNoteSendLogSend(in);
        return out;
    }

    @Override
    public void deleteMySendNote(Map in) throws Exception {
        iNoteSendBService.deleteSendNote(in);
    }
}
