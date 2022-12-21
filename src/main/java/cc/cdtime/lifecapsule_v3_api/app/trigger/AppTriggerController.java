package cc.cdtime.lifecapsule_v3_api.app.trigger;

import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import cc.cdtime.lifecapsule_v3_api.framework.vo.TriggerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/trigger")
public class AppTriggerController {
    private final IAppTriggerBService iAppTriggerBService;

    public AppTriggerController(IAppTriggerBService iAppTriggerBService) {
        this.iAppTriggerBService = iAppTriggerBService;
    }


    /**
     * App用户创建一个指定时间的触发器
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/createTriggerDatetime")
    public Response createTriggerDatetime(@RequestBody TriggerRequest request,
                                          HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("toEmail", request.getToEmail());
            in.put("title", request.getTitle());
            in.put("sendTime", request.getSendTime());
            in.put("fromName", request.getFromName());
            in.put("toName", request.getToName());

            iAppTriggerBService.createTriggerDatetime(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App createTriggerDatetime error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App用户创建一个立即发送的触发器，并发送笔记
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

            iAppTriggerBService.createTriggerInstant(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App createTriggerInstant error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户查看笔记的触发器列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listMyTrigger")
    public Response listMyTrigger(@RequestBody TriggerRequest request,
                                  HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());
            in.put("status", request.getStatus());

            Map out = iAppTriggerBService.listMyTrigger(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listMyTrigger error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户查看笔记的触发器详情
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getMyTriggerDetail")
    public Response getMyTriggerDetail(@RequestBody TriggerRequest request,
                                       HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("triggerId", request.getTriggerId());
            in.put("keyToken", request.getKeyToken());
            in.put("encryptKey", request.getEncryptKey());

            Map out = iAppTriggerBService.getMyTriggerDetail(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getMyTriggerDetail error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户删除一个笔记的触发器
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteMyNoteTrigger")
    public Response deleteMyNoteTrigger(@RequestBody TriggerRequest request,
                                        HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("triggerId", request.getTriggerId());

            iAppTriggerBService.deleteMyNoteTrigger(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App deleteMyNoteTrigger error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App用户指定一篇笔记随主倒计时结束发送
     * 遗言
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/createTriggerPrimary")
    public Response createTriggerPrimary(@RequestBody TriggerRequest request,
                                         HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("toEmail", request.getToEmail());
            in.put("title", request.getTitle());
            in.put("fromName", request.getFromName());
            in.put("toName", request.getToName());

            iAppTriggerBService.createTriggerPrimary(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App createTriggerPrimary error:" + ex.getMessage());
            }
        }
        return response;
    }

}
