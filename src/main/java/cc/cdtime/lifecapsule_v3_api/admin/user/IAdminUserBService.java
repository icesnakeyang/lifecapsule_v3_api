package cc.cdtime.lifecapsule_v3_api.admin.user;

import java.util.Map;

public interface IAdminUserBService {
    /**
     * 读取所有用户列表
     *
     * @param in
     * @return
     */
    Map listUsers(Map in) throws Exception;
}
