package cc.cdtime.lifecapsule_v3_api.meta.user.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserEncodeKeyView {
    private Integer ids;
    private String encodeKeyId;
    private String userId;
    private String encodeKey;
    private Date createTime;
}
