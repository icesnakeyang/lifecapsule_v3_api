package cc.cdtime.lifecapsule_v3_api.meta.user.service;

import cc.cdtime.lifecapsule_v3_api.meta.user.dao.UserLoginNameDao;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserLoginName;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserLoginNameService implements IUserLoginNameService {
    private final UserLoginNameDao userLoginNameDao;

    public UserLoginNameService(UserLoginNameDao userLoginNameDao) {
        this.userLoginNameDao = userLoginNameDao;
    }

    @Override
    public void createUserLoginName(UserLoginName userLoginName) {
        userLoginNameDao.createUserLoginName(userLoginName);
    }

    @Override
    public UserView getLoginName(Map qIn) {
        UserView userView = userLoginNameDao.getLoginName(qIn);
        return userView;
    }

    @Override
    public void updateLoginName(Map qIn) throws Exception {
        userLoginNameDao.updateLoginName(qIn);
    }
}
