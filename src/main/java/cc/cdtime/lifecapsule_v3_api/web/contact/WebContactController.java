package cc.cdtime.lifecapsule_v3_api.web.contact;

import cc.cdtime.lifecapsule_v3_api.framework.vo.ContactRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/web/contact")
public class WebContactController {
    private final IWebContactBService iWebContactBService;

    public WebContactController(IWebContactBService iWebContactBService) {
        this.iWebContactBService = iWebContactBService;
    }

    /**
     * 修改或者创建一个联系人
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/save_my_contact")
    public Response saveMyContact(@RequestBody ContactRequest request,
                                  HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("contactName", request.getContactName());
            in.put("phone", request.getPhone());
            in.put("email", request.getEmail());
            in.put("remark", request.getRemark());
            in.put("contactId", request.getContactId());

            iWebContactBService.saveMyContact(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web user saveMyContact error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 读取我的联系人列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/list_my_contact")
    public Response listMyContact(@RequestBody ContactRequest request,
                                  HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());

            Map out = iWebContactBService.listMyContact(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web user listMyContact error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 读取一个联系人详情
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/get_my_contact")
    public Response getMyContact(@RequestBody ContactRequest request,
                                 HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("contactId", request.getContactId());

            Map out = iWebContactBService.getMyContact(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web user getMyContact error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 删除一个联系人
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/delete_my_contact")
    public Response deleteMyContact(@RequestBody ContactRequest request,
                                    HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("contactId", request.getContactId());

            iWebContactBService.deleteMyContact(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web user deleteMyContact error:" + ex.getMessage());
            }
        }
        return response;
    }
}
