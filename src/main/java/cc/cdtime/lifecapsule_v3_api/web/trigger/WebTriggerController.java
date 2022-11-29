package cc.cdtime.lifecapsule_v3_api.web.trigger;

import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import cc.cdtime.lifecapsule_v3_api.framework.vo.TriggerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/web/trigger")
public class WebTriggerController {
    private final IWebTriggerBService iWebTriggerBService;
    private final ICommonService iCommonService;

    public WebTriggerController(IWebTriggerBService iWebTriggerBService,
                                ICommonService iCommonService) {
        this.iWebTriggerBService = iWebTriggerBService;
        this.iCommonService = iCommonService;
    }

    /**
     * Web用户创建一个立即发送的触发器，并发送笔记
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/createTriggerInstant")
    public Response createTriggerInstant(@RequestBody TriggerRequest request,
                                         HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("toEmail", request.getToEmail());
            in.put("title", request.getTitle());
            in.put("noteContent", request.getNoteContent());
            in.put("fromName", request.getFromName());
            in.put("toName", request.getToName());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());

            logMap.put("UserActType", ESTags.USER_CREATE_TRIGGER_INSTANT);
            logMap.put("token", token);
            memoMap.put("noteId", request.getNoteId());

            iWebTriggerBService.createTriggerInstant(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web createTriggerInstant error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web createTriggerInstant use act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * Web用户查询自己笔记的发送队列记录
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listMyTriggerQue")
    public Response listMyTriggerQue(@RequestBody TriggerRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());

            logMap.put("UserActType", ESTags.USER_LIST_TRIGGER);
            logMap.put("token", token);

            Map out = iWebTriggerBService.listMyTriggerQue(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web listMyTriggerQue error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web listMyTriggerQue use act error:" + ex3.getMessage());
        }
        return response;
    }
}
