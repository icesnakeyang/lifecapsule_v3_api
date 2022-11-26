package cc.cdtime.lifecapsule_v3_api.meta.task.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TaskView {
    private Integer ids;
    private String userId;
    private String taskTitle;
    private Boolean complete;
    private Integer priority;
    private Date createTime;
    private String taskId;
    private String content;
    private String userEncodeKey;
    private String status;
}
