package cc.cdtime.lifecapsule_v3_api.business.timer;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.meta.timer.entity.TimerView;
import cc.cdtime.lifecapsule_v3_api.meta.timer.entity.UserTimerLog;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.timer.ITimerMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TimerBService implements ITimerBService {
    private final IUserMiddle iUserMiddle;
    private final ITimerMiddle iTimerMiddle;

    public TimerBService(IUserMiddle iUserMiddle,
                         ITimerMiddle iTimerMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iTimerMiddle = iTimerMiddle;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map snooze(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * 重置主计时器
         */
        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        qIn.put("type", ESTags.TIMER_TYPE_PRIMARY);
        //读取当前的主计时器
        TimerView timerView = iTimerMiddle.getUserTimer(qIn, false);
        //当前时间+30天
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.DAY_OF_MONTH, 30);
        //保存计时器日志
        UserTimerLog userTimerLog = new UserTimerLog();
        userTimerLog.setTimerId(timerView.getTimerId());
        userTimerLog.setSnoozeTime(new Date());
        userTimerLog.setNewTimer(c1.getTime());
        userTimerLog.setUserId(userView.getUserId());
        userTimerLog.setOldTimer(timerView.getTimerTime());
        iTimerMiddle.createUserTimerLog(userTimerLog);
        //修改主计时器
        qIn = new HashMap();
        qIn.put("timerTime", c1.getTime());
        qIn.put("timerStatus", ESTags.ACTIVE);
        qIn.put("timerId", timerView.getTimerId());
        iTimerMiddle.updateUserTimer(qIn);

        Map out=new HashMap();
        out.put("timerPrimary",c1.getTimeInMillis());

        return out;
    }
}
