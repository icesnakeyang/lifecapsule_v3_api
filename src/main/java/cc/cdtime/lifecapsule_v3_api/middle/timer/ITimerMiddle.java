package cc.cdtime.lifecapsule_v3_api.middle.timer;


import cc.cdtime.lifecapsule_v3_api.meta.timer.entity.TimerView;
import cc.cdtime.lifecapsule_v3_api.meta.timer.entity.UserTimerLog;

import java.util.ArrayList;
import java.util.Map;

public interface ITimerMiddle {
    /**
     * 创建一个计时器（包含计时器日志）
     * @param userId
     * @return
     * timerTime
     * @throws Exception
     */
    Map createUserTimer(String userId) throws Exception;

    /**
     * 查询用户的一个计时器
     *
     * @param qIn timerId
     *            userId
     *            type
     * @return
     */
    TimerView getUserTimer(Map qIn, Boolean returnNull) throws Exception;

    /**
     * 创建计时器日志
     *
     * @param userTimerLog
     */
    void createUserTimerLog(UserTimerLog userTimerLog) throws Exception;

    /**
     * 查询用户的触发时间列表
     *
     * @param qIn type
     * @return
     */
    ArrayList<TimerView> listUserTimer(Map qIn) throws Exception;

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
    void updateUserTimer(Map qIn) throws Exception;
}
