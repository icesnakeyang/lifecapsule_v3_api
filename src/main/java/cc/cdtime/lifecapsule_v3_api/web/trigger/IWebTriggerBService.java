package cc.cdtime.lifecapsule_v3_api.web.trigger;

import java.util.Map;

public interface IWebTriggerBService {

    void createTriggerInstant(Map in) throws Exception;

    Map listMyTriggerQue(Map in) throws Exception;
}
