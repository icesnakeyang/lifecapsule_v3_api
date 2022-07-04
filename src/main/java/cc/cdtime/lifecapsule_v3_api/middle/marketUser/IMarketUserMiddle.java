package cc.cdtime.lifecapsule_v3_api.middle.marketUser;

import cc.cdtime.lifecapsule_v3_api.meta.market.entity.MarketUser;
import cc.cdtime.lifecapsule_v3_api.meta.market.entity.MarketUserView;

import java.util.Map;

public interface IMarketUserMiddle {
    /**
     * 创建一个市场运营人员
     *
     * @param marketUser
     */
    void createMarketUser(MarketUser marketUser) throws Exception;

    /**
     * 查询一个市场运营人员账号
     *
     * @param qIn marketUserId
     *            token
     * @return
     */
    MarketUserView getMarketUser(Map qIn) throws Exception;
}
