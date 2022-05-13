package cc.cdtime.lifecapsule_v3_api.meta.userAct.service;

import cc.cdtime.lifecapsule_v3_api.meta.userAct.dao.UserActDao;
import cc.cdtime.lifecapsule_v3_api.meta.userAct.entity.UserAct;
import org.springframework.stereotype.Service;

@Service
public class UserActService implements IUserActService {
    private final UserActDao userActDao;

    public UserActService(UserActDao userActDao) {
        this.userActDao = userActDao;
    }

    @Override
    public void createUserAct(UserAct userAct) throws Exception {
        userActDao.createUserAct(userAct);
    }
}
