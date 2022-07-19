package cc.cdtime.lifecapsule_v3_api.web.trigger;

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

    public WebTriggerController(IWebTriggerBService iWebTriggerBService) {
        this.iWebTriggerBService = iWebTriggerBService;
    }

    /**
     * 保存一个接收人的触发条件
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/save_note_recipient_trigger")
    public Response saveNoteRecipientTrigger(@RequestBody TriggerRequest request,
                                             HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("triggerTime", request.getTriggerTime());
            in.put("triggerType", request.getTriggerType());
            in.put("recipientId", request.getRecipientId());
            in.put("triggerId", request.getTriggerId());
            Map out = iWebTriggerBService.saveNoteRecipientTrigger(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web saveNoteRecipientTrigger error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 根据recipientId读取一个触发条件
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/get_note_recipient_trigger")
    public Response getNoteRecipientTrigger(@RequestBody TriggerRequest request,
                                             HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("recipientId", request.getRecipientId());
            Map out = iWebTriggerBService.getNoteRecipientTrigger(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web getNoteRecipientTrigger error:" + ex.getMessage());
            }
        }
        return response;
    }
}
