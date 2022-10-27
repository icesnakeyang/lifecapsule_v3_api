package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

@Data
public class MottoRequest {
    private String noteId;
    private String content;
    private String authorName;
    private String mottoId;
}
