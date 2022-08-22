package cc.cdtime.lifecapsule_v3_api.meta.email.dao;

import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmail;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmailView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface UserEmailDao {
    /**
     * 创建用户的email账号
     *
     * @param userEmail
     */
    void createUserEmail(UserEmail userEmail);

    /**
     * 根据email或userId查询用户
     *
     * @param qIn emailId
     *            email
     *            userId（默认）
     * @return
     */
    UserEmailView getUserEmail(Map qIn);

    /**
     * 修改用户email
     *
     * @param qIn email
     *            emailId
     *            status
     */
    void updateUserEmail(Map qIn);

    /**
     * 批量查询Email
     *
     * @param qIn userId
     * @return
     */
    ArrayList<UserEmailView> listEmail(Map qIn);

    /**
     * 把某个用户的所有email设置为某个状态
     *
     * @param qIn status
     *            userId
     */
    void setEmailStatus(Map qIn);
}
