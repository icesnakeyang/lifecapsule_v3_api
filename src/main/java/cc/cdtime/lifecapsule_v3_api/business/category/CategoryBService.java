package cc.cdtime.lifecapsule_v3_api.business.category;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.category.entity.Category;
import cc.cdtime.lifecapsule_v3_api.meta.category.entity.CategoryView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.category.ICategoryMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CategoryBService implements ICategoryBService {
    private final IUserMiddle iUserMiddle;
    private final ICategoryMiddle iCategoryMiddle;

    public CategoryBService(IUserMiddle iUserMiddle,
                            ICategoryMiddle iCategoryMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iCategoryMiddle = iCategoryMiddle;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveCategory(Map in) throws Exception {
        String token = in.get("token").toString();
        String categoryName = (String) in.get("categoryName");
        String remark = (String) in.get("remark");
        String categoryId = (String) in.get("categoryId");
        String noteType = (String) in.get("noteType");

        if (categoryName == null && remark == null) {
            //新增或者修改笔记分类时，没有需要修改的数据
            throw new Exception("10011");
        }

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        if (categoryId == null) {
            /**
             * 新增
             */
            if (categoryName == null) {
                //必须指定笔记分类名称
                throw new Exception("10012");
            }
            /**
             * 检查该用户是否已经有该分类了
             */
            qIn = new HashMap();
            qIn.put("categoryName", categoryName);
            CategoryView categoryView = iCategoryMiddle.getCategory(qIn, true, userView.getUserId());
            if (categoryView != null) {
                //该用户已经有该笔记分类了
                throw new Exception("10013");
            }
            Category category = new Category();
            category.setCategoryId(GogoTools.UUID32());
            category.setCategoryName(categoryName);
            if (noteType == null) {
                noteType = ESTags.NORMAL.toString();
            }
            category.setNoteType(noteType);
            category.setUserId(userView.getUserId());
            category.setRemark(remark);
            iCategoryMiddle.createCategory(category);
        } else {
            /**
             * 修改
             */
            qIn = new HashMap();
            qIn.put("categoryId", categoryId);
            CategoryView categoryView = iCategoryMiddle.getCategory(qIn, false, userView.getUserId());

            qIn = new HashMap();
            int cc = 0;
            if (!categoryView.getCategoryName().equals(categoryName)) {
                qIn.put("categoryName", categoryName);
                cc++;
            }
            if (categoryView.getRemark() != null) {
                if (!categoryView.getRemark().equals(remark)) {
                    qIn.put("remark", remark);
                    cc++;
                }
            } else {
                if (remark != null) {
                    qIn.put("remark", remark);
                    cc++;
                }
            }
            if (categoryView.getNoteType() != null) {
                if (!categoryView.getNoteType().equals(noteType)) {
                    qIn.put("noteType", noteType);
                    cc++;
                }
            }
            if (cc > 0) {
                qIn.put("categoryId", categoryId);
                iCategoryMiddle.updateCategory(qIn);
            }
        }
    }

    @Override
    public void deleteCategory(Map in) throws Exception {
        String token = in.get("token").toString();
        String categoryId = in.get("categoryId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("categoryId", categoryId);
        CategoryView categoryView = iCategoryMiddle.getCategory(qIn, false, userView.getUserId());

        if (categoryView.getNoteType().equals(ESTags.CREATIVE_NOTE.toString())) {
            //防拖延笔记是系统预定义的专用笔记，用户不能删除
            throw new Exception("10014");
        }

        /**
         * 普通笔记，需要把该分类下的笔记转移到DEFAULT分类里去，修改note的categoryId
         */
        //获取当前用户的default分类id
        CategoryView defaultCategory = iCategoryMiddle.getDefaultCategory(userView.getUserId());
        qIn = new HashMap();
        qIn.put("categoryIdNew", defaultCategory.getCategoryId());
        qIn.put("categoryIdOld", categoryId);
        iCategoryMiddle.moveNoteCategory(qIn);

        //删除分类
        iCategoryMiddle.deleteCategory(categoryId);
    }

    @Override
    public Map listCategory(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");
        String categoryId = (String) in.get("categoryId");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);
        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        if (pageIndex != null) {
            Integer offset = (pageIndex - 1) * pageSize;
            qIn.put("offset", offset);
            qIn.put("size", pageSize);
        }
        if (categoryId != null) {
            qIn.put("categoryId", categoryId);
        }
        ArrayList<CategoryView> categories = iCategoryMiddle.listCategory(qIn);
        Integer totalCategory = iCategoryMiddle.totalCategory(qIn);

        Map out = new HashMap();
        out.put("categoryList", categories);
        out.put("totalCategory", totalCategory);

        return out;
    }

    @Override
    public Map getCategory(Map in) throws Exception {
        String token = in.get("token").toString();
        String categoryId = in.get("categoryId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("categoryId", categoryId);
        CategoryView categoryView = iCategoryMiddle.getCategory(qIn, false, userView.getUserId());

        Map out = new HashMap();
        out.put("category", categoryView);

        return out;
    }

    @Override
    public Map getMyDefaultCategory(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        CategoryView categoryView = iCategoryMiddle.getDefaultCategory(userView.getUserId());

        Map out = new HashMap();
        out.put("category", categoryView);

        return out;
    }
}
