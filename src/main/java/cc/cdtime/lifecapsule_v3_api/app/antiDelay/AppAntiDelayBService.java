package cc.cdtime.lifecapsule_v3_api.app.antiDelay;

import cc.cdtime.lifecapsule_v3_api.business.antiDelay.IAntiDelayBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppAntiDelayBService implements IAppAntiDelayBService {
    private final IAntiDelayBService iAntiDelayBService;

    public AppAntiDelayBService(IAntiDelayBService iAntiDelayBService) {
        this.iAntiDelayBService = iAntiDelayBService;
    }

    @Override
    public Map listMyAntiDelayNote(Map in) throws Exception {
        Map out = iAntiDelayBService.listMyAntiDelayNote(in);
        return out;
    }

    @Override
    public void createMyAntiDelayNote(Map in) throws Exception {
        iAntiDelayBService.createAntiDelayNote(in);
    }

    @Override
    public Map getMyAntiDelayNote(Map in) throws Exception {
        Map out = iAntiDelayBService.getMyAntiDelayNote(in);
        return out;
    }

    @Override
    public void updateMyAntiDelayNote(Map in) throws Exception {
        iAntiDelayBService.updateAntiDelayNote(in);
    }

    @Override
    public void deleteMyAntiDelayNote(Map in) throws Exception {
        iAntiDelayBService.deleteAntiDelayNote(in);
    }

    @Override
    public Map loadLastMyAntiDelayNote(Map in) throws Exception {
        Map out = iAntiDelayBService.loadLastMyAntiDelayNote(in);
        return out;
    }
}
