package cc.cdtime.lifecapsule_v3_api.middle.category;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.category.entity.Category;
import cc.cdtime.lifecapsule_v3_api.meta.category.entity.CategoryView;
import cc.cdtime.lifecapsule_v3_api.meta.category.service.ICategoryService;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.meta.user.service.IUserBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CategoryMiddle implements ICategoryMiddle {
    private final ICategoryService iCategoryService;
    private final IUserBaseService iUserBaseService;

    public CategoryMiddle(ICategoryService iCategoryService,
                          IUserBaseService iUserBaseService) {
        this.iCategoryService = iCategoryService;
        this.iUserBaseService = iUserBaseService;
    }

    @Override
    public void createCategory(Category category) throws Exception {
        iCategoryService.createCategory(category);
    }

    @Override
    public CategoryView getCategory(Map qIn, Boolean returnNull, String userId) throws Exception {
        CategoryView categoryView = iCategoryService.getCategory(qIn);
        if (categoryView == null) {
            if (returnNull) {
                return null;
            }
            //没有查询到该笔记分类
            throw new Exception("10009");
        }
        if (userId != null) {
            if (!userId.equals(categoryView.getUserId())) {
                //该笔记分类不属于当前用户
                throw new Exception("10010");
            }
        }
        return categoryView;
    }

    @Override
    public ArrayList<CategoryView> listCategory(Map qIn) throws Exception {
        ArrayList<CategoryView> categoryViews = iCategoryService.listCategory(qIn);
        return categoryViews;
    }

    @Override
    public void updateCategory(Map qIn) throws Exception {
        iCategoryService.updateCategory(qIn);
    }

    @Override
    public CategoryView getDefaultCategory(String userId) throws Exception {
        Map qIn = new HashMap();
        qIn.put("userId", userId);
        qIn.put("categoryName", ESTags.DEFAULT);
        CategoryView categoryView = iCategoryService.getCategory(qIn);
        if (categoryView == null) {
            /**
             * 如果当前用户还没有默认分类，就创建一个
             */
            UserView userView = iUserBaseService.getUserBase(userId);
            if (userView == null) {
                //没有查询到用户信息
                throw new Exception("10002");
            }
            //创建一个default分类
            Category category = new Category();
            category.setCategoryId(GogoTools.UUID32());
            category.setCategoryName(ESTags.DEFAULT.toString());
            category.setUserId(userId);
            category.setNoteType(ESTags.NORMAL.toString());
            iCategoryService.createCategory(category);

            categoryView = new CategoryView();
            categoryView.setCategoryId(category.getCategoryId());
            categoryView.setCategoryName(category.getCategoryName());
            categoryView.setUserId(category.getUserId());
            categoryView.setNoteType(category.getNoteType());
        }
        return categoryView;
    }

    @Override
    public void moveNoteCategory(Map qIn) throws Exception {
        iCategoryService.moveNoteCategory(qIn);
    }

    @Override
    public void deleteCategory(String categoryId) throws Exception {
        iCategoryService.deleteCategory(categoryId);
    }

    @Override
    public Integer totalCategory(Map qIn) throws Exception {
        Integer total = iCategoryService.totalCategory(qIn);
        return total;
    }
}
