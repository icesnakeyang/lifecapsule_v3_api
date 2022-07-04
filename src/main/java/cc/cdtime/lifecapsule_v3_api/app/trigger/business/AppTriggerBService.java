package cc.cdtime.lifecapsule_v3_api.app.trigger.business;

import cc.cdtime.lifecapsule_v3_api.business.trigger.ITriggerBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppTriggerBService implements IAppTriggerBService {
    private final ITriggerBService iTriggerBService;

    public AppTriggerBService(ITriggerBService iTriggerBService) {
        this.iTriggerBService = iTriggerBService;
    }

    @Override
    public Map listNoteTrigger(Map in) throws Exception {
        Map out = iTriggerBService.listNoteTrigger(in);
        return out;
    }

    @Override
    public Map saveNoteTrigger(Map in) throws Exception {
        Map out = iTriggerBService.saveNoteTrigger(in);
        return out;
    }

    @Override
    public Map getNoteTrigger(Map in) throws Exception {
        Map out = iTriggerBService.getNoteTrigger(in);
        return out;
    }
}
