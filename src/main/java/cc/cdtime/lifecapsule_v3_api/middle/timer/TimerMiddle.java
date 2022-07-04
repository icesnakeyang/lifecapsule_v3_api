package cc.cdtime.lifecapsule_v3_api.middle.timer;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.timer.entity.TimerView;
import cc.cdtime.lifecapsule_v3_api.meta.timer.entity.UserTimer;
import cc.cdtime.lifecapsule_v3_api.meta.timer.entity.UserTimerLog;
import cc.cdtime.lifecapsule_v3_api.meta.timer.service.IUserTimerService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TimerMiddle implements ITimerMiddle {
    private final IUserTimerService iUserTimerService;

    public TimerMiddle(IUserTimerService iUserTimerService) {
        this.iUserTimerService = iUserTimerService;
    }

    @Override
    public Map createUserTimer(String userId) throws Exception {
        Map out = new HashMap();
        /**
         * 创建用户的主计时器
         */
        UserTimer userTimer = new UserTimer();
        userTimer.setUserId(userId);
        userTimer.setTimerId(GogoTools.UUID32());
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.DAY_OF_MONTH, 30);
        userTimer.setTimerTime(c1.getTime());
        out.put("timerTime", userTimer.getTimerTime().getTime());
        userTimer.setTimerStatus(ESTags.ACTIVE.toString());
        userTimer.setType(ESTags.TIMER_TYPE_PRIMARY.toString());
        iUserTimerService.createUserTimer(userTimer);

        /**
         * 创建计时器日志
         */
        UserTimerLog userTimerLog = new UserTimerLog();
        userTimerLog.setUserId(userId);
        userTimerLog.setTimerId(userTimer.getTimerId());
        userTimerLog.setNewTimer(userTimer.getTimerTime());
        userTimerLog.setSnoozeTime(new Date());
        iUserTimerService.createUserTimerLog(userTimerLog);

        return out;
    }

    @Override
    public TimerView getUserTimer(Map qIn, Boolean returnNull) throws Exception {
        TimerView timerView = iUserTimerService.getUserTimer(qIn);
        if (timerView == null) {
            if (returnNull) {
                return null;
            } else {
                //没有查询到该计时器
                throw new Exception("10042");
            }
        }
        return timerView;
    }

    @Override
    public void createUserTimerLog(UserTimerLog userTimerLog) throws Exception {
        iUserTimerService.createUserTimerLog(userTimerLog);
    }

    @Override
    public ArrayList<TimerView> listUserTimer(Map qIn) throws Exception {
        ArrayList<TimerView> timerViews = iUserTimerService.listUserTimer(qIn);
        return timerViews;
    }

    @Override
    public void updateUserTimer(Map qIn) throws Exception {
        iUserTimerService.updateUserTimer(qIn);
    }
}
