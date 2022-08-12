package cc.cdtime.lifecapsule_v3_api.app.theme.business;

import java.util.Map;

public interface IAppThemeBService {
    /**
     * 读取手机端主题列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listTheme(Map in) throws Exception;
}
