package cc.cdtime.lifecapsule_v3_api.admin.theme;

import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import cc.cdtime.lifecapsule_v3_api.framework.vo.ThemeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/admin/theme")
public class AdminThemeController {
    private final IAdminThemeBService iAdminThemeBService;

    public AdminThemeController(IAdminThemeBService iAdminThemeBService) {
        this.iAdminThemeBService = iAdminThemeBService;
    }

    /**
     * 管理员查询web端的主题列表
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listWebTheme")
    public Response listWebTheme(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            Map out = iAdminThemeBService.listWebTheme(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin listWebTheme error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 管理员查询web端的主题详情
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getWebTheme")
    public Response getWebTheme(@RequestBody ThemeRequest request, HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("themeId", request.getThemeId());

            Map out = iAdminThemeBService.getWebTheme(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin getWebTheme error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 管理员新增一个Web主题
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/createWebTheme")
    public Response createWebTheme(@RequestBody ThemeRequest request,
                                   HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("themeName", request.getThemeName());
            in.put("background", request.getBackground());
            in.put("blockDark", request.getBlockDark());
            in.put("blockLight", request.getBlockLight());
            in.put("textDark", request.getTextDark());
            in.put("textLight", request.getTextLight());
            in.put("textHolder", request.getTextHolder());

            iAdminThemeBService.createWebTheme(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin createWebTheme error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 管理员修改一个Web主题
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/updateWebTheme")
    public Response updateWebTheme(@RequestBody ThemeRequest request,
                                   HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("themeName", request.getThemeName());
            in.put("themeId", request.getThemeId());
            in.put("background", request.getBackground());
            in.put("blockDark", request.getBlockDark());
            in.put("blockLight", request.getBlockLight());
            in.put("textDark", request.getTextDark());
            in.put("textLight", request.getTextLight());
            in.put("textHolder", request.getTextHolder());

            iAdminThemeBService.updateWebTheme(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin updateWebTheme error:" + ex.getMessage());
            }
        }
        return response;
    }
}
