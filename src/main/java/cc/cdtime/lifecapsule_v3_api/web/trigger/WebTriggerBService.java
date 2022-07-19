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
    public Map saveNoteRecipientTrigger(Map in) throws Exception {
        Map out = iTriggerBService.saveNoteRecipientTrigger(in);
        return out;
    }

    @Override
    public Map getNoteRecipientTrigger(Map in) throws Exception {
        Map out = iTriggerBService.getNoteRecipientTrigger(in);
        return out;
    }
}
