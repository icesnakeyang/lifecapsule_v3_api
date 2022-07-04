package cc.cdtime.lifecapsule_v3_api.meta.timer.service;

import cc.cdtime.lifecapsule_v3_api.meta.timer.dao.UserTimerDao;
import cc.cdtime.lifecapsule_v3_api.meta.timer.entity.TimerView;
import cc.cdtime.lifecapsule_v3_api.meta.timer.entity.UserTimer;
import cc.cdtime.lifecapsule_v3_api.meta.timer.entity.UserTimerLog;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class UserTimerService implements IUserTimerService {
    private final UserTimerDao userTimerDao;

    public UserTimerService(UserTimerDao userTimerDao) {
        this.userTimerDao = userTimerDao;
    }

    @Override
    public void createUserTimer(UserTimer userTimer) throws Exception {
        userTimerDao.createUserTimer(userTimer);
    }

    @Override
    public TimerView getUserTimer(Map qIn) throws Exception {
        TimerView timerView = userTimerDao.getUserTimer(qIn);
        return timerView;
    }

    @Override
    public void createUserTimerLog(UserTimerLog userTimerLog) throws Exception {
        userTimerDao.createUserTimerLog(userTimerLog);
    }

    @Override
    public ArrayList<TimerView> listUserTimer(Map qIn) throws Exception {
        ArrayList<TimerView> timerViews = userTimerDao.listUserTimer(qIn);
        return timerViews;
    }

    @Override
    public void updateUserTimer(Map qIn) throws Exception {
        userTimerDao.updateUserTimer(qIn);
    }
}
