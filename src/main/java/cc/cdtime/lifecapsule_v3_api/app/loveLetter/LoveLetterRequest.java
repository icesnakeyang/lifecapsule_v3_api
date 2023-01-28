package cc.cdtime.lifecapsule_v3_api.app.loveLetter;

import cc.cdtime.lifecapsule_v3_api.framework.vo.Request;
import lombok.Data;

import java.util.Date;

@Data
public class LoveLetterRequest extends Request {
    private String title;
    private String content;
    private String noteId;
    private String encryptKey;
    private String keyToken;
    private String toEmail;
    private String toName;
    private String fromName;
    private Date sendDateTime;
}
