package cc.cdtime.lifecapsule_v3_api.meta.admin.entity;

import lombok.Data;

import java.util.Date;

@Data
public class AdminUserView {
    private Integer ids;
    private String adminId;
    private String loginName;
    private String password;
    private String roleType;
    private Date createTime;
    private String token;
    private Date tokenTime;
}
