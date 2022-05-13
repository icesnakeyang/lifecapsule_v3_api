package cc.cdtime.lifecapsule_v3_api.middle.userAct;

import cc.cdtime.lifecapsule_v3_api.meta.userAct.entity.UserAct;
import cc.cdtime.lifecapsule_v3_api.meta.userAct.service.IUserActService;
import org.springframework.stereotype.Service;

@Service
public class UserActMiddle implements IUserActMiddle {
    private final IUserActService iUserActService;

    public UserActMiddle(IUserActService iUserActService) {
        this.iUserActService = iUserActService;
    }

    @Override
    public void createUserAct(UserAct userAct) throws Exception {
        iUserActService.createUserAct(userAct);
    }
}
