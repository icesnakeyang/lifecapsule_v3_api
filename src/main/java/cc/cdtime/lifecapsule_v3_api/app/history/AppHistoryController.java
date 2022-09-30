package cc.cdtime.lifecapsule_v3_api.app.history;

import cc.cdtime.lifecapsule_v3_api.framework.vo.HistoryRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/history")
public class AppHistoryController {
    private final IAppHistoryBService iAppHistoryBService;

    public AppHistoryController(IAppHistoryBService iAppHistoryBService) {
        this.iAppHistoryBService = iAppHistoryBService;
    }

    /**
     * App端查询我的历史首页需要显示的数据
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
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());

            Map out = iAppHistoryBService.loadHistoryHome(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App loadHistoryHome error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App端用户回复自己的笔记
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
}
