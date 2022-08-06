package cc.cdtime.lifecapsule_v3_api.middle.user;

import cc.cdtime.lifecapsule_v3_api.meta.user.entity.*;

import java.util.ArrayList;
import java.util.Map;

public interface IUserMiddle {
    /**
     * 创建一个基本用户账户
     *
     * @param userBase
     */
    void createUserBase(UserBase userBase) throws Exception;

    /**
     * 获取用户简要信息
     *
     * @param qIn
     * @return
     * @throws Exception
     */
    UserView getUserTiny(Map qIn, Boolean returnNull, Boolean isLogin) throws Exception;

    /**
     * 记录用户登录日志
     *
     * @param userLoginLog
     */
    void createUserLoginLog(UserLoginLog userLoginLog) throws Exception;

    /**
     * 查询用户登录日志列表
     *
     * @param qIn deviceCode
     *            userId
     *            offset
     *            size
     * @return
     */
    ArrayList<UserView> listUserLoginLog(Map qIn) throws Exception;
    Integer totalUserLoginLog(Map qIn) throws Exception;

    /**
     * 创建一个用户的登录信息
     *
     * @param userLogin
     */
    void createUserLogin(UserLogin userLogin) throws Exception;

    /**
     * 修改用户登录信息
     *
     * @param qIn token
     *            tokenTime
     *            userId
     */
    void updateUserLogin(Map qIn) throws Exception;

    /**
     * 查询一个用户的登录信息
     *
     * @param qIn userId
     *            token
     * @return
     */
    UserView getUserLogin(Map qIn, Boolean returnNull) throws Exception;

    /**
     * 创建一个用自定义登录名账号
     *
     * @param userLoginName
     */
    void createUserLoginName(UserLoginName userLoginName);

    /**
     * 查询一个登录名用户
     *
     * @param qIn userId
     *            loginName
     *            password
     * @return
     */
    UserView getLoginName(Map qIn);

    /**
     * 获取一个用户信息，并判断是否已登录
     *
     * @param qIn        userId
     *                   token
     *                   loginName
     *                   password
     *                   phone
     *                   email
     * @param returnNull
     * @param isLogin
     * @return
     * @throws Exception
     */
    UserView getUser(Map qIn, Boolean returnNull, Boolean isLogin) throws Exception;

    /**
     * @param qIn 查询用户列表
     *            userId
     *            loginName
     *            phone
     *            email
     * @return
     */
    ArrayList<UserView> listUser(Map qIn) throws Exception;
    Integer totalUser(Map qIn) throws Exception;

    /**
     * 修改用户基本信息
     *
     * @param qIn nickname
     *            userId
     */
    void updateUserBase(Map qIn) throws Exception;


}
