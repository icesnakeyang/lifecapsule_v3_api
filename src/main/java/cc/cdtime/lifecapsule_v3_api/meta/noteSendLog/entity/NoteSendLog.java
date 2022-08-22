package cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity;

import lombok.Data;

import java.util.Date;

/**
 * 主动发送笔记日志
 */
@Data
public class NoteSendLog {
    private Integer ids;
    private String sendLogId;
    private String sendUserId;
    private String receiveUserId;
    private Date sendTime;
    private Date readTime;
    private String noteContent;
    private String sendPhone;
    private String sendEmail;
    private String title;
}