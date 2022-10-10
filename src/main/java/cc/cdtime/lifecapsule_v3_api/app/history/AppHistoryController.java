package cc.cdtime.lifecapsule_v3_api.app.history;

import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.vo.HistoryRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/history")
public class AppHistoryController {
    private final IAppHistoryBService iAppHistoryBService;
    private final ICommonService iCommonService;

    public AppHistoryController(IAppHistoryBService iAppHistoryBService,
                                ICommonService iCommonService) {
        this.iAppHistoryBService = iAppHistoryBService;
        this.iCommonService = iCommonService;
    }

    /**
     * App端查询我的历史首页需要显示的数据
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/loadHistoryHome")
    public Response loadHistoryHome(@RequestBody HistoryRequest request,
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

            logMap.put("UserActType", ESTags.USER_LIST_HISTORY);
            logMap.put("token", token);

            Map out = iAppHistoryBService.loadHistoryHome(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App loadHistoryHome error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App loadHistoryHome user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * App端用户回复自己的笔记
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/replyMyNote")
    public Response replyMyNote(@RequestBody HistoryRequest request,
                                HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pid", request.getPid());
            in.put("content", request.getContent());
            in.put("title", request.getTitle());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());

            iAppHistoryBService.replyMyNote(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App replyMyNote error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App端用户查询自己的历史笔记
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/searchHistoryNote")
    public Response searchHistoryNote(@RequestBody HistoryRequest request,
                                      HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("searchKey", request.getSearchKey());

            logMap.put("UserActType", ESTags.USER_LIST_HISTORY);
            logMap.put("token", token);
            memoMap.put("searchKey", request.getSearchKey());

            Map out = iAppHistoryBService.searchHistoryNote(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App searchHistoryNote error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App searchHistoryNote user act error:" + ex3.getMessage());
        }
        return response;
    }
}
