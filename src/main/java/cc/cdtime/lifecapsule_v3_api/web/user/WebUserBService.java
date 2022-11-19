package cc.cdtime.lifecapsule_v3_api.web.user;

import cc.cdtime.lifecapsule_v3_api.business.email.IEmailBService;
import cc.cdtime.lifecapsule_v3_api.business.userAccount.IUserAccountBService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebUserBService implements IWebUserBService {
    private final IUserMiddle iUserMiddle;
    private final IUserAccountBService iUserAccountBService;
    private final IEmailBService iEmailBService;

    public WebUserBService(IUserMiddle iUserMiddle,
                           IUserAccountBService
            iUserAccountBService, IEmailBService iEmailBService) {
        this.iUserMiddle = iUserMiddle;
        this.iUserAccountBService = iUserAccountBService;
        this.iEmailBService = iEmailBService;
    }

    @Override
    public Map register(Map in) throws Exception {
        in.put("frontEnd", ESTags.WEB_CLIENT.toString());
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

    @Override
    public Map signInByNothing(Map in) throws Exception {
        in.put("frontEnd", ESTags.WEB_CLIENT.toString());
        Map out = iUserAccountBService.signInByNothing(in);
        return out;
    }

    @Override
    public Map bindEmail(Map in) throws Exception {
        in.put("frontEnd", ESTags.WEB_CLIENT.toString());
        Map out = iUserAccountBService.bindEmail(in);
        return out;
    }

    @Override
    public void saveUserNickname(Map in) throws Exception {
        iUserAccountBService.saveProfile(in);
    }

    @Override
    public Map signByEmail(Map in) throws Exception {
        in.put("frontEnd", ESTags.WEB_CLIENT.toString());
        Map out = iUserAccountBService.signByEmail(in);
        return out;
    }

    @Override
    public void sendVerifyCodeToEmail(Map in) throws Exception {
        iEmailBService.sendVerifyCodeToEmail(in);
    }
}
