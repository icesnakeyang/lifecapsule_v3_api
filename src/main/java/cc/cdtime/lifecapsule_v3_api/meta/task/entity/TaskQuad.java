package cc.cdtime.lifecapsule_v3_api.meta.task.entity;

import lombok.Data;

import java.util.Date;

/**
 * 四象限任务
 */
@Data
public class TaskQuad{
    private Integer ids;
    private String taskId;
    private String userId;
    private String taskTitle;
    private String content;
    private Date createTime;
    private Integer priority;
    private String important;
    private String status;
    private Integer importantLevel;
    private Date endTime;
    private String userEncodeKey;
}
