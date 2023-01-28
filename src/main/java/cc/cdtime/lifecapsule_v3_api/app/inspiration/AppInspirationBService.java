package cc.cdtime.lifecapsule_v3_api.app.inspiration;

import cc.cdtime.lifecapsule_v3_api.business.inspiration.IInspirationBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppInspirationBService implements IAppInspirationBService {
    private final IInspirationBService iInspirationBService;

    public AppInspirationBService(IInspirationBService iInspirationBService) {
        this.iInspirationBService = iInspirationBService;
    }

    @Override
    public Map listInspiration(Map in) throws Exception {
        Map out = iInspirationBService.listInspiration(in);
        return out;
    }

    @Override
    public Map getInspiration(Map in) throws Exception {
        Map out = iInspirationBService.getInspiration(in);
        return out;
    }

    @Override
    public void saveInspiration(Map in) throws Exception {
        iInspirationBService.saveInspiration(in);
    }

    @Override
    public void deleteInspiration(Map in) throws Exception {
        iInspirationBService.deleteInspiration(in);
    }
}
