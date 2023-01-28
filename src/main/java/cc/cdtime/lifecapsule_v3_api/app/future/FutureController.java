package cc.cdtime.lifecapsule_v3_api.app.future;

import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/future")
public class FutureController {
    private final ICommonService iCommonService;
    private final IAppFutureBService iAppFutureBService;

    public FutureController(ICommonService iCommonService,
                            IAppFutureBService iAppFutureBService) {
        this.iCommonService = iCommonService;
        this.iAppFutureBService = iAppFutureBService;
    }

    /**
     * App用户查询写给未来的信列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listFuture")
    public Response listFuture(@RequestBody FutureRequest request,
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

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.LIST_FUTURE);

            Map out = iAppFutureBService.listFuture(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listFuture error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App listFuture user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * App用户查询写给未来的信详情
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getFuture")
    public Response getFuture(@RequestBody FutureRequest request,
                              HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.GET_FUTURE);

            Map out = iAppFutureBService.getFuture(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getFuture error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App getFuture user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * App用户保存写给未来的信
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/saveFuture")
    public Response saveFuture(@RequestBody FutureRequest request,
                               HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("content", request.getContent());
            in.put("title", request.getTitle());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());
            in.put("noteId", request.getNoteId());
            in.put("toEmail", request.getToEmail());
            in.put("toName", request.getToName());
            in.put("fromName", request.getFromName());
            in.put("sendTime", request.getSendTime());

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.SAVE_FUTURE);

            iAppFutureBService.saveFuture(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App saveFuture error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App saveFuture user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * App用户删除写给未来的信
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteFuture")
    public Response deleteFuture(@RequestBody FutureRequest request,
                                 HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.DELETE_FUTURE);
            memoMap.put("noteId", request.getNoteId());

            iAppFutureBService.deleteFuture(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App deleteFuture error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App deleteFuture user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * App用户读取写给未来的信的触发器列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listFutureTrigger")
    public Response listFutureTrigger(@RequestBody FutureRequest request,
                                      HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            Map out = iAppFutureBService.listFutureTrigger(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listFutureTrigger error:" + ex.getMessage());
            }
        }
        return response;
    }
}
