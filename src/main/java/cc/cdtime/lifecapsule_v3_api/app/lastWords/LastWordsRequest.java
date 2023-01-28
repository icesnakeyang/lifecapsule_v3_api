package cc.cdtime.lifecapsule_v3_api.app.lastWords;

import cc.cdtime.lifecapsule_v3_api.framework.vo.Request;
import lombok.Data;

@Data
public class LastWordsRequest extends Request {
    private String toEmail;
    private String content;
    private String encryptKey;
    private String keyToken;
    private String title;
    private String toName;
    private String fromName;
    private String noteId;
}
