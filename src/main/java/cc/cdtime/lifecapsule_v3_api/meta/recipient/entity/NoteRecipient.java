package cc.cdtime.lifecapsule_v3_api.meta.recipient.entity;

import lombok.Data;

/**
 * 一个笔记要发送给的接收人
 */
@Data
public class NoteRecipient {
    private Integer ids;
    private String noteId;
    private String triggerId;
    private String recipientName;
    private String phone;
    private String email;
    private String remark;
    private String status;
    private String recipientId;
    private String userId;
    private String description;
    private String title;
    private String fromName;
    private String toName;
}
