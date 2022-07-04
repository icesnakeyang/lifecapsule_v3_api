package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

@Data
public class UserAccountRequest {
    private String deviceCode;
    private String deviceName;
    private String loginName;
    private String password;
}
