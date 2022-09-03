package cc.cdtime.lifecapsule_v3_api.middle.theme;

import cc.cdtime.lifecapsule_v3_api.meta.theme.entity.Theme;
import cc.cdtime.lifecapsule_v3_api.meta.theme.service.IThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class ThemeMiddle implements IThemeMiddle {
    private final IThemeService iThemeService;

    public ThemeMiddle(IThemeService iThemeService) {
        this.iThemeService = iThemeService;
    }

    @Override
    public void createTheme(Theme theme) throws Exception {
        iThemeService.createTheme(theme);
    }

    @Override
    public Theme getTheme(Map qIn, Boolean returnNull) throws Exception {
        Theme theme = iThemeService.getTheme(qIn);
        if (theme == null) {
            if (returnNull) {
                return null;
            }
            //没有查询到该主题
            throw new Exception("10035");
        }
        return theme;
    }

    @Override
    public ArrayList<Theme> listTheme(Map qIn) throws Exception {
        ArrayList<Theme> themes = iThemeService.listTheme(qIn);
        return themes;
    }

    @Override
    public void updateTheme(Map qIn) throws Exception {
        iThemeService.updateTheme(qIn);
    }

    @Override
    public void setAllThemeStatusToActive(Map qIn) throws Exception {
        iThemeService.setAllThemeStatusToActive(qIn);
    }
}
