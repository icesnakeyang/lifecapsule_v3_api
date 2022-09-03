package cc.cdtime.lifecapsule_v3_api.app.taskTodo.controller;

import cc.cdtime.lifecapsule_v3_api.app.taskTodo.business.IAppTaskTodoBService;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import cc.cdtime.lifecapsule_v3_api.framework.vo.TaskRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/taskTodo")
public class AppTaskTodoController {
    private final IAppTaskTodoBService iAppTaskTodoBService;

    public AppTaskTodoController(IAppTaskTodoBService iAppTaskTodoBService) {
        this.iAppTaskTodoBService = iAppTaskTodoBService;
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
     * 手机端用户创建或者保存自己的代办任务列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/saveMyTaskTodo")
    public Response saveMyTaskTodo(@RequestBody TaskRequest request,
                                   HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("taskId", request.getTaskId());
            in.put("complete", request.getComplete());
            in.put("priority", request.getPriority());
            in.put("title", request.getTitle());
            in.put("content", request.getContent());

            iAppTaskTodoBService.saveMyTaskTodo(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App saveMyTaskTodo error:" + ex.getMessage());
            }
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

            Map out=iAppTaskTodoBService.getMyTaskTodo(in);
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
}
