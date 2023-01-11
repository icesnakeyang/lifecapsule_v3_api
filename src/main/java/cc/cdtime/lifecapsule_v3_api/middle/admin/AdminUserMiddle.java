package cc.cdtime.lifecapsule_v3_api.middle.admin;

import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUser;
import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUserView;
import cc.cdtime.lifecapsule_v3_api.meta.admin.service.IAdminUserService;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmailView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class AdminUserMiddle implements IAdminUserMiddle {
    private final IAdminUserService iAdminUserService;

    public AdminUserMiddle(IAdminUserService iAdminUserService) {
        this.iAdminUserService = iAdminUserService;
    }

    @Override
    public void createAdminUser(AdminUser adminUser) throws Exception {
        iAdminUserService.createAdminUser(adminUser);
    }

    @Override
    public AdminUserView getAdminUser(Map qIn, Boolean returnNull) throws Exception {
        AdminUserView adminUserView = iAdminUserService.getAdminUser(qIn);
        if (adminUserView == null) {
            if (returnNull) {
                return null;
            }
            //没有查询到管理员账号
            throw new Exception("10027");
        }
        return adminUserView;
    }

    @Override
    public ArrayList<UserEmailView> listUserEmail(Map qIn) throws Exception {
        ArrayList<UserEmailView> userEmailViews = iAdminUserService.listUserEmail(qIn);
        return userEmailViews;
    }
}
