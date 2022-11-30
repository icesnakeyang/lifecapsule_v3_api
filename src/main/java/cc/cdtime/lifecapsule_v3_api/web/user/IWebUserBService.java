package cc.cdtime.lifecapsule_v3_api.web.user;

import java.util.Map;

public interface IWebUserBService {
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

    Map signByLoginName(Map in) throws Exception;

    void setLoginNamePassword(Map in) throws Exception;
}
