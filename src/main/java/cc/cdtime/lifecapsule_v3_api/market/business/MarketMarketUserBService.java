package cc.cdtime.lifecapsule_v3_api.market.business;


import cc.cdtime.lifecapsule_v3_api.business.marketUser.IMarketUserBService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MarketMarketUserBService implements IMarketMarketUserBService {
    private final IMarketUserBService iMarketUserBService;

    public MarketMarketUserBService(IMarketUserBService iMarketUserBService) {
        this.iMarketUserBService = iMarketUserBService;
    }

    @Override
    public void createMarketUser(Map in) throws Exception {
        iMarketUserBService.createMarketUser(in);
    }

    @Override
    public Map login(Map in) throws Exception {
        Map out=iMarketUserBService.marketUserLogin(in);
        return out;
    }
}
