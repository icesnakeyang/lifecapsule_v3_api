package cc.cdtime.lifecapsule_v3_api.business.trigger;

import java.util.Map;

public interface ITriggerBService {

    Map listNoteTrigger(Map in) throws Exception;

    Map saveNoteTrigger(Map in) throws Exception;

    Map saveNoteRecipientTrigger(Map in) throws Exception;

    /**
     * 根据recipientId读取一个触发条件
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getNoteRecipientTrigger(Map in) throws Exception;
}
