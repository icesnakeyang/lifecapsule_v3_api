package cc.cdtime.lifecapsule_v3_api.meta.note.entity;

import lombok.Data;

import java.util.Date;

/**
 * 笔记类
 */
@Data
public class NoteInfo {
    private Integer ids;
    private String noteId;
    private String userId;
    private String title;
    private Date createTime;
    private String status;
    private Integer encrypt;
    private String userEncodeKey;
    private String categoryId;
    private String content;
}
