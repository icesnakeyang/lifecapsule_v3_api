package cc.cdtime.lifecapsule_v3_api.middle.noteSend;

import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLog;
import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLogView;
import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.service.INoteSendService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class NoteSendMiddle implements INoteSendMiddle {
    private final INoteSendService iNoteSendService;

    public NoteSendMiddle(INoteSendService iNoteSendService) {
        this.iNoteSendService = iNoteSendService;
    }

    @Override
    public void createNoteSendLog(NoteSendLog noteSendLog) throws Exception {
        iNoteSendService.createNoteSendLog(noteSendLog);
    }

    @Override
    public ArrayList<NoteSendLogView> listNoteSendLog(Map qIn) throws Exception {
        ArrayList<NoteSendLogView> noteSendLogViews = iNoteSendService.listNoteSendLog(qIn);
        return noteSendLogViews;
    }

    @Override
    public Integer totalNoteSendLog(Map qIn) throws Exception {
        Integer total = iNoteSendService.totalNoteSendLog(qIn);
        return total;
    }

    @Override
    public NoteSendLogView getNoteSendLog(String sendLogId) throws Exception {
        NoteSendLogView noteSendLogView = iNoteSendService.getNoteSendLog(sendLogId);
        return noteSendLogView;

    }
}
