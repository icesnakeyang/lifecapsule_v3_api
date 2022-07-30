package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

import java.util.Date;

@Data
public class QuadTaskRequest extends Request {
    private String taskTitle;
    private String important;
    private Date endTime;
    private String encryptKey;
    private String keyToken;
    private String content;
    private String taskId;
    private Integer priority;
    private String status;
    private Boolean complete;
    private String taskType;
}
