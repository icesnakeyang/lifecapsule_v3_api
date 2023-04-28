package cc.cdtime.lifecapsule_v3_api.meta.notePublic;

import lombok.Data;

import java.util.Date;

@Data
public class NotePublicView {
    private Integer ids;
    private String title;
    private String content;
    private String userId;
    private Date createTime;
    private String noteId;
    private Integer views;
    private Integer comments;
    private String authorName;
}
