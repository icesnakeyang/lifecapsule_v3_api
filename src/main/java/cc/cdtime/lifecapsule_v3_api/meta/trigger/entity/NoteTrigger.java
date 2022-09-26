package cc.cdtime.lifecapsule_v3_api.meta.trigger.entity;

import lombok.Data;

import java.util.Date;

/**
 * 触发器类
 */
@Data
public class NoteTrigger {
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
    private String noteContent;
    private String userEncodeKey;
    private String title;
    private String toEmail;
    private String fromName;
    private String toUserId;
    private String refPid;
}
