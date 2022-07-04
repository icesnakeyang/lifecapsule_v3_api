package cc.cdtime.lifecapsule_v3_api.meta.timer.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户的倒计时器
 */
@Data
public class UserTimer {
    private Integer ids;
    private String timerId;
    private String userId;
    private Date timerTime;
    private String timerStatus;
    private String remark;
    private String tag;
    /**
     * 用户每次点snooze，snooze++
     */
    private Integer snooze;
    /**
     * 计时器类型
     */
    private String type;
}
