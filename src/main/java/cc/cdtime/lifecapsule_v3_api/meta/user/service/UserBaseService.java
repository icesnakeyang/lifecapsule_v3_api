package cc.cdtime.lifecapsule_v3_api.meta.user.service;

import cc.cdtime.lifecapsule_v3_api.meta.user.dao.UserBaseDao;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserBase;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserBaseService implements IUserBaseService {
    private final UserBaseDao userBaseDao;

    public UserBaseService(UserBaseDao userBaseDao) {
        this.userBaseDao = userBaseDao;
    }

    @Override
    public void createUserBase(UserBase userBase) throws Exception {
        userBaseDao.createUserBase(userBase);
    }

    @Override
    public UserView getUserBase(String userId) throws Exception {
        UserView userView = userBaseDao.getUserBase(userId);
        return userView;
    }

    @Override
    public UserView getUser(Map qIn) throws Exception {
        UserView userView = userBaseDao.getUser(qIn);
        return userView;
    }
}
