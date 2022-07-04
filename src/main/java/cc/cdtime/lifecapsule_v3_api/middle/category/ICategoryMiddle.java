package cc.cdtime.lifecapsule_v3_api.middle.category;

import cc.cdtime.lifecapsule_v3_api.meta.category.entity.Category;
import cc.cdtime.lifecapsule_v3_api.meta.category.entity.CategoryView;

import java.util.ArrayList;
import java.util.Map;

public interface ICategoryMiddle {
    /**
     * 创建一个笔记分类
     *
     * @param category
     */
    void createCategory(Category category) throws Exception;

    /**
     * 查询一条笔记分类记录
     *
     * @param qIn categoryId
     *            categoryName
     *            userId
     * @return
     */
    CategoryView getCategory(Map qIn, Boolean returnNull, String userId) throws Exception;

    /**
     * 查询笔记分类列表
     *
     * @param qIn userId
     *            categoryName
     *            noteType
     *            offset
     * @return
     */
    ArrayList<CategoryView> listCategory(Map qIn) throws Exception;

    /**
     * 修改笔记分类信息
     *
     * @param qIn categoryName
     *            noteType
     *            categoryId
     */
    void updateCategory(Map qIn) throws Exception;

    CategoryView getDefaultCategory(String userId) throws Exception;

    /**
     * 批量修改note的分类
     *
     * @param qIn categoryIdNew
     *            categoryIdOld
     */
    void moveNoteCategory(Map qIn) throws Exception;

    /**
     * 物理删除笔记分类
     *
     * @param categoryId
     */
    void deleteCategory(String categoryId) throws Exception;

    /**
     * 统计笔记分类总数
     *
     * @param qIn userId
     * @return
     */
    Integer totalCategory(Map qIn) throws Exception;
}
