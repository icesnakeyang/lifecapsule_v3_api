package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

@Data
public class InboxRequest extends Request{
    private String sendLogId;
    private String encryptKey;
    private String keyToken;
    private String pid;
    private String title;
    private String content;
}
