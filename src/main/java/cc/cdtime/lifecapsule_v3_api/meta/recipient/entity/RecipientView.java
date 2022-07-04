package cc.cdtime.lifecapsule_v3_api.meta.recipient.entity;

import lombok.Data;

@Data
public class RecipientView {
    private Integer ids;
    private String noteId;
    private String triggerId;
    private String recipientName;
    private String phone;
    private String email;
    private String recipientRemark;
    private String recipientId;
    private String userId;
    private String triggerRemark;
    private String triggerTime;
    private String actTime;
    private String triggerType;
    private String description;
    private String title;
    private String fromName;
    private String toName;
}
