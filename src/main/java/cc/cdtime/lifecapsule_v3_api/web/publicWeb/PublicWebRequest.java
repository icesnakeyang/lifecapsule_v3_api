package cc.cdtime.lifecapsule_v3_api.web.publicWeb;

import cc.cdtime.lifecapsule_v3_api.framework.vo.Request;
import lombok.Data;

@Data
public class PublicWebRequest extends Request {
    private String title;
    private String content;
    private String noteId;
    private String authorName;
}
