package cc.cdtime.lifecapsule_v3_api.meta.email.service;

import cc.cdtime.lifecapsule_v3_api.meta.email.dao.UserEmailDao;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmail;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmailView;
import cc.cdtime.lifecapsule_v3_api.meta.email.service.IUserEmailService;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class UserEmailService implements IUserEmailService {
    private final UserEmailDao userEmailDao;

    public UserEmailService(UserEmailDao userEmailDao) {
        this.userEmailDao = userEmailDao;
    }

    @Override
    public void createUserEmail(UserEmail userEmail) throws Exception {
        userEmailDao.createUserEmail(userEmail);
    }

    @Override
    public UserEmailView getUserEmail(Map qIn) throws Exception {
        UserEmailView userEmailView = userEmailDao.getUserEmail(qIn);
        return userEmailView;
    }

    @Override
    public void updateUserEmail(Map qIn) throws Exception {
        userEmailDao.updateUserEmail(qIn);
    }

    @Override
    public ArrayList<UserEmailView> listEmail(Map qIn) throws Exception {
        ArrayList<UserEmailView> UserEmailView = userEmailDao.listEmail(qIn);
        return UserEmailView;
    }

    @Override
    public void setEmailStatus(Map qIn) throws Exception {
        userEmailDao.setEmailStatus(qIn);
    }
}
