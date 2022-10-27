package cc.cdtime.lifecapsule_v3_api.app.motto;

import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.vo.MottoRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/motto")
public class AppMottoController {
    private final IAppMottoBService iAppMottoBService;
    private final ICommonService iCommonService;

    public AppMottoController(IAppMottoBService iAppMottoBService,
                              ICommonService iCommonService) {
        this.iAppMottoBService = iAppMottoBService;
        this.iCommonService = iCommonService;
    }

    /**
     * 用户发布一条motto
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
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("content", request.getContent());
            in.put("authorName", request.getAuthorName());

            iAppMottoBService.publishMotto(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App publishMotto error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 随机读取一条motto
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @GetMapping("/getMottoRandom")
    public Response getMottoRandom(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            Map out = iAppMottoBService.getMottoRandom(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getMottoRandom error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户点赞一条motto
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/likeMotto")
    public Response likeMotto(
            @RequestBody MottoRequest request,
            HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("mottoId", request.getMottoId());

            logMap.put("UserActType", ESTags.USER_LIKE_MOTTO);
            logMap.put("token", token);
            memoMap.put("mottoId", request.getMottoId());

            iAppMottoBService.likeMotto(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App likeMotto error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App likeMotto user act error:" + ex3.getMessage());
        }
        return response;
    }
}
