package cc.cdtime.lifecapsule_v3_api.web.user;

import java.util.Map;

public interface IWebUserBService {
    /**
     * 用户通过web页面注册
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map register(Map in) throws Exception;

    /**
     * 用户通过web页面登录
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map login(Map in) throws Exception;

    Map registerByLoginName(Map in) throws Exception;

    /**
     * web端用户通过token获取个人账户信息
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getUserInfoByToken(Map in) throws Exception;

    Map signByToken(Map in) throws Exception;

    Map signInByNothing(Map in) throws Exception;

    Map bindEmail(Map in) throws Exception;

    /**
     * web 用户保存昵称
     *
     * @param in
     * @throws Exception
     */
    void saveUserNickname(Map in) throws Exception;

    Map signByEmail(Map in) throws Exception;

    void sendVerifyCodeToEmail(Map in) throws Exception;
}
