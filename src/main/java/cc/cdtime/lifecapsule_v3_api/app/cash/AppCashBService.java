package cc.cdtime.lifecapsule_v3_api.app.cash;

import cc.cdtime.lifecapsule_v3_api.business.cash.ICashBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppCashBService implements IAppCashBService {
    private final ICashBService iCashBService;

    public AppCashBService(ICashBService iCashBService) {
        this.iCashBService = iCashBService;
    }

    @Override
    public void createLedger(Map in) throws Exception {
        iCashBService.createLedger(in);
    }

    @Override
    public Map getMyDefaultCashCategory(Map in) throws Exception {
        Map out = iCashBService.getMyDefaultCashCategory(in);
        return out;
    }

    @Override
    public Map listMyCashCategory(Map in) throws Exception {
        Map out = iCashBService.listMyCashCategory(in);
        return out;
    }

    @Override
    public void createMyCashCategory(Map in) throws Exception {
        iCashBService.createMyCashCategory(in);
    }
}
