package cc.cdtime.lifecapsule_v3_api.meta.user.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserView {
    private String userId;
    private Integer ids;
    private String playerId;
    private String deviceCode;
    private String deviceName;
    private Timestamp loginTime;
    private Timestamp regTime;
    private String token;
    private String tokenTime;
}
