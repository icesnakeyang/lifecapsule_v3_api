package cc.cdtime.lifecapsule_v3_api.admin.cash;

import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/admin/cash")
public class AdminCashController {
    private final IAdminCashBService iAdminCashBService;

    public AdminCashController(IAdminCashBService iAdminCashBService) {
        this.iAdminCashBService = iAdminCashBService;
    }

    @ResponseBody
    @PostMapping("/listUserCash")
    public Response listUserCash(@RequestBody AdminCashRequest request,
                                 HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());
            in.put("userId", request.getUserId());

            Map out = iAdminCashBService.listUserCash(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin listUserCash error:" + ex.getMessage());
            }
        }
        return response;
    }
}
