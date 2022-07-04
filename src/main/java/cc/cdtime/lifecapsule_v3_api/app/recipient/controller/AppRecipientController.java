package cc.cdtime.lifecapsule_v3_api.app.recipient.controller;

import cc.cdtime.lifecapsule_v3_api.app.recipient.business.IAppRecipientBService;
import cc.cdtime.lifecapsule_v3_api.framework.vo.RecipientRequest;
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
@RequestMapping("/lifecapsule3_api/app/recipient")
public class AppRecipientController {
    private final IAppRecipientBService iAppRecipientBService;

    public AppRecipientController(IAppRecipientBService iAppRecipientBService) {
        this.iAppRecipientBService = iAppRecipientBService;
    }

    /**
     * 用户查看接收人列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listRecipient")
    public Response listRecipient(@RequestBody TriggerRequest request,
                                  HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            Map out = iAppRecipientBService.listRecipient(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User listRecipient error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App创建或者保存一个接收人
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/saveRecipient")
    public Response saveRecipient(@RequestBody RecipientRequest request,
                                  HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("triggerId", request.getTriggerId());
            in.put("name", request.getName());
            in.put("phone", request.getPhone());
            in.put("email", request.getEmail());
            in.put("recipientId", request.getRecipientId());
            in.put("remark", request.getRemark());
            in.put("noteId", request.getNoteId());
            in.put("title", request.getTitle());
            in.put("description", request.getDescription());
            in.put("fromName", request.getFromName());
            in.put("toName", request.getToName());

            iAppRecipientBService.saveRecipient(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User saveRecipient error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App读取一个接收人信息
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getRecipient")
    public Response getRecipient(@RequestBody RecipientRequest request,
                                 HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("recipientId", request.getRecipientId());

            Map out = iAppRecipientBService.getRecipient(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getRecipient error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App物理删除一个接收人信息
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteRecipient")
    public Response deleteRecipient(@RequestBody RecipientRequest request,
                                    HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("recipientId", request.getRecipientId());

            iAppRecipientBService.deleteRecipient(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App deleteRecipient error:" + ex.getMessage());
            }
        }
        return response;
    }
}
