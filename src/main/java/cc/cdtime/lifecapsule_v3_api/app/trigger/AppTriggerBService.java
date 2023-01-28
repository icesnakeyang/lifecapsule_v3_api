package cc.cdtime.lifecapsule_v3_api.app.trigger;

import cc.cdtime.lifecapsule_v3_api.business.noteSend.INoteSendBService;
import cc.cdtime.lifecapsule_v3_api.business.trigger.ITriggerBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppTriggerBService implements IAppTriggerBService {
    private final ITriggerBService iTriggerBService;
    private final INoteSendBService iNoteSendBService;

    public AppTriggerBService(ITriggerBService iTriggerBService,
                              INoteSendBService iNoteSendBService) {
        this.iTriggerBService = iTriggerBService;
        this.iNoteSendBService = iNoteSendBService;
    }

    @Override
    public void createTriggerDatetime(Map in) throws Exception {
        iTriggerBService.createNoteTriggerByDatetime(in);
    }

    @Override
    public Map listMyTrigger(Map in) throws Exception {
        Map out = iTriggerBService.listNoteTrigger(in);
        return out;
    }

    @Override
    public Map getMyTriggerDetail(Map in) throws Exception {
        Map out = iTriggerBService.getTriggerDetail(in);
        return out;
    }

    @Override
    public void deleteMyNoteTrigger(Map in) throws Exception {
        iTriggerBService.deleteNoteTrigger(in);
    }

    @Override
    public void createTriggerInstant(Map in) throws Exception {
        iTriggerBService.createTriggerInstant(in);
    }

    @Override
    public void createTriggerPrimary(Map in) throws Exception {
        iTriggerBService.createNoteTriggerPrimary(in);
    }

    @Override
    public Map getTriggerIdFromSendLog(Map in) throws Exception {
        Map out = iNoteSendBService.getTriggerIdFromSendLog(in);
        return out;
    }
}
