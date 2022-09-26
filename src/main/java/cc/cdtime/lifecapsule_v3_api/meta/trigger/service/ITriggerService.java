package cc.cdtime.lifecapsule_v3_api.meta.trigger.service;

import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.NoteTrigger;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.TriggerView;

import java.util.ArrayList;
import java.util.Map;

public interface ITriggerService {
    /**
     * 创建一个触发器
     *
     * @param trigger
     */
    void createTrigger(NoteTrigger trigger) throws Exception;

    /**
     * 查询一个触发器
     *
     * @param triggerId
     * @return
     */
    TriggerView getTrigger(String triggerId) throws Exception;

    /**
     * 查询触发器列表
     *
     * @param qIn userId
     *            status
     *            size
     *            offset
     * @return
     */
    ArrayList<TriggerView> listTrigger(Map qIn) throws Exception;

    Integer totalTrigger(Map qIn) throws Exception;

    /**
     * 修改一个笔记触发条件
     *
     * @param qIn remark
     *            triggerTime
     *            recipientId
     *            actTimes
     *            triggerType
     *            status
     *            triggerId
     */
    void updateNoteTrigger(Map qIn) throws Exception;

    /**
     * 物理删除触发器
     *
     * @param triggerId
     */
    void deleteTrigger(String triggerId) throws Exception;
}
