package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

@Data
public class HistoryRequest extends Request {
    private String content;
    private String pid;
    private String title;
    private String encryptKey;
    private String keyToken;
    private String searchKey;
}
