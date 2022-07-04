package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

import java.util.Date;

@Data
public class TriggerRequest {
    private String noteId;
    private String triggerId;
    private String recipientId;
    private String remark;
    private String triggerType;
    private Date triggerTime;
    private String fromName;
}
