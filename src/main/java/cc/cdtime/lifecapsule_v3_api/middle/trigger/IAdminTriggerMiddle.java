package cc.cdtime.lifecapsule_v3_api.middle.trigger;

import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.TriggerView;

import java.util.ArrayList;
import java.util.Map;

public interface IAdminTriggerMiddle {
    /**
     * 管理后台读取所有触发器
     *
     * @param qIn triggerType
     *            status
     * @return
     */
    ArrayList<TriggerView> adminListTrigger(Map qIn) throws Exception;

    /**
     * 管理员修改笔记触发器
     *
     * @param qIn actTimes
     *            status
     *            triggerId
     */
    void updateNoteTrigger(Map qIn) throws Exception;
}
