package cc.cdtime.lifecapsule_v3_api.meta.admin.entity;

import lombok.Data;

import java.util.Date;

/**
 * 系统管理员账号
 */
@Data
public class AdminUser {
    private Integer ids;
    private String adminId;
    private String loginName;
    private String password;
    private String roleType;
    private Date createTime;
    private String token;
    private Date tokenTime;
}
