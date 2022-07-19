package cc.cdtime.lifecapsule_v3_api.web.trigger;

import java.util.Map;

public interface IWebTriggerBService {
    /**
     * 保存一个接收人的触发条件
     *
     * @param in
     * @throws Exception
     */
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
