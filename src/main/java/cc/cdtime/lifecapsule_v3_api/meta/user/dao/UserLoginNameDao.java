package cc.cdtime.lifecapsule_v3_api.meta.user.dao;

import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserLoginName;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserLoginNameDao {
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
}
