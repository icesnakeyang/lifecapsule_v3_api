package cc.cdtime.lifecapsule_v3_api.app.settings.language;

import cc.cdtime.lifecapsule_v3_api.business.userProfile.IUserProfileBService;
import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/settings/language")
public class AppLanguageController {
    private final IUserProfileBService iUserProfileBService;

    public AppLanguageController(IUserProfileBService iUserProfileBService) {
        this.iUserProfileBService = iUserProfileBService;
    }

    /**
     * 设置用户使用的语言
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/saveUserLanguage")
    public Response saveUserLanguage(@RequestBody LanguageRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("language", request.getLanguage());

            iUserProfileBService.saveUserLanguage(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App saveUserLanguage error:" + ex.getMessage());
            }
        }
        return response;
    }


}
