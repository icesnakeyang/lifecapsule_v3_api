package cc.cdtime.lifecapsule_v3_api.meta.category.entity;

import lombok.Data;

@Data
public class CategoryView {
    private Integer ids;
    private String categoryId;
    private String categoryName;
    private String userId;
    private String noteType;
    private String remark;
}
