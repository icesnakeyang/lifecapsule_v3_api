package cc.cdtime.lifecapsule_v3_api.meta.user.service;

import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserBase;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;

import java.util.ArrayList;
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
     *            language
     *            userId
     */
    void updateUserBase(Map qIn) throws Exception;
}
