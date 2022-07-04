package cc.cdtime.lifecapsule_v3_api.meta.task.entity;

import lombok.Data;

import java.util.Date;

/**
 * todoList task
 */
@Data
public class TaskTodo {
    private Integer ids;
    private String userId;
    private String title;
    private Boolean complete;
    private Integer priority;
    private Date createTime;
    private String taskId;
    private String content;
}
