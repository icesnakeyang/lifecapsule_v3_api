package cc.cdtime.lifecapsule_v3_api.meta.user.entity;

import lombok.Data;

/**
 * 用户的秘钥
 */
@Data
public class UserEncodeKey {
    private Integer ids;
    private String encodeKey;
    private String indexId;
}
