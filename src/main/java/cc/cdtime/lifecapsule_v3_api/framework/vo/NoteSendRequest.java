package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

import java.util.Date;

@Data
public class NoteSendRequest extends Request {
    private String noteId;
    private String receiveUserId;
    private String searchKey;
    private String sendLogId;
    private String noteContent;
    private String email;
    private String title;
    private String encryptKey;
    private String keyToken;
}
