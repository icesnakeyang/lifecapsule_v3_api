package cc.cdtime.lifecapsule_v3_api.business.marketUser;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.market.entity.MarketUser;
import cc.cdtime.lifecapsule_v3_api.meta.market.entity.MarketUserView;
import cc.cdtime.lifecapsule_v3_api.middle.marketUser.IMarketUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MarketUserBService implements IMarketUserBService {
    private final IMarketUserMiddle iMarketUserMiddle;

    public MarketUserBService(IMarketUserMiddle iMarketUserMiddle) {
        this.iMarketUserMiddle = iMarketUserMiddle;
    }

    @Override
    public void createMarketUser(Map in) throws Exception {
        String loginName = in.get("loginName").toString();
        String loginPassword = in.get("loginPassword").toString();

        MarketUser marketUser = new MarketUser();
        marketUser.setMarketUserId(GogoTools.UUID32());
        marketUser.setCreateTime(new Date());
        marketUser.setLoginName(loginName);
        marketUser.setLoginPassword(GogoTools.encoderByMd5(loginPassword));
        marketUser.setToken(GogoTools.UUID32());
        marketUser.setTokenTime(new Date());

        iMarketUserMiddle.createMarketUser(marketUser);
    }

    @Override
    public Map marketUserLogin(Map in) throws Exception {
        String loginName = in.get("loginName").toString();
        String loginPassword = in.get("loginPassword").toString();

        Map qIn = new HashMap();
        qIn.put("loginName", loginName);
        qIn.put("loginPassword", GogoTools.encoderByMd5(loginPassword));
        MarketUserView marketUserView = iMarketUserMiddle.getMarketUser(qIn);

        Map out = new HashMap();
        out.put("marketUser", marketUserView);

        return out;
    }
}
