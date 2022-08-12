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
    public NoteSendLogView getNoteSendLog(String sendLogId, Boolean returnNull, String userId) throws Exception {
        NoteSendLogView noteSendLogView = iNoteSendService.getNoteSendLog(sendLogId);
        if (noteSendLogView == null) {
            if (returnNull) {
                return null;
            }
            //发送的笔记不存在
            throw new Exception("10039");
        }
        if (userId != null) {
            if (!noteSendLogView.getSendUserId().equals(userId) &&
                    !noteSendLogView.getReceiveUserId().equals(userId)) {
                //当前用户既不是发送方，也不是接收方
                throw new Exception("10040");
            }
        }
        return noteSendLogView;
    }

    @Override
    public void updateNoteSendLog(Map qIn) throws Exception {
        iNoteSendService.updateNoteSendLog(qIn);
    }

    @Override
    public void deleteNoteSendLog(String sendLogId) throws Exception {
        iNoteSendService.deleteNoteSendLog(sendLogId);
    }
}
