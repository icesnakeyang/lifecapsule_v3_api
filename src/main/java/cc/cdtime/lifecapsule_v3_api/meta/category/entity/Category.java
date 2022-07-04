package cc.cdtime.lifecapsule_v3_api.meta.category.entity;

import lombok.Data;

/**
 * 笔记分类
 */
@Data
public class Category {
    private Integer ids;
    private String categoryId;
    private String categoryName;
    private String userId;
    private String noteType;
    private String remark;
}
