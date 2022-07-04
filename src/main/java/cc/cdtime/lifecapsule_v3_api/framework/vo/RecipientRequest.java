package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

@Data
public class RecipientRequest {
    private String name;
    private String phone;
    private String email;
    private String triggerId;
    private String recipientId;
    private String remark;
    private String noteId;
    private String title;
    private String description;
    private String toName;
    private String fromName;
}
