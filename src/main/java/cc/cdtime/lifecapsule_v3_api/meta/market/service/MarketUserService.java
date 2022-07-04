package cc.cdtime.lifecapsule_v3_api.meta.market.service;

import cc.cdtime.lifecapsule_v3_api.meta.market.dao.MarketUserDao;
import cc.cdtime.lifecapsule_v3_api.meta.market.entity.MarketUser;
import cc.cdtime.lifecapsule_v3_api.meta.market.entity.MarketUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MarketUserService implements IMarketUserService {
    private final MarketUserDao marketUserDao;

    public MarketUserService(MarketUserDao marketUserDao) {
        this.marketUserDao = marketUserDao;
    }

    @Override
    public void createMarketUser(MarketUser marketUser) throws Exception {
        marketUserDao.createMarketUser(marketUser);
    }

    @Override
    public MarketUserView getMarketUser(Map qIn) throws Exception {
        MarketUserView marketUserView = marketUserDao.getMarketUser(qIn);
        return marketUserView;
    }
}
