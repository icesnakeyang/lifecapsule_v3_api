package cc.cdtime.lifecapsule_v3_api.web.trigger;

import cc.cdtime.lifecapsule_v3_api.business.trigger.ITriggerBService;
import org.springframework.stereotype.Service;

@Service
public class WebTriggerBService implements IWebTriggerBService {
    private final ITriggerBService iTriggerBService;

    public WebTriggerBService(ITriggerBService iTriggerBService) {
        this.iTriggerBService = iTriggerBService;
    }
}
