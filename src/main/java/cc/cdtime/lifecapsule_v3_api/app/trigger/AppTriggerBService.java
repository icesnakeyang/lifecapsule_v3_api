package cc.cdtime.lifecapsule_v3_api.app.trigger;

import cc.cdtime.lifecapsule_v3_api.business.trigger.ITriggerBService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppTriggerBService implements IAppTriggerBService {
    private final ITriggerBService iTriggerBService;

    public AppTriggerBService(ITriggerBService iTriggerBService) {
        this.iTriggerBService = iTriggerBService;
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
}
