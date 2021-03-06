package cc.cdtime.lifecapsule_v3_api.admin.user;

import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import cc.cdtime.lifecapsule_v3_api.framework.vo.UserAccountRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/admin/users")
public class AdminUserController {
    private final IAdminUserBService iAdminUserBService;

    public AdminUserController(IAdminUserBService iAdminUserBService) {
        this.iAdminUserBService = iAdminUserBService;
    }

    /**
     * 读取所有用户列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listUsers")
    public Response listUsers(@RequestBody UserAccountRequest request,
                              HttpServletRequest httpServletRequest
    ) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());

            Map out = iAdminUserBService.listUsers(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin listUsers error:" + ex.getMessage());
            }
        }
        return response;
    }
}
