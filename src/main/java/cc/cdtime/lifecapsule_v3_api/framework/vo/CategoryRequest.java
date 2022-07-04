package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

/**
 * 笔记分类参数
 */
@Data
public class CategoryRequest extends Request{
    private String categoryId;
    private String categoryName;
    private String remark;
}
