package cc.cdtime.lifecapsule_v3_api.meta.creativeNote.entity;

import lombok.Data;

/**
 * 防拖延笔记
 */
@Data
public class CreativeNote {
    private Integer ids;
    private String creativeNoteId;
    private String noteId;
    private String creativeType;
    private String content;
    private String title;
    private String userEncodeKey;
}
