package cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity;

import lombok.Data;

import java.util.Date;

@Data
public class NoteSendLogView {
        private Integer ids;
    private String sendLogId;
    //    private String noteId;
    private String sendUserId;
    private String receiveUserId;
    private Date sendTime;
    //    private String sendLoginName;
//    private String receiveLoginName;
//    private String noteTitle;
    private String content;
    private String userEncodeKey;
    private String sendUserNickname;
    //    private String receiveUserNickname;
    private Date readTime;
    //    private String sendPhone;
    private String toEmail;
    private String title;
    private String recipientId;
    private String triggerType;
    private String recipientName;
    private String toName;
    private String recipientTitle;
    private String fromName;
    private String description;
    private String recipientEmail;
    private String recipientRemark;
    private String refPid;

}
