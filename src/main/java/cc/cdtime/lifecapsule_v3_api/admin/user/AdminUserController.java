package cc.cdtime.lifecapsule_v3_api.admin.user;

import cc.cdtime.lifecapsule_v3_api.business.adminUser.IAdminUserBService;
import cc.cdtime.lifecapsule_v3_api.framework.vo.AdminUserRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/admin/user")
public class AdminUserController {
    private final IAdminUserBService iAdminUserBService;

    public AdminUserController(IAdminUserBService iAdminUserBService) {
        this.iAdminUserBService = iAdminUserBService;
    }

    @ResponseBody
    @PostMapping("/createAdminRoot27890")
    public Response createAdmin(@RequestBody AdminUserRequest request) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            in.put("loginName", request.getLoginName());
            in.put("password", request.getPassword());
            in.put("roleType", request.getRoleType());

            iAdminUserBService.createAdmin(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin createAdminRoot error:" + ex.getMessage());
            }
        }
        return response;
    }

    @ResponseBody
    @PostMapping("/adminLogin")
    public Response adminLogin(@RequestBody AdminUserRequest request) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            in.put("loginName", request.getLoginName());
            in.put("password", request.getPassword());

            Map out=iAdminUserBService.adminLogin(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin adminLogin error:" + ex.getMessage());
            }
        }
        return response;
    }
}
