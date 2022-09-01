package cc.cdtime.lifecapsule_v3_api.meta.user.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户的秘钥
 */
@Data
public class UserEncodeKey {
    private Integer ids;
    private String encodeKeyId;
    private String userId;
    private Date createTime;
    private String encodeKey;
    private String indexId;
}
