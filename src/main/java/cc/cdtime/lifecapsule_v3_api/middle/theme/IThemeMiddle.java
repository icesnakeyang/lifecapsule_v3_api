package cc.cdtime.lifecapsule_v3_api.middle.theme;

import cc.cdtime.lifecapsule_v3_api.meta.theme.entity.Theme;

import java.util.ArrayList;
import java.util.Map;

public interface IThemeMiddle {
    void createTheme(Theme theme) throws Exception;

    /**
     * 读取一个主题
     *
     * @param qIn themeId
     *            defaultTheme
     * @return
     */
    Theme getTheme(Map qIn, Boolean returnNull) throws Exception;

    /**
     * 查询主题列表
     *
     * @param qIn themeType
     * @return
     */
    ArrayList<Theme> listTheme(Map qIn) throws Exception;

    /**
     * 修改主题颜色
     *
     * @param qIn background
     *            blockDark
     *            blockLight
     *            textDark
     *            textLight
     *            textHolder
     *            themeName
     *            themeId
     */
    void updateTheme(Map qIn) throws Exception;

    void setAllThemeStatusToActive(Map qIn) throws Exception;
}
