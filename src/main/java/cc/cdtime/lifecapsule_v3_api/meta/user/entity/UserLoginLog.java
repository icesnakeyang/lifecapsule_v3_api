package cc.cdtime.lifecapsule_v3_api.meta.user.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户登录日志表
 */
@Data
public class UserLoginLog {
    private Integer ids;
    private String userId;
    /**
     * 移动设备的唯一ID
     */
    private String deviceCode;
    /**
     * 移动设备名称
     * 例如：Redmi 7
     */
    private String deviceName;
    /**
     * 设备登录时间
     */
    private Date loginTime;
    /**
     * 用户登录的终端
     */
    private String frontEnd;
}
