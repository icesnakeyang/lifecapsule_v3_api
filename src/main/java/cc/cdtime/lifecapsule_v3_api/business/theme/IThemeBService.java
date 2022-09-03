package cc.cdtime.lifecapsule_v3_api.business.theme;

import java.util.Map;

public interface IThemeBService {
    Map listTheme(Map in) throws Exception;
    Map getTheme(Map in) throws Exception;

    Map getDefaultTheme(Map in) throws Exception;
}
