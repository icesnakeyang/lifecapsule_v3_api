package cc.cdtime.lifecapsule_v3_api.app.noteTrigger;

import cc.cdtime.lifecapsule_v3_api.business.noteTrigger.INoteTriggerBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppNoteTriggerBService implements IAppNoteTriggerBService {
    private final INoteTriggerBService iNoteTriggerBService;

    public AppNoteTriggerBService(INoteTriggerBService iNoteTriggerBService) {
        this.iNoteTriggerBService = iNoteTriggerBService;
    }

    @Override
    public Map listMyNoteTrigger(Map in) throws Exception {
        Map out = iNoteTriggerBService.listMyNoteTrigger(in);
        return out;
    }
}
