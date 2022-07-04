package cc.cdtime.lifecapsule_v3_api.meta.market.dao;

import cc.cdtime.lifecapsule_v3_api.meta.market.entity.MarketUser;
import cc.cdtime.lifecapsule_v3_api.meta.market.entity.MarketUserView;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MarketUserDao {
    /**
     * 创建一个市场运营人员
     *
     * @param marketUser
     */
    void createMarketUser(MarketUser marketUser);

    /**
     * 查询一个市场运营人员账号
     *
     * @param qIn marketUserId
     *            token
     *            loginName
     *            loginPassword
     * @return
     */
    MarketUserView getMarketUser(Map qIn);
}
