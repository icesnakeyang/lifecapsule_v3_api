package cc.cdtime.lifecapsule_v3_api.meta.security.entity;

import lombok.Data;

/**
 * 钥匙类
 */
@Data
public class SecurityKey {
    private String keyToken;
    private String privateRSA;
}
