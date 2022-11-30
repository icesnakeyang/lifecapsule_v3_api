package cc.cdtime.lifecapsule_v3_api.business.userAccount;

import java.util.Map;

public interface IUserAccountBService {
    /**
     * 获取用户基本信息
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getUserInfo(Map in) throws Exception;

    Map signByToken(Map in) throws Exception;

    /**
     * App用户查询自己的个人信息
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getProfile(Map in) throws Exception;

    void saveProfile(Map in) throws Exception;

    Map signInByNothing(Map in) throws Exception;

    Map listEmail(Map in) throws Exception;

    Map getEmail(Map in) throws Exception;

    /**
     * App用户设置一个email为默认
     *
     * @param in
     * @throws Exception
     */
    void setDefaultEmail(Map in) throws Exception;

    /**
     * 用户绑定email
     * email通过验证后，绑定给用户账号
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map bindEmail(Map in) throws Exception;

    Map signByEmail(Map in) throws Exception;
    Map signByLoginName(Map in) throws Exception;

    /**
     * 用户通过token获取用户登录信息，查看是否需要口令登录
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getUserLoginByToken(Map in) throws Exception;

    /**
     * 用户设置登录名和密码
     * 只适用未设置登录名的用户，如果修改账号，使用changePassword接口
     * @param in
     * @throws Exception
     */
    void setLoginNamePassword(Map in) throws Exception;
}
