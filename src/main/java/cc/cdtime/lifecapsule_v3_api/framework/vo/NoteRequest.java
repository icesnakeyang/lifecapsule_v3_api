package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

/**
 * 笔记类的请求参数
 */
@Data
public class NoteRequest extends Request {
    private String categoryId;
    private String keyword;
    private String noteId;
    private String keyToken;
    private String encryptKey;
    private String title;
    private String content;
    private Integer encrypt;
    private String triggerId;
    private String triggerType;
    private Date triggerTime;
    private String remark;
    private String name;
    private String phone;
    private String email;
    private Integer ids;
    private ArrayList tagList;
}
