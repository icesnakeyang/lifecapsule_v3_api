package cc.cdtime.lifecapsule_v3_api.web.timer;

import java.util.Map;

public interface IWebTimerBService {
    /**
     * 用户重置主倒计时
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map snooze(Map in) throws Exception;
}
