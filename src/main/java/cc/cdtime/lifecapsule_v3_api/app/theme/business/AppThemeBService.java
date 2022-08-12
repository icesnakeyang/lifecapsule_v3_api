package cc.cdtime.lifecapsule_v3_api.app.theme.business;

import cc.cdtime.lifecapsule_v3_api.business.theme.IThemeBService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppThemeBService implements IAppThemeBService {
    private final IThemeBService iThemeBService;

    public AppThemeBService(IThemeBService iThemeBService) {
        this.iThemeBService = iThemeBService;
    }

    @Override
    public Map listTheme(Map in) throws Exception {
        in.put("themeType", ESTags.MOBILE_CLIENT.toString());
        Map out = iThemeBService.listTheme(in);
        return out;
    }
}
