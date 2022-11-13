package cc.cdtime.lifecapsule_v3_api.middle.userAct;

import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.meta.userAct.entity.UserAct;
import cc.cdtime.lifecapsule_v3_api.meta.userAct.entity.UserActView;
import cc.cdtime.lifecapsule_v3_api.meta.userAct.service.IUserActService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

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

    @Override
    public Integer totalUserAct(Map qIn) throws Exception {
        Integer total = iUserActService.totalUserAct(qIn);
        return total;
    }

    @Override
    public ArrayList<UserActView> listUserAct(Map qIn) throws Exception {
        ArrayList<UserActView> userActViews = iUserActService.listUserAct(qIn);
        return userActViews;
    }
}
