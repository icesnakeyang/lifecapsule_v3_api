package cc.cdtime.lifecapsule_v3_api.app.user.business;

import java.util.Map;

public interface IAppUserBService {
    /**
     * 用户通过手机设备ID登录或者注册
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map signInByDevice(Map in) throws Exception;

    /**
     * 用户通过token登录
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map signInByToken(Map in) throws Exception;

    /**
     * App用户查询自己的个人信息
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getMyProfile(Map in) throws Exception;

    /**
     * App用户保存自己的个人信息
     *
     * @param in
     * @throws Exception
     */
    void saveMyProfile(Map in) throws Exception;

    Map signInByNothing(Map in) throws Exception;

    Map listMyEmail(Map in) throws Exception;

    Map getMyEmail(Map in) throws Exception;

    /**
     * App用户设置一个email为默认
     *
     * @param in
     * @throws Exception
     */
    void setDefaultEmail(Map in) throws Exception;

    /**
     * 用户通过email验证登录
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map bindEmail(Map in) throws Exception;

    /**
     * App用户通过token获取用户登录信息，查看是否需要口令登录
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getUserLoginByToken(Map in) throws Exception;

    Map signByEmail(Map in) throws Exception;

    void sendVerifyCodeToEmail(Map in) throws Exception;

    void saveNickname(Map in) throws Exception;

    Map signByLoginNamePassword(Map in) throws Exception;
}
