package cc.cdtime.lifecapsule_v3_api.web.motto;

import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.vo.MottoRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/web/motto")
public class WebMottoController {
    private final IWebMottoBService iWebMottoBService;
    private final ICommonService iCommonService;

    public WebMottoController(IWebMottoBService iWebMottoBService,
                              ICommonService iCommonService) {
        this.iWebMottoBService = iWebMottoBService;
        this.iCommonService = iCommonService;
    }

    /**
     * Web用户发布一条motto
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/publishMotto")
    public Response publishMotto(@RequestBody MottoRequest request,
                                 HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("content", request.getContent());
            in.put("authorName", request.getAuthorName());

            logMap.put("UserActType", ESTags.USER_PUBLISH_MOTTO);
            logMap.put("token", token);
            memoMap.put("content", request.getContent());

            iWebMottoBService.publishMotto(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web publishMotto error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web publishMotto user act error:" + ex3.getMessage());
        }
        return response;
    }
}
