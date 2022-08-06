package cc.cdtime.lifecapsule_v3_api.web.user;

import cc.cdtime.lifecapsule_v3_api.business.userAccount.IUserAccountBService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebUserBService implements IWebUserBService {
    private final IUserMiddle iUserMiddle;
    private final IUserAccountBService iUserAccountBService;

    public WebUserBService(IUserMiddle iUserMiddle, IUserAccountBService
            iUserAccountBService) {
        this.iUserMiddle = iUserMiddle;
        this.iUserAccountBService = iUserAccountBService;
    }

    @Override
    public Map register(Map in) throws Exception {
        Map out = iUserAccountBService.registerByLoginName(in);
        return out;
    }

    @Override
    public Map login(Map in) throws Exception {
        in.put("frontEnd", ESTags.WEB_CLIENT.toString());
        Map out = iUserAccountBService.loginByLoginName(in);
        return out;
    }

    @Override
    public Map registerByLoginName(Map in) throws Exception {
        in.put("frontEnd", ESTags.WEB_CLIENT.toString());
        Map out = iUserAccountBService.registerByLoginName(in);
        return out;
    }

    @Override
    public Map getUserInfoByToken(Map in) throws Exception {
        Map out = iUserAccountBService.getUserInfo(in);
        return out;
    }

    @Override
    public Map signByToken(Map in) throws Exception {
        in.put("frontEnd", ESTags.WEB_CLIENT.toString());
        Map out = iUserAccountBService.signByToken(in);
        return out;
    }
}
