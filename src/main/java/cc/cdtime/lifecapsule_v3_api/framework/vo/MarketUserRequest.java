package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

@Data
public class MarketUserRequest {
    private String loginName;
    private String loginPassword;
    private String token;
    private String marketUserId;
}
