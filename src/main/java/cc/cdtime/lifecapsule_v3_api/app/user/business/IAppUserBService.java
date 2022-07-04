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
}
