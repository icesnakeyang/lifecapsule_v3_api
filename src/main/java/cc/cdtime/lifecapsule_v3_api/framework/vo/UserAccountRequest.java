package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserAccountRequest extends Request {
    private String deviceCode;
    private String deviceName;
    private String loginName;
    private String password;
    private String nickname;
    private String searchKey;
    private String email;
    private String emailId;
    private String emailCode;
    private String actType;
    private Date startTime;
    private Date endTime;
    private String userId;
}
