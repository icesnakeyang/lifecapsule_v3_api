package cc.cdtime.lifecapsule_v3_api.app.trigger.business;

import java.util.Map;

public interface IAppTriggerBService {
    /**
     * 用户查看笔记的触发器
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listNoteTrigger(Map in) throws Exception;

    /**
     * 用户保存笔记的触发器
     *
     * @param in
     * @return
     */
    Map saveNoteTrigger(Map in) throws Exception;
}
