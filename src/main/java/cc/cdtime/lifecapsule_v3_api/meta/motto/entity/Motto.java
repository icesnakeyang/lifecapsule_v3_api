package cc.cdtime.lifecapsule_v3_api.meta.motto.entity;

import lombok.Data;

import java.util.Date;

/**
 * 格言精句
 */
@Data
public class Motto {
    private Integer ids;
    private String mottoId;
    private String userId;
    private Date createTime;
    private String status;
    private Integer views;
    private Integer likes;
    private String noteId;
    private String content;
    private String authorName;
}
