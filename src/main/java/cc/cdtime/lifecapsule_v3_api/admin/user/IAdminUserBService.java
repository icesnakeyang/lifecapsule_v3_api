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

    /**
     * 读取所有用户登录日志列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listUserLoginLog(Map in) throws Exception;
}
