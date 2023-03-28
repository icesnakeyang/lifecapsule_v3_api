package cc.cdtime.lifecapsule_v3_api.web.taskTodo;

import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
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
@RequestMapping("/lifecapsule3_api/web/task/todo")
public class WebTaskTodoController {
    private final IWebTaskTodoBService iWebTaskTodoBService;
    private final ICommonService iCommonService;

    public WebTaskTodoController(IWebTaskTodoBService iWebTaskTodoBService,
                                 ICommonService iCommonService) {
        this.iWebTaskTodoBService = iWebTaskTodoBService;
        this.iCommonService = iCommonService;
    }

    /**
     * web端用户查询自己的代办任务列表
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

            Map out = iWebTaskTodoBService.listMyTaskTodo(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web listMyTaskTodo error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * web端用户创建一条代办任务
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
            in.put("projectId", request.getProjectId());

            logMap.put("UserActType", ESTags.USER_CREATE_TASK_TODO);
            logMap.put("token", token);
            memoMap.put("title", request.getTitle());

            iWebTaskTodoBService.createMyTaskTodo(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web createMyTaskTodo error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web createMyTaskTodo user act error: " + ex3.getMessage());
        }
        return response;
    }

    /**
     * web端用户修改待办任务
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

            iWebTaskTodoBService.updateMyTaskTodo(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web updateMyTaskTodo error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web updateMyTaskTodo user act error: " + ex3.getMessage());
        }
        return response;
    }

    /**
     * web端用户读取一个代办任务详情
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
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());

            Map out = iWebTaskTodoBService.getMyTaskTodo(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web getMyTaskTodo error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * web端用户删除一个代办任务
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

            iWebTaskTodoBService.deleteMyTaskTodo(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web deleteMyTaskTodo error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * web端用户更改一个代办任务的完成状态
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/updateMyTaskTodoCompleteStatus")
    public Response updateMyTaskTodoCompleteStatus(@RequestBody TaskRequest request,
                                                   HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("taskId", request.getTaskId());
            in.put("complete", request.getComplete());

            iWebTaskTodoBService.updateMyTaskTodoCompleteStatus(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web updateMyTaskTodoCompleteStatus error:" + ex.getMessage());
            }
        }
        return response;
    }


}
