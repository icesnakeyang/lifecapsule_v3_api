package cc.cdtime.lifecapsule_v3_api.meta.user.service;

import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserBase;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;

import java.util.Map;

public interface IUserBaseService {
    /**
     * 创建一个基本用户账户
     *
     * @param userBase
     */
    void createUserBase(UserBase userBase) throws Exception;

    /**
     * 查询用户基础信息
     *
     * @param userId
     * @return
     */
    UserView getUserBase(String userId) throws Exception;

    /**
     * 泛查询一个用户信息
     *
     * @param qIn userId
     *            token
     *            loginName
     *            password
     *            phone
     *            email
     * @return
     */
    UserView getUser(Map qIn) throws Exception;
}
