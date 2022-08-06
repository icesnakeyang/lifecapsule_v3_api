package cc.cdtime.lifecapsule_v3_api.meta.user.service;

import cc.cdtime.lifecapsule_v3_api.meta.user.dao.UserLoginDao;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserLogin;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserLoginLog;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class UserLoginService implements IUserLoginService {
    private final UserLoginDao userLoginDao;

    public UserLoginService(UserLoginDao userLoginDao) {
        this.userLoginDao = userLoginDao;
    }

    @Override
    public void createUserLoginLog(UserLoginLog userLoginLog) throws Exception {
        userLoginDao.createUserLoginLog(userLoginLog);
    }

    @Override
    public ArrayList<UserView> listUserLoginLog(Map qIn) throws Exception {
        ArrayList<UserView> userViews = userLoginDao.listUserLoginLog(qIn);
        return userViews;
    }

    @Override
    public Integer totalUserLoginLog(Map qIn) throws Exception {
        Integer total = userLoginDao.totalUserLoginLog(qIn);
        return total;
    }

    @Override
    public void createUserLogin(UserLogin userLogin) throws Exception {
        userLoginDao.createUserLogin(userLogin);
    }

    @Override
    public void updateUserLogin(Map qIn) throws Exception {
        userLoginDao.updateUserLogin(qIn);
    }

    @Override
    public UserView getUserLogin(Map qIn) throws Exception {
        UserView userView = userLoginDao.getUserLogin(qIn);
        return userView;
    }
}
