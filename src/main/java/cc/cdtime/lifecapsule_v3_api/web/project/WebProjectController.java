package cc.cdtime.lifecapsule_v3_api.web.project;

import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.vo.ProjectRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/web/project")
public class WebProjectController {
    private final IWebProjectBService iWebProjectBService;
    private final ICommonService iCommonService;

    public WebProjectController(IWebProjectBService iWebProjectBService, ICommonService iCommonService) {
        this.iWebProjectBService = iWebProjectBService;
        this.iCommonService = iCommonService;
    }

    /**
     * web端用户保存我的项目
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
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("projectName", request.getProjectName());
            in.put("projectId", request.getProjectId());

            logMap.put("UserActType", ESTags.PROJECT_SAVE);
            logMap.put("token", token);
            memoMap.put("projectName", request.getProjectName());

            iWebProjectBService.saveMyProject(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("web saveMyProject error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("web saveMyProject user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * web端用户查看我的项目列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listMyProject")
    public Response listMyProject(@RequestBody ProjectRequest request,
                                  HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("projectName", request.getProjectName());
            in.put("projectId", request.getProjectId());

            logMap.put("UserActType", ESTags.PROJECT_LIST);
            logMap.put("token", token);

            Map out=iWebProjectBService.listMyProject(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("web listMyProject error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("web listMyProject user act error:" + ex3.getMessage());
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

            Map out = iWebProjectBService.getProject(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("web getProject error:" + ex.getMessage());
            }
        }
        return response;
    }
}
