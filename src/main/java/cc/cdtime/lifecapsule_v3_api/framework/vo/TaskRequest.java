package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

@Data
public class TaskRequest extends Request {
    private String taskId;
    private String title;
    private Integer priority;
    private Boolean complete;
    private String content;
}
