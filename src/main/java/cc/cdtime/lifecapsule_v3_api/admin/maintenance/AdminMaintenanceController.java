package cc.cdtime.lifecapsule_v3_api.admin.maintenance;

import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/admin/maintenance")
public class AdminMaintenanceController {
    private final IAdminMaintenanceBService iAdminMaintenanceBService;

    public AdminMaintenanceController(IAdminMaintenanceBService iAdminMaintenanceBService) {
        this.iAdminMaintenanceBService = iAdminMaintenanceBService;
    }

    /**
     * 把旧的数据库迁移到新库
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @GetMapping("/restoreOldDatabase")
    public Response restoreOldDatabase(HttpServletRequest httpServletRequest){
        Response response=new Response();
        Map in=new HashMap();
        try {
            String token= httpServletRequest.getHeader("token");
            in.put("token", token);
            iAdminMaintenanceBService.restoreOldDatabase(in);
        }catch (Exception ex){
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            }catch (Exception ex2){
                response.setCode(10001);
                log.error("Admin restoreOldDatabase error:"+ex.getMessage());
            }
        }
        return response;
    }
}
