package cc.cdtime.lifecapsule_v3_api.meta.user.entity;

import lombok.Data;

/**
 * 用户登录名和密码
 */
@Data
public class UserLoginName {
    private Integer ids;
    private String userId;
    private String loginName;
    private String password;
}
