package cc.cdtime.lifecapsule_v3_api.market.business;

import java.util.Map;

public interface IMarketMarketUserBService {
    void createMarketUser(Map in) throws Exception;

    Map login(Map in) throws Exception;
}
