package cc.cdtime.lifecapsule_v3_api.meta.admin.dao;

import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUser;
import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUserView;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmailView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface AdminUserDao {
    /**
     * 创建一个系统管理员
     *
     * @param adminUser
     */
    void createAdminUser(AdminUser adminUser);

    /**
     * 读取一个系统管理员账号
     *
     * @param qIn adminId
     *            loginName
     *            token
     * @return
     */
    AdminUserView getAdminUser(Map qIn);

    ArrayList<UserEmailView> listUserEmail(Map qIn);
}
