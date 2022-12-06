package cc.cdtime.lifecapsule_v3_api.meta.antiDelay.entity;

import lombok.Data;

import java.util.Date;

@Data
public class AntiDelayView {
    private Integer ids;
    private String antiDelayType;
    private String antiDelayId;
    private String title;
    private String content;
    private String userEncodeKey;
    private String userId;
    private String noteId;
    private Date createTime;
    private String status;
    private String noteType;
}
