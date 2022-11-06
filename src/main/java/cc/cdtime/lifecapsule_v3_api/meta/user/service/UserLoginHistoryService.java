package cc.cdtime.lifecapsule_v3_api.meta.user.service;

import cc.cdtime.lifecapsule_v3_api.meta.user.dao.UserLoginHistoryDao;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserLoginHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginHistoryService implements IUserLoginHistoryService {
    private final UserLoginHistoryDao userLoginHistoryDao;

    public UserLoginHistoryService(UserLoginHistoryDao userLoginHistoryDao) {
        this.userLoginHistoryDao = userLoginHistoryDao;
    }

    @Override
    public void createUserLoginHistory(UserLoginHistory userLoginHistory) throws Exception {
        userLoginHistoryDao.createUserLoginHistory(userLoginHistory);
    }
}
