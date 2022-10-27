package cc.cdtime.lifecapsule_v3_api.meta.motto.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MottoView {
    private Integer ids;
    private String mottoId;
    private String userId;
    private Date createTime;
    private String status;
    private Integer views;
    private Integer likes;
    private String noteId;
    private String authorName;
    private String content;
    private String logType;
}
