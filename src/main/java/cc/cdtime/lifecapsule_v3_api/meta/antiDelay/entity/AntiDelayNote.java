package cc.cdtime.lifecapsule_v3_api.meta.antiDelay.entity;

import lombok.Data;

/**
 * 防拖延笔记
 */
@Data
public class AntiDelayNote {
    private Integer ids;
    private String antiDelayType;
    private String antiDelayId;
    private String noteId;
    private String content;
    private String userEncodeKey;
}
