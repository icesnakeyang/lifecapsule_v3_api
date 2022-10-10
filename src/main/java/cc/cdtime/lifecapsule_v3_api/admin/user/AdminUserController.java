package cc.cdtime.lifecapsule_v3_api.admin.user;

import cc.cdtime.lifecapsule_v3_api.business.adminStatistic.IAdminStatisticBService;
import cc.cdtime.lifecapsule_v3_api.framework.vo.AdminUserRequest;
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
    private final IAdminStatisticBService iAdminStatisticBService;

    public AdminUserController(IAdminUserBService iAdminUserBService,
                               IAdminStatisticBService iAdminStatisticBService) {
        this.iAdminUserBService = iAdminUserBService;
        this.iAdminStatisticBService = iAdminStatisticBService;
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

    /**
     * 读取所有用户登录日志列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listUserLoginLog")
    public Response listUserLoginLog(@RequestBody UserAccountRequest request,
                                     HttpServletRequest httpServletRequest
    ) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());
            in.put("searchKey", request.getSearchKey());

            Map out = iAdminUserBService.listUserLoginLog(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin listUserLoginLog error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 读取用户统计数据
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @GetMapping("/loadUserStatistic")
    public Response loadUserStatistic(HttpServletRequest httpServletRequest
    ) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            Map out = iAdminUserBService.loadUserStatistic(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin loadUserStatistic error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 统计用户登录数据
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/loadUserLoginStatistic")
    public Response loadUserLoginStatistic(@RequestBody AdminUserRequest request,
                                           HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("startTime", request.getStartTime());
            in.put("endTime", request.getEndTime());
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());

            Map out = iAdminStatisticBService.totalUserLogin(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin loadUserLoginStatistic error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 查询最新的10篇笔记
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @GetMapping("/listTop10Notes")
    public Response listTop10Notes(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            Map out = iAdminStatisticBService.listTopNote(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin listTop10Notes error:" + ex.getMessage());
            }
        }
        return response;
    }
}
