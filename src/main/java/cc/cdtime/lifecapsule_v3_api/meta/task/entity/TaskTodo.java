package cc.cdtime.lifecapsule_v3_api.meta.task.entity;

import lombok.Data;

import java.util.Date;

/**
 * todolist
 */
@Data
public class TaskTodo {
    private Integer ids;
    private String taskId;
    private String userId;
    private String taskTitle;
    private String content;
    private Date createTime;
    private Integer priority;
    private Boolean complete;
    private String userEncodeKey;
    private String noteId;
    private String taskType;
    private String projectId;
}
