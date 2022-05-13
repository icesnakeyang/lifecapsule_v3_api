package cc.cdtime.lifecapsule_v3_api.user.account.business;

import java.util.Map;

public interface IUserAccountBService {
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
}
