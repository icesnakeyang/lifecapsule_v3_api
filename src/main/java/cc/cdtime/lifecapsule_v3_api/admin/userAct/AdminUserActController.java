package cc.cdtime.lifecapsule_v3_api.admin.userAct;

import cc.cdtime.lifecapsule_v3_api.business.adminUserAct.IAdminUserActBService;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import cc.cdtime.lifecapsule_v3_api.framework.vo.UserAccountRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/admin/userAct")
public class AdminUserActController {
    private final IAdminUserActBService iAdminUserActBService;

    public AdminUserActController(IAdminUserActBService iAdminUserActBService) {
        this.iAdminUserActBService = iAdminUserActBService;
    }

    /**
     * 管理员查看用户行为日志
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listUserAct")
    public Response listUserAct(@RequestBody UserAccountRequest request,
                                HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());
            in.put("nickname", request.getNickname());
            if (request.getStartTime() != null) {
                Calendar c1 = Calendar.getInstance();
                c1.setTime(request.getStartTime());
                c1.set(Calendar.HOUR_OF_DAY, 0);
                c1.set(Calendar.MINUTE, 0);
                c1.set(Calendar.SECOND, 0);
                in.put("startTime", c1.getTime());
            }
            if (request.getEndTime() != null) {
                Calendar c1 = Calendar.getInstance();
                c1.setTime(request.getEndTime());
                int theDay = c1.get(Calendar.DATE);
                theDay += 1;
                c1.set(Calendar.DATE, theDay);
                c1.set(Calendar.HOUR_OF_DAY, 0);
                c1.set(Calendar.MINUTE, 0);
                c1.set(Calendar.SECOND, 0);
                in.put("endTime", c1.getTime());
            }

            Map out = iAdminUserActBService.listUserAct(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin listUserAct error:" + ex.getMessage());
            }
        }
        return response;
    }
}
