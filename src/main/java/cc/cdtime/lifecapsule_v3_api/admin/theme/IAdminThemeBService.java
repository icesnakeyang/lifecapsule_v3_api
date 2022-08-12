package cc.cdtime.lifecapsule_v3_api.admin.theme;

import java.util.Map;

public interface IAdminThemeBService {
    /**
     * 管理员查询web端的主题列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listWebTheme(Map in) throws Exception;

    /**
     * 管理员新增一个Web主题
     *
     * @param in
     * @throws Exception
     */
    void createWebTheme(Map in) throws Exception;

    Map getWebTheme(Map in) throws Exception;

    /**
     * 管理员修改一个Web主题
     *
     * @param in
     * @throws Exception
     */
    void updateWebTheme(Map in) throws Exception;

    void createAppTheme(Map in) throws Exception;

    void updateAppTheme(Map in) throws Exception;

    /**
     * 管理员查询App端的主题列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listAppTheme(Map in) throws Exception;

    /**
     * 管理员查询App端的主题详情
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getAppTheme(Map in) throws Exception;
}
