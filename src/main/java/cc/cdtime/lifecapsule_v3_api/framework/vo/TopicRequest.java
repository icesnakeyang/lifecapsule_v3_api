package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

@Data
public class TopicRequest extends Request{
    private String noteId;
    private String title;
    private String content;
    private String topicId;
    private String pid;
    private String comment;
    private String authorName;
}
