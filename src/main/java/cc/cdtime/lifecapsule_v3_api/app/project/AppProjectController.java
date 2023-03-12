package cc.cdtime.lifecapsule_v3_api.app.project;

import cc.cdtime.lifecapsule_v3_api.framework.vo.ProjectRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import cc.cdtime.lifecapsule_v3_api.framework.vo.TaskRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/project")
public class AppProjectController {
    private final IAppProjectBService iAppProjectBService;

    public AppProjectController(IAppProjectBService iAppProjectBService) {
        this.iAppProjectBService = iAppProjectBService;
    }

    /**
     * 读取我的项目列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listMyProject")
    public Response listMyProject(@RequestBody TaskRequest request,
                                  HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            Map out = iAppProjectBService.listMyProject(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listMyProject error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 查询一个项目
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getProject")
    public Response getProject(@RequestBody ProjectRequest request,
                               HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("projectName", request.getProjectName());
            in.put("projectId", request.getProjectId());

            Map out = iAppProjectBService.getProject(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getProject error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 创建或者保存一个项目
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/saveMyProject")
    public Response saveMyProject(@RequestBody ProjectRequest request,
                                  HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("projectName", request.getProjectName());
            in.put("projectId", request.getProjectId());

            iAppProjectBService.saveMyProject(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App saveMyProject error:" + ex.getMessage());
            }
        }
        return response;
    }
}
