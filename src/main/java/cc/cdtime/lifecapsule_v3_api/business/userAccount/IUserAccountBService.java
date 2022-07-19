package cc.cdtime.lifecapsule_v3_api.business.userAccount;

import java.util.Map;

public interface IUserAccountBService {
    /**
     * 用户通过用户登录名和密码登录
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map loginByLoginName(Map in) throws Exception;

    /**
     * 用户通过用户登录名和密码注册账号
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map registerByLoginName(Map in) throws Exception;

    /**
     * 获取用户基本信息
     * @param in
     * @return
     * @throws Exception
     */
    Map getUserInfo(Map in) throws Exception;

    Map signByToken(Map in) throws Exception;
}
