package cc.cdtime.lifecapsule_v3_api.meta.timer.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户snooze操作的日志
 */
@Data
public class UserTimerLog {
    private Integer ids;
    private String timerId;
    private String userId;
    /**
     * 旧的计时器启动时间
     */
    private Date oldTimer;
    /**
     * snooze时间
     */
    private Date snoozeTime;
    /**
     * 新的计时器启动时间
     */
    private Date newTimer;
}
