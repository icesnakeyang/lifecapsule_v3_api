package cc.cdtime.lifecapsule_v3_api.web.theme;

import cc.cdtime.lifecapsule_v3_api.business.theme.IThemeBService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebThemeBService implements IWebThemeBService {
    private final IThemeBService iThemeBService;

    public WebThemeBService(IThemeBService iThemeBService) {
        this.iThemeBService = iThemeBService;
    }

    @Override
    public Map listTheme(Map in) throws Exception {
        in.put("themeType", ESTags.WEB_CLIENT);
        Map out = iThemeBService.listTheme(in);
        return out;
    }
}
