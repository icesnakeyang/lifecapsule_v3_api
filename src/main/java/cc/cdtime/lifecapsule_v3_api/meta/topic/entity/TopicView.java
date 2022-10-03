package cc.cdtime.lifecapsule_v3_api.meta.topic.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TopicView {
    private Integer ids;
    private String topicId;
    private Date createTime;
    private String userId;
    private String title;
    private Integer views;
    private Integer comments;
    private String status;
    private String pid;
    private String noteId;
    private String content;
    private String authorName;
}
