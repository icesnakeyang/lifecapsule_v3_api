package cc.cdtime.lifecapsule_v3_api.meta.user.entity;

import lombok.Data;

import java.sql.Date;
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
    private Timestamp tokenTime;
    private Timestamp createTime;
    private String phone;
    private String email;
    private String loginName;
    private Timestamp timerPrimary;
    private String password;
    private String nickname;
    private String deviceId;
    private String frontEnd;
    private String emailId;
    private String openPassword;
}
