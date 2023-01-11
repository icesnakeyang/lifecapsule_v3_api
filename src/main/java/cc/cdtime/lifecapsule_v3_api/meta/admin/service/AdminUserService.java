package cc.cdtime.lifecapsule_v3_api.meta.admin.service;

import cc.cdtime.lifecapsule_v3_api.meta.admin.dao.AdminUserDao;
import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUser;
import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUserView;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmailView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class AdminUserService implements IAdminUserService {
    private final AdminUserDao adminUserDao;

    public AdminUserService(AdminUserDao adminUserDao) {
        this.adminUserDao = adminUserDao;
    }

    @Override
    public void createAdminUser(AdminUser adminUser) throws Exception {
        adminUserDao.createAdminUser(adminUser);
    }

    @Override
    public AdminUserView getAdminUser(Map qIn) throws Exception {
        AdminUserView adminUserView = adminUserDao.getAdminUser(qIn);
        return adminUserView;
    }

    @Override
    public ArrayList<UserEmailView> listUserEmail(Map qIn) throws Exception {
        ArrayList<UserEmailView> userEmailViews = adminUserDao.listUserEmail(qIn);
        return userEmailViews;
    }
}
