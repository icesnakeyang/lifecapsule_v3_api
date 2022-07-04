package cc.cdtime.lifecapsule_v3_api.app.trigger.controller;

import cc.cdtime.lifecapsule_v3_api.app.trigger.business.IAppTriggerBService;
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
@RequestMapping("/lifecapsule3_api/app/trigger")
public class AppTriggerController {
    private final IAppTriggerBService iAppTriggerBService;

    public AppTriggerController(IAppTriggerBService iAppTriggerBService) {
        this.iAppTriggerBService = iAppTriggerBService;
    }

    /**
     * 用户查看笔记的触发器
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listNoteTrigger")
    public Response listNoteTrigger(@RequestBody TriggerRequest request,
                                    HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            Map out = iAppTriggerBService.listNoteTrigger(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User listNoteTrigger error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户查看笔记的一条触发器详情
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getNoteTrigger")
    public Response getNoteTrigger(@RequestBody TriggerRequest request,
                                    HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("triggerId", request.getTriggerId());

            Map out = iAppTriggerBService.getNoteTrigger(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User getNoteTrigger error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户保存笔记的触发器
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/saveNoteTrigger")
    public Response saveNoteTrigger(@RequestBody TriggerRequest request,
                                    HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("remark", request.getRemark());
            in.put("triggerType", request.getTriggerType());
            in.put("triggerTime", request.getTriggerTime());
            in.put("triggerId", request.getTriggerId());
            in.put("fromName", request.getFromName());
            in.put("recipientId", request.getRecipientId());

            Map out = iAppTriggerBService.saveNoteTrigger(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User createNoteTrigger error:" + ex.getMessage());
            }
        }
        return response;
    }
}
