package cc.cdtime.lifecapsule_v3_api.web.theme;

import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/web/theme")
public class WebThemeController {
    private final IWebThemeBService iWebThemeBService;

    public WebThemeController(IWebThemeBService iWebThemeBService) {
        this.iWebThemeBService = iWebThemeBService;
    }

    /**
     * web端用户查询主题颜色列表
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listTheme")
    public Response listTheme(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            if (token == null || token.equals("")) {
                throw new Exception("10029");
            }
            in.put("token", token);

            Map out = iWebThemeBService.listTheme(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web listTheme error:" + ex.getMessage());
            }
        }
        return response;
    }
}
