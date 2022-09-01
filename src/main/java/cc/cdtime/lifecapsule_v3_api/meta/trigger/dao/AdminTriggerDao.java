package cc.cdtime.lifecapsule_v3_api.meta.trigger.dao;

import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.TriggerView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface AdminTriggerDao {
    /**
     * 管理后台读取所有触发器
     *
     * @param qIn triggerType
     *            status
     * @return
     */
    ArrayList<TriggerView> adminListTrigger(Map qIn);

    /**
     * 管理员修改笔记触发器
     *
     * @param qIn actTimes
     *            status
     *            triggerId
     */
    void updateNoteTrigger(Map qIn);

}
