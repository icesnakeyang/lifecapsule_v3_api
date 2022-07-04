package cc.cdtime.lifecapsule_v3_api.meta.user.dao;

import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserBase;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserBaseDao {
    /**
     * 创建一个基本用户账户
     *
     * @param userBase
     */
    void createUserBase(UserBase userBase);

    /**
     * 查询用户基础信息
     *
     * @param userId
     * @return
     */
    UserView getUserBase(String userId);

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
    UserView getUser(Map qIn);
}
