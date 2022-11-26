package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

@Data
public class TaskRequest extends Request {
    private String taskId;
    private String title;
    private Integer priority;
    /**
     * 是否隐藏已完成任务
     */
    private Boolean hideComplete;
    private String content;
    private Boolean complete;
    private String keyToken;
    private String encryptKey;
}
