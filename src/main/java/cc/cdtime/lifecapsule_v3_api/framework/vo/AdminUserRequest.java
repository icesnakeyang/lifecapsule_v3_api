package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

import java.util.Date;

@Data
public class AdminUserRequest extends Request {
    private String loginName;
    private String password;
    private String roleType;
    private Date startTime;
    private Date endTime;
}
