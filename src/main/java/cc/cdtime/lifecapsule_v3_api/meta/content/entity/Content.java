package cc.cdtime.lifecapsule_v3_api.meta.content.entity;

import lombok.Data;

/**
 * 内容详情表
 * 专用于存储大段文本内容
 */
@Data
public class Content {
    private Integer ids;
    private String indexId;
    private String content;
}
