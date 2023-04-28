package cc.cdtime.lifecapsule_v3_api.web.noteSend;

import cc.cdtime.lifecapsule_v3_api.business.noteSend.INoteSendBService;
import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLogView;
import cc.cdtime.lifecapsule_v3_api.middle.noteSend.INoteSendMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WebNoteSendBService implements IWebNoteSendBService {
    private final INoteSendBService iNoteSendBService;
    private final INoteSendMiddle iNoteSendMiddle;

    public WebNoteSendBService(INoteSendBService iNoteSendBService,
                               INoteSendMiddle iNoteSendMiddle) {
        this.iNoteSendBService = iNoteSendBService;
        this.iNoteSendMiddle = iNoteSendMiddle;
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
        if (out.get("readTime") == null) {
            Map qIn = new HashMap();
            qIn.put("sendLogId", out.get("sendLogId"));
            qIn.put("readTime", new Date());
            iNoteSendMiddle.updateNoteSendLog(qIn);
        }
        return out;
    }

    @Override
    public Map listMyNoteSendOutLog(Map in) throws Exception {
        Map out = iNoteSendBService.listNoteSendLogSend(in);
        return out;
    }

    @Override
    public Map getMyNoteSendOutLog(Map in) throws Exception {
        Map out = iNoteSendBService.getNoteSendLogSender(in);
        return out;
    }
}
