package cc.cdtime.lifecapsule_v3_api.app.timer.business;

import java.util.Map;

public interface IAppTimerBService {
    /**
     * 用户重置主倒计时
     *
     * @param in
     * @throws Exception
     */
    Map snooze(Map in) throws Exception;
}
