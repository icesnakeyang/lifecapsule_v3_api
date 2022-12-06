package cc.cdtime.lifecapsule_v3_api.app.antiDelay;

import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.vo.AntiDelayRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/anti_delay")
public class AppAntiDelayController {
    private final IAppAntiDelayBService iAppAntiDelayBService;
    private final ICommonService iCommonService;

    public AppAntiDelayController(IAppAntiDelayBService iAppAntiDelayBService,
                                  ICommonService iCommonService) {
        this.iAppAntiDelayBService = iAppAntiDelayBService;
        this.iCommonService = iCommonService;
    }

    /**
     * app端用户读取自己的防拖延笔记列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listMyAntiDelayNote")
    public Response listMyAntiDelayNote(@RequestBody AntiDelayRequest request,
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
            logMap.put("UserActType", ESTags.USER_LIST_ANTI_DELAY_NOTE);

            Map out = iAppAntiDelayBService.listMyAntiDelayNote(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App user listMyAntiDelayNote error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App user listMyAntiDelayNote user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * app端用户创建自己的防拖延笔记
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/createMyAntiDelayNote")
    public Response createMyAntiDelayNote(@RequestBody AntiDelayRequest request,
                                          HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());
            in.put("tasks", request.getTasks());
            in.put("happyYesterday", request.getHappyYesterday());
            in.put("longGoal", request.getLongGoal());
            in.put("todayGoal", request.getTodayGoal());
            in.put("title", request.getTitle());
            in.put("myThought", request.getMyThought());

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.USER_CREATE_ANTI_DELAY_NOTE);
            memoMap.put("title", request.getTitle());

            iAppAntiDelayBService.createMyAntiDelayNote(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App user createMyAntiDelayNote error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App user createMyAntiDelayNote user act error:" + ex3.getMessage());
        }
        return response;
    }


    /**
     * app端用户修改自己的防拖延笔记
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/updateMyAntiDelayNote")
    public Response updateMyAntiDelayNote(@RequestBody AntiDelayRequest request,
                                          HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());
            in.put("tasks", request.getTasks());
            in.put("happyYesterday", request.getHappyYesterday());
            in.put("longGoal", request.getLongGoal());
            in.put("todayGoal", request.getTodayGoal());
            in.put("title", request.getTitle());
            in.put("myThought", request.getMyThought());
            in.put("noteId", request.getNoteId());

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.USER_UPDATE_ANTI_DELAY_NOTE);
            memoMap.put("noteId", request.getNoteId());

            iAppAntiDelayBService.updateMyAntiDelayNote(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App user updateMyAntiDelayNote error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App user updateMyAntiDelayNote user act error:" + ex3.getMessage());
        }
        return response;
    }


    /**
     * app端用户读取一个防拖延笔记详情
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getMyAntiDelayNote")
    public Response getMyAntiDelayNote(@RequestBody AntiDelayRequest request,
                                       HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());
            in.put("noteId", request.getNoteId());

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.USER_GET_ANTI_DELAY_NOTE);
            memoMap.put("noteId", request.getNoteId());

            Map out = iAppAntiDelayBService.getMyAntiDelayNote(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App user getMyAntiDelayNote error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App user getMyAntiDelayNote user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * app端用户物理删除一个防拖延笔记
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteMyAntiDelayNote")
    public Response deleteMyAntiDelayNote(@RequestBody AntiDelayRequest request,
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
            logMap.put("UserActType", ESTags.USER_DELETE_ANTI_DELAY_NOTE);
            memoMap.put("noteId", request.getNoteId());

            iAppAntiDelayBService.deleteMyAntiDelayNote(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App user deleteMyAntiDelayNote error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App user deleteMyAntiDelayNote user act error:" + ex3.getMessage());
        }
        return response;
    }
}
