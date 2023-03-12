package cc.cdtime.lifecapsule_v3_api.app.taskTodo.controller;

import cc.cdtime.lifecapsule_v3_api.app.taskTodo.business.IAppTaskTodoBService;
import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import cc.cdtime.lifecapsule_v3_api.framework.vo.TaskRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/taskTodo")
public class AppTaskTodoController {
    private final IAppTaskTodoBService iAppTaskTodoBService;
    private final ICommonService iCommonService;

    public AppTaskTodoController(IAppTaskTodoBService iAppTaskTodoBService,
                                 ICommonService iCommonService) {
        this.iAppTaskTodoBService = iAppTaskTodoBService;
        this.iCommonService = iCommonService;
    }

    /**
     * 手机端用户查询自己的代办任务列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listMyTaskTodo")
    public Response listMyTaskTodo(@RequestBody TaskRequest request,
                                   HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());
            in.put("hideComplete", request.getHideComplete());
            in.put("projectId", request.getProjectId());

            Map out = iAppTaskTodoBService.listMyTaskTodo(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listMyTaskTodo error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 手机端用户创建一条代办任务
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/createMyTaskTodo")
    public Response createMyTaskTodo(@RequestBody TaskRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("title", request.getTitle());
            in.put("content", request.getContent());
            in.put("keyToken", request.getKeyToken());
            in.put("encryptKey", request.getEncryptKey());

            logMap.put("UserActType", ESTags.USER_CREATE_TASK_TODO);
            logMap.put("token", token);
            memoMap.put("title", request.getTitle());

            iAppTaskTodoBService.createMyTaskTodo(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App createMyTaskTodo error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App createMyTaskTodo user act error: " + ex3.getMessage());
        }
        return response;
    }

    /**
     * 手机端用户修改待办任务
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/updateMyTaskTodo")
    public Response updateMyTaskTodo(@RequestBody TaskRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("taskId", request.getTaskId());
            in.put("title", request.getTitle());
            in.put("content", request.getContent());
            in.put("keyToken", request.getKeyToken());
            in.put("encryptKey", request.getEncryptKey());
            in.put("complete", request.getComplete());
            in.put("projectId", request.getProjectId());

            logMap.put("UserActType", ESTags.USER_UPDATE_TASK_TODO);
            logMap.put("token", token);
            memoMap.put("title", request.getTitle());

            iAppTaskTodoBService.updateMyTaskTodo(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App updateMyTaskTodo error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App updateMyTaskTodo user act error: " + ex3.getMessage());
        }
        return response;
    }

    /**
     * 手机端用户读取一个代办任务详情
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getMyTaskTodo")
    public Response getMyTaskTodo(@RequestBody TaskRequest request,
                                  HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("taskId", request.getTaskId());
            in.put("keyToken", request.getKeyToken());
            in.put("encryptKey", request.getEncryptKey());

            Map out = iAppTaskTodoBService.getMyTaskTodo(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getMyTaskTodo error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 手机端用户删除一个代办任务
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteMyTaskTodo")
    public Response deleteMyTaskTodo(@RequestBody TaskRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("taskId", request.getTaskId());

            iAppTaskTodoBService.deleteMyTaskTodo(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App deleteMyTaskTodo error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 设置完成或者未完成
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/updateTaskTodoComplete")
    public Response updateTaskTodoComplete(@RequestBody TaskRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("taskId", request.getTaskId());
            in.put("complete", request.getComplete());

            iAppTaskTodoBService.updateTaskTodoComplete(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App updateTaskTodoComplete error:" + ex.getMessage());
            }
        }
        return response;
    }
}
