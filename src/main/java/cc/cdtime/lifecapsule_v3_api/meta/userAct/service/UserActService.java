package cc.cdtime.lifecapsule_v3_api.meta.userAct.service;

import cc.cdtime.lifecapsule_v3_api.meta.userAct.dao.UserActDao;
import cc.cdtime.lifecapsule_v3_api.meta.userAct.entity.UserAct;
import cc.cdtime.lifecapsule_v3_api.meta.userAct.entity.UserActView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

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

    @Override
    public Integer totalUserAct(Map qIn) throws Exception {
        Integer total = userActDao.totalUserAct(qIn);
        return total;
    }

    @Override
    public ArrayList<UserActView> listUserAct(Map qIn) throws Exception {
        ArrayList<UserActView> userActViews = userActDao.listUserAct(qIn);
        return userActViews;
    }
}
