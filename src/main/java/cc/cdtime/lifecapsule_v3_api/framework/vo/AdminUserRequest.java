package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

@Data
public class AdminUserRequest {
    private String loginName;
    private String password;
    private String roleType;
}
