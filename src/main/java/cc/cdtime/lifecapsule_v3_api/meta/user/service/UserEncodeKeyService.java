package cc.cdtime.lifecapsule_v3_api.meta.user.service;

import cc.cdtime.lifecapsule_v3_api.meta.user.dao.UserEncodeKeyDao;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKey;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKeyView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserEncodeKeyService implements IUserEncodeKeyService {
    private final UserEncodeKeyDao userEncodeKeyDao;

    public UserEncodeKeyService(UserEncodeKeyDao userEncodeKeyDao) {
        this.userEncodeKeyDao = userEncodeKeyDao;
    }

    @Override
    public void createUserEncodeKey(UserEncodeKey userEncodeKey) throws Exception {
        userEncodeKeyDao.createUserEncodeKey(userEncodeKey);
    }

    @Override
    public UserEncodeKeyView getUserEncodeKey(Map qIn) throws Exception {
        UserEncodeKeyView userEncodeKeyView = userEncodeKeyDao.getUserEncodeKey(qIn);
        return userEncodeKeyView;
    }

    @Override
    public void updateUserEncodeKey(Map qIn) throws Exception {
        userEncodeKeyDao.updateUserEncodeKey(qIn);
    }
}
