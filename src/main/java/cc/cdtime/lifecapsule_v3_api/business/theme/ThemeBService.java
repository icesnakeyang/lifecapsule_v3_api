package cc.cdtime.lifecapsule_v3_api.business.theme;

import cc.cdtime.lifecapsule_v3_api.meta.theme.entity.Theme;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.theme.IThemeMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ThemeBService implements IThemeBService {
    private final IUserMiddle iUserMiddle;
    private final IThemeMiddle iThemeMiddle;

    public ThemeBService(IUserMiddle iUserMiddle, IThemeMiddle iThemeMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iThemeMiddle = iThemeMiddle;
    }

    @Override
    public Map listTheme(Map in) throws Exception {
        String token = in.get("token").toString();
        String themeType=in.get("themeType").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        ArrayList<Theme> themes = iThemeMiddle.listTheme(qIn);

        Map out = new HashMap();
        out.put("themeList", themes);

        return out;
    }

    @Override
    public Map getTheme(Map in) throws Exception {
        String token = in.get("token").toString();
        String themeId = in.get("themeId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Theme theme = iThemeMiddle.getTheme(themeId, false);

        Map out = new HashMap();
        out.put("theme", theme);

        return out;
    }
}
