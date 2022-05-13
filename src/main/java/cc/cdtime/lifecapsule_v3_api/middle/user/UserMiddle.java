package cc.cdtime.lifecapsule_v3_api.middle.user;

import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserBase;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserLogin;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserLoginLog;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.meta.user.service.IUserBaseService;
import cc.cdtime.lifecapsule_v3_api.meta.user.service.IUserLoginService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class UserMiddle implements IUserMiddle {
    private final IUserBaseService iUserBaseService;
    private final IUserLoginService iUserLoginService;

    public UserMiddle(IUserBaseService iUserBaseService,
                      IUserLoginService iUserLoginService) {
        this.iUserBaseService = iUserBaseService;
        this.iUserLoginService = iUserLoginService;
    }

    @Override
    public void createUserBase(UserBase userBase) throws Exception {
        iUserBaseService.createUserBase(userBase);
    }

    @Override
    public UserView getUserTiny(Map qIn, Boolean returnNull) throws Exception {
        String userId = (String) qIn.get("userId");
        UserView userView = iUserBaseService.getUserBase(userId);
        if (userView == null) {
            if (returnNull) {
                return null;
            }
            //没有查询到用户信息
            throw new Exception("10002");
        }
        return userView;
    }

    @Override
    public void createUserLoginLog(UserLoginLog userLoginLog) throws Exception {
        iUserLoginService.createUserLoginLog(userLoginLog);
    }

    @Override
    public ArrayList<UserView> listUserLoginLog(Map qIn) throws Exception {
        ArrayList<UserView> userViews = iUserLoginService.listUserLoginLog(qIn);
        return userViews;
    }

    @Override
    public void createUserLogin(UserLogin userLogin) throws Exception {
        iUserLoginService.createUserLogin(userLogin);
    }

    @Override
    public void updateUserLogin(Map qIn) throws Exception {
        iUserLoginService.updateUserLogin(qIn);
    }

    @Override
    public UserView getUserLogin(Map qIn, Boolean returnNull) throws Exception {
        UserView userView = iUserLoginService.getUserLogin(qIn);
        if (userView == null) {
            if (returnNull) {
                return null;
            }
            //没有查询该用户登录信息
            throw new Exception("10003");
        }
        return userView;
    }
}
