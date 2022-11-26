package cc.cdtime.lifecapsule_v3_api.meta.task.entity;

import lombok.Data;

/**
 * 使命任务（长期重要的任务）
 */
@Data
public class TaskMind {
    /**
     * 任务优先级
     */
    private Integer priority;
    private String important;
    private String status;
    private String taskType;
}
