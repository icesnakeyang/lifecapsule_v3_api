package cc.cdtime.lifecapsule_v3_api.meta.trigger.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TriggerView {
    private Integer ids;
    private String triggerId;
    private String noteId;
    private String remark;
    private Date createTime;
    private Date triggerTime;
    private String userId;
    private Integer actTimes;
    private String triggerType;
    private String status;
    private String recipientId;
}
