package cc.cdtime.lifecapsule_v3_api.meta.user.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户曾经使用过的历史账号
 */
@Data
public class UserLoginHistory {
    private Integer ids;
    private String oldToken;
    private String newToken;
    private String oldUserId;
    private String newUserId;
    private Date createTime;
}
