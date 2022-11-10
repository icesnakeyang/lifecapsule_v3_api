package cc.cdtime.lifecapsule_v3_api.app.inbox;

import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.vo.InboxRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.ReplyNoteRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/inbox")
public class AppInboxController {
    private final IAppInboxBService iAppInboxBService;
    private final ICommonService iCommonService;

    public AppInboxController(IAppInboxBService iAppInboxBService,
                              ICommonService iCommonService) {
        this.iAppInboxBService = iAppInboxBService;
        this.iCommonService = iCommonService;
    }

    /**
     * App端用户读取自己收到的笔记列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listMyReceiveNote")
    public Response listMyReceiveNote(@RequestBody InboxRequest request,
                                      HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap=new HashMap();
        Map memoMap=new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());

            logMap.put("UserActType", ESTags.USER_LIST_RECEIVE_NOTE);
            logMap.put("token", token);

            Map out = iAppInboxBService.listMyReceiveNote(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listMyReceiveNote error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App listMyReceiveNote user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * App端用户读取自己收到的笔记详情
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getMyReceiveNote")
    public Response getMyReceiveNote(@RequestBody InboxRequest request,
                                      HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());
            in.put("sendLogId", request.getSendLogId());

            Map out = iAppInboxBService.getMyReceiveNote(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getMyReceiveNote error:" + ex.getMessage());
            }
        }
        return response;
    }
}
