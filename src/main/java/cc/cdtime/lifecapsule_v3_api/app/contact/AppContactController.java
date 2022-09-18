package cc.cdtime.lifecapsule_v3_api.app.contact;

import cc.cdtime.lifecapsule_v3_api.framework.vo.ContactRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/contact")
public class AppContactController {
    private final IAppContactBService iAppContactBService;

    public AppContactController(IAppContactBService iAppContactBService) {
        this.iAppContactBService = iAppContactBService;
    }

    /**
     * 读取我的联系人列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listMyContact")
    public Response listMyContact(@RequestBody ContactRequest request,
                                  HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());

            Map out = iAppContactBService.listMyContact(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App user listMyContact error:" + ex.getMessage());
            }
        }
        return response;
    }
}
