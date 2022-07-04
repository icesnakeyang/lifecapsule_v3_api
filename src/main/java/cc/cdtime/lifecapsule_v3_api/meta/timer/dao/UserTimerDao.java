package cc.cdtime.lifecapsule_v3_api.meta.timer.dao;

import cc.cdtime.lifecapsule_v3_api.meta.timer.entity.TimerView;
import cc.cdtime.lifecapsule_v3_api.meta.timer.entity.UserTimer;
import cc.cdtime.lifecapsule_v3_api.meta.timer.entity.UserTimerLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface UserTimerDao {
    /**
     * 创建一个计时器
     *
     * @param userTimer
     */
    void createUserTimer(UserTimer userTimer);

    /**
     * 查询用户的一个计时器
     *
     * @param qIn timerId
     *            userId
     *            type
     * @return
     */
    TimerView getUserTimer(Map qIn);

    /**
     * 创建计时器日志
     *
     * @param userTimerLog
     */
    void createUserTimerLog(UserTimerLog userTimerLog);

    /**
     * 查询用户的触发时间列表
     *
     * @param qIn type
     * @return
     */
    ArrayList<TimerView> listUserTimer(Map qIn);

    /**
     * 修改用户的计时器
     *
     * @param qIn timerTime
     *            timerStatus
     *            remark
     *            tag
     *            snooze
     *            timerId
     */
    void updateUserTimer(Map qIn);
}
