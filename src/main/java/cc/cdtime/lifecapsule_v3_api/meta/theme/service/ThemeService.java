package cc.cdtime.lifecapsule_v3_api.meta.theme.service;

import cc.cdtime.lifecapsule_v3_api.meta.theme.dao.ThemeDao;
import cc.cdtime.lifecapsule_v3_api.meta.theme.entity.Theme;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class ThemeService implements IThemeService {
    private final ThemeDao themeDao;

    public ThemeService(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    @Override
    public void createTheme(Theme theme) throws Exception {
        themeDao.createTheme(theme);
    }

    @Override
    public Theme getTheme(Map qIn) throws Exception {
        Theme theme = themeDao.getTheme(qIn);
        return theme;
    }

    @Override
    public ArrayList<Theme> listTheme(Map qIn) throws Exception {
        ArrayList<Theme> themes = themeDao.listTheme(qIn);
        return themes;
    }

    @Override
    public void updateTheme(Map qIn) throws Exception {
        themeDao.updateTheme(qIn);
    }

    @Override
    public void setAllThemeStatusToActive(Map qIn) throws Exception {
        themeDao.setAllThemeStatusToActive(qIn);
    }
}
