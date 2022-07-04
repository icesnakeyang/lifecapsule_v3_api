package cc.cdtime.lifecapsule_v3_api.business.category;

import java.util.Map;

public interface ICategoryBService {
    /**
     * 保存或者创建一个笔记分类
     * @param in
     * @throws Exception
     */
    void saveCategory(Map in) throws Exception;

    void deleteCategory(Map in) throws Exception;

    Map listCategory(Map in) throws Exception;

    Map getCategory(Map in) throws Exception;

    Map getMyDefaultCategory(Map in) throws Exception;
}
