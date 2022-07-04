package cc.cdtime.lifecapsule_v3_api.meta.task.entity;

import lombok.Data;

import java.util.Date;

/**
 * 待办任务
 */
@Data
public class Task {
    private Integer ids;
    private String taskId;
    private String userId;
    private String taskTitle;
    private Date createTime;
    /**
     * 任务优先级
     */
    private Integer priority;
    private String important;
    private String status;
    private String taskType;
    /**
     * 父任务
     */
    private String pid;
    private Date endTime;
    private String noteId;
    private String userEncodeKey;
    private String content;
    private Boolean complete = false;
    private Integer importantLevel;
}
