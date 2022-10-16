package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

import java.util.Date;

@Data
public class TriggerRequest extends Request{
    private String noteId;
    private String triggerId;
    private String toEmail;
    private String title;
    private Date sendTime;
    private String keyToken;
    private String encryptKey;
    private String noteContent;
    private String fromName;
    private String status;
}
