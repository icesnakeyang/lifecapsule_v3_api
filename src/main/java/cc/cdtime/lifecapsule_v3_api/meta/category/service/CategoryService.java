package cc.cdtime.lifecapsule_v3_api.meta.category.service;

import cc.cdtime.lifecapsule_v3_api.meta.category.dao.CategoryDao;
import cc.cdtime.lifecapsule_v3_api.meta.category.entity.Category;
import cc.cdtime.lifecapsule_v3_api.meta.category.entity.CategoryView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public void createCategory(Category category) throws Exception {
        categoryDao.createCategory(category);
    }

    @Override
    public CategoryView getCategory(Map qIn) throws Exception {
        CategoryView categoryView = categoryDao.getCategory(qIn);
        return categoryView;
    }

    @Override
    public ArrayList<CategoryView> listCategory(Map qIn) throws Exception {
        ArrayList<CategoryView> categoryViews = categoryDao.listCategory(qIn);
        return categoryViews;
    }

    @Override
    public void updateCategory(Map qIn) throws Exception {
        categoryDao.updateCategory(qIn);
    }

    @Override
    public void moveNoteCategory(Map qIn) throws Exception {
        categoryDao.moveNoteCategory(qIn);
    }

    @Override
    public void deleteCategory(String categoryId) throws Exception {
        categoryDao.deleteCategory(categoryId);
    }

    @Override
    public Integer totalCategory(Map qIn) throws Exception {
        Integer total = categoryDao.totalCategory(qIn);
        return total;
    }
}
