package cc.cdtime.lifecapsule_v3_api.meta.user.dao;

import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserLogin;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserLoginLog;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface UserLoginDao {
    /**
     * 记录用户登录日志
     *
     * @param deviceLog
     */
    void createUserLoginLog(UserLoginLog deviceLog);

    /**
     * 查询用户登录日志列表
     *
     * @param qIn deviceCode
     *            userId
     *            offset
     *            size
     * @return
     */
    ArrayList<UserView> listUserLoginLog(Map qIn);
    Integer totalUserLoginLog(Map qIn);

    /**
     * 创建一个用户的登录信息
     *
     * @param userLogin
     */
    void createUserLogin(UserLogin userLogin);

    /**
     * 修改用户登录信息
     *
     * @param qIn token
     *            tokenTime
     *            userId
     */
    void updateUserLogin(Map qIn);

    /**
     * 查询一个用户的登录信息
     *
     * @param qIn userId
     *            token
     * @return
     */
    UserView getUserLogin(Map qIn);
}
