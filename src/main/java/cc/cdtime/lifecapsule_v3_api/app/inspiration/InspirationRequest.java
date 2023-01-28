package cc.cdtime.lifecapsule_v3_api.app.inspiration;

import cc.cdtime.lifecapsule_v3_api.framework.vo.Request;
import lombok.Data;

@Data
public class InspirationRequest extends Request {
    private String noteId;
    private String content;
    private String encryptKey;
    private String keyToken;
    private String title;
}
