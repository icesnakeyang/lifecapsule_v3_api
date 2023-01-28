package cc.cdtime.lifecapsule_v3_api.web.publicUser;

import cc.cdtime.lifecapsule_v3_api.business.trigger.ITriggerBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebPublicUserBService implements IWebPublicUserBService {
    private final ITriggerBService iTriggerBService;

    public WebPublicUserBService(ITriggerBService iTriggerBService) {
        this.iTriggerBService = iTriggerBService;
    }

    @Override
    public Map getNoteFromMail(Map in) throws Exception {
        Map out = iTriggerBService.getNoteFromMail(in);
        return out;
    }
}
