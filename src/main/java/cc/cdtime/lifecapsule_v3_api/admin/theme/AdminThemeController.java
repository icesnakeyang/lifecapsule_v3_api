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
            in.put("colorDanger", request.getColorDanger());
            in.put("colorDanger2", request.getColorDanger2());
            in.put("color1", request.getColor1());
            in.put("color2", request.getColor2());
            in.put("color3", request.getColor3());
            in.put("color4", request.getColor4());

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
     * 管理员新增一个App主题
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/createAppTheme")
    public Response createAppTheme(@RequestBody ThemeRequest request,
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
            in.put("colorDanger", request.getColorDanger());
            in.put("colorDanger2", request.getColorDanger2());
            in.put("colorDark", request.getColorDark());
            in.put("colorDark2", request.getColorDark2());
            in.put("colorMedium", request.getColorMedium());
            in.put("colorMedium2", request.getColorMedium2());
            in.put("colorLight", request.getColorLight());
            in.put("colorLight2", request.getColorLight2());
            in.put("color1", request.getColor1());
            in.put("color2", request.getColor2());
            in.put("color3", request.getColor3());
            in.put("color4", request.getColor4());
            in.put("status", request.getStatus());

            iAdminThemeBService.createAppTheme(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin createAppTheme error:" + ex.getMessage());
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
            in.put("colorDanger", request.getColorDanger());
            in.put("colorDanger2", request.getColorDanger2());
            in.put("colorDark2", request.getColorDark2());
            in.put("colorMedium2", request.getColorMedium2());
            in.put("colorLight2", request.getColorLight2());

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

    /**
     * 管理员修改一个App主题
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/updateAppTheme")
    public Response updateAppTheme(@RequestBody ThemeRequest request,
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
            in.put("colorDanger", request.getColorDanger());
            in.put("colorDanger2", request.getColorDanger2());
            in.put("colorDark", request.getColorDark());
            in.put("colorDark2", request.getColorDark2());
            in.put("colorMedium", request.getColorMedium());
            in.put("colorMedium2", request.getColorMedium2());
            in.put("colorLight", request.getColorLight());
            in.put("colorLight2", request.getColorLight2());
            in.put("color1", request.getColor1());
            in.put("color2", request.getColor2());
            in.put("color3", request.getColor3());
            in.put("color4", request.getColor4());
            in.put("status", request.getStatus());

            iAdminThemeBService.updateAppTheme(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin updateAppTheme error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 管理员查询App端的主题列表
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listAppTheme")
    public Response listAppTheme(@RequestBody ThemeRequest request,
                                 HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            Map out = iAdminThemeBService.listAppTheme(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin listAppTheme error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 管理员查询App端的主题详情
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getAppTheme")
    public Response getAppTheme(@RequestBody ThemeRequest request,
                                HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("themeId", request.getThemeId());

            Map out = iAdminThemeBService.getAppTheme(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin getAppTheme error:" + ex.getMessage());
            }
        }
        return response;
    }
}
