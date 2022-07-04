package cc.cdtime.lifecapsule_v3_api.business.trigger;

import java.util.Map;

public interface ITriggerBService {

    Map listNoteTrigger(Map in) throws Exception;

    Map saveNoteTrigger(Map in) throws Exception;

    Map getNoteTrigger(Map in) throws Exception;
}
