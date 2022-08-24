package cc.cdtime.lifecapsule_v3_api.app.noteTrigger;

import java.util.Map;

public interface IAppNoteTriggerBService {
    /**
     * App端用户查询自己的触发笔记列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listMyNoteTrigger(Map in) throws Exception;
}
