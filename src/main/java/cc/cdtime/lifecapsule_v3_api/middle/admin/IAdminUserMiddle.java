package cc.cdtime.lifecapsule_v3_api.middle.admin;

import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUser;
import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUserView;

import java.util.Map;

public interface IAdminUserMiddle {
    /**
     * 创建一个系统管理员
     *
     * @param adminUser
     */
    void createAdminUser(AdminUser adminUser) throws Exception;

    /**
     * 读取一个系统管理员账号
     *
     * @param qIn adminId
     *            loginName
     *            token
     * @return
     */
    AdminUserView getAdminUser(Map qIn,Boolean returnNull) throws Exception;
}
