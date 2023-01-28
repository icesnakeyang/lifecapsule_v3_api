package cc.cdtime.lifecapsule_v3_api.app.trigger;

import java.util.Map;

public interface IAppTriggerBService {
    /**
     * App用户创建一个指定时间的触发器
     *
     * @param in
     * @return
     * @throws Exception
     */
    void createTriggerDatetime(Map in) throws Exception;

    /**
     * 用户查看笔记的触发器列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listMyTrigger(Map in) throws Exception;

    /**
     * 用户查看笔记的触发器详情
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getMyTriggerDetail(Map in) throws Exception;

    /**
     * 用户删除一个笔记的触发器
     *
     * @param in
     * @return
     * @throws Exception
     */
    void deleteMyNoteTrigger(Map in) throws Exception;

    /**
     * App用户创建一个立即发送的触发器，并发送笔记
     *
     * @param in
     * @throws Exception
     */
    void createTriggerInstant(Map in) throws Exception;

    /**
     * App用户指定一篇笔记随主倒计时结束发送
     *
     * @param in
     * @throws Exception
     */
    void createTriggerPrimary(Map in) throws Exception;

    Map getTriggerIdFromSendLog(Map in) throws Exception;
}
