package cc.cdtime.lifecapsule_v3_api.web.trigger;

import cc.cdtime.lifecapsule_v3_api.business.trigger.ITriggerBService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebTriggerBService implements IWebTriggerBService {
    private final ITriggerBService iTriggerBService;

    public WebTriggerBService(ITriggerBService iTriggerBService) {
        this.iTriggerBService = iTriggerBService;
    }

    @Override
    public void createTriggerInstant(Map in) throws Exception {
        iTriggerBService.createTriggerInstant(in);
    }

    @Override
    public Map listMyTriggerQue(Map in) throws Exception {
        Map out = iTriggerBService.listNoteTrigger(in);
        return out;
    }

    @Override
    public Map getMyTriggerDetail(Map in) throws Exception {
        Map out = iTriggerBService.getTriggerDetail(in);
        return out;
    }

    @Override
    public void createTriggerDatetime(Map in) throws Exception {
        iTriggerBService.createNoteTriggerByDatetime(in);
    }

    @Override
    public void createTriggerPrimary(Map in) throws Exception {
        iTriggerBService.createNoteTriggerPrimary(in);
    }
}
