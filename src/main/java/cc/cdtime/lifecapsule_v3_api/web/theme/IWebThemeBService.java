package cc.cdtime.lifecapsule_v3_api.web.theme;

import java.util.Map;

public interface IWebThemeBService {
    /**
     * web端用户查询主题颜色列表
     * @param in
     * @return
     * @throws Exception
     */
    Map listTheme(Map in) throws Exception;
}
