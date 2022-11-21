package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class CreativeNoteRequest {
    private String detail1;
    private String detail2;
    private String detail3;
    private String detail4;
    private String noteId;
    private String encryptKey;
    private String keyToken;
    /**
     * 10秒行动任务列表
     */
    private ArrayList tasks;
    private String noteTitle;

    private Integer pageIndex;
    private Integer pageSize;
    private Boolean complete;
}
