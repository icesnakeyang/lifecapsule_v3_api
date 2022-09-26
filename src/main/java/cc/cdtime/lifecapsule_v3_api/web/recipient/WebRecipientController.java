package cc.cdtime.lifecapsule_v3_api.web.recipient;

import cc.cdtime.lifecapsule_v3_api.framework.vo.RecipientRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/web/recipient")
public class WebRecipientController {

    private final IWebRecipientBService iWebRecipientBService;

    public WebRecipientController(IWebRecipientBService iWebRecipientBService) {
        this.iWebRecipientBService = iWebRecipientBService;
    }

    /**
     * 把一个联系人添加到接收人里
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/add_contact_to_recipient")
    public Response addContactToRecipient(@RequestBody RecipientRequest request,
                                          HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("contactId", request.getContactId());
            in.put("noteId", request.getNoteId());

            iWebRecipientBService.addContactToRecipient(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web addContactToRecipient error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 添加一个email为接收人
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/addEmailToRecipient")
    public Response addEmailToRecipient(@RequestBody RecipientRequest request,
                                          HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("email", request.getEmail());
            in.put("noteId", request.getNoteId());

            iWebRecipientBService.addEmailToRecipient(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web addEmailToRecipient error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 读取我的一篇笔记的接收人列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/list_my_note_recipient")
    public Response listMyNoteRecipient(@RequestBody RecipientRequest request,
                                        HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            Map out = iWebRecipientBService.listMyNoteRecipient(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web listMyNoteRecipient error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * Web读取一个接收人信息
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/get_recipient")
    public Response getRecipient(@RequestBody RecipientRequest request,
                                 HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("recipientId", request.getRecipientId());

            Map out = iWebRecipientBService.getRecipient(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web getRecipient error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * web端用户修改一个接收人信息
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
            in.put("recipientId", request.getRecipientId());
            in.put("name", request.getName());
            in.put("phone", request.getPhone());
            in.put("email", request.getEmail());
            in.put("title", request.getTitle());
            in.put("description", request.getDescription());
            in.put("toName", request.getToName());
            in.put("fromName", request.getFromName());

            iWebRecipientBService.saveRecipient(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web saveRecipient error:" + ex.getMessage());
            }
        }
        return response;
    }

}
