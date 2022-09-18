package cc.cdtime.lifecapsule_v3_api.meta.tag.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TagView {
    private Integer ids;
    private String tagId;
    private String noteId;
    private Date createTime;
    private String tagName;
    private Integer tagHot;
}
