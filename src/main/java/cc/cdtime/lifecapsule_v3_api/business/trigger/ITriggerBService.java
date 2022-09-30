package cc.cdtime.lifecapsule_v3_api.business.trigger;

import java.util.Map;

public interface ITriggerBService {

    Map saveNoteTrigger(Map in) throws Exception;

//    Map saveNoteRecipientTrigger(Map in) throws Exception;

    /**
     * 根据recipientId读取一个触发条件
     *
     * @param in
     * @return
     * @throws Exception
     */
//    Map getNoteRecipientTrigger(Map in) throws Exception;

    Map listNoteTrigger(Map in) throws Exception;

    Map getTriggerDetail(Map in) throws Exception;

    void deleteNoteTrigger(Map in) throws Exception;

    void createTriggerInstant(Map in) throws Exception;

    /**
     * 用户保存一个笔记触发器，根据设置的时间来发送
     *
     * @param in
     * @throws Exception
     */
    void createNoteTriggerByDatetime(Map in) throws Exception;

    /**
     * 用户设置一篇笔记为主倒计时结束时发送
     * @param in
     * @throws Exception
     */
    void createNoteTriggerPrimary(Map in)throws Exception;
}
