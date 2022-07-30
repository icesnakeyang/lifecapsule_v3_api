package cc.cdtime.lifecapsule_v3_api.web.quadTask;

import cc.cdtime.lifecapsule_v3_api.framework.vo.QuadTaskRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/web/quadTask")
public class WebQuadTaskController {
    private final IWebQuadTaskBService iWebQuadTaskBService;

    public WebQuadTaskController(IWebQuadTaskBService iWebQuadTaskBService) {
        this.iWebQuadTaskBService = iWebQuadTaskBService;
    }

    /**
     * 网页用户查看我的四象限任务列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listMyQuadTask")
    public Response listMyQuadTask(@RequestBody QuadTaskRequest request,
                                   HttpServletRequest httpServletRequest
    ) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());

            Map out = iWebQuadTaskBService.listMyQuadTask(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web listMyQuadTask error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 网页用户创建一条四象限任务
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/createMyQuadTask")
    public Response createMyQuadTask(@RequestBody QuadTaskRequest request,
                                     HttpServletRequest httpServletRequest
    ) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("taskTitle", request.getTaskTitle());
            in.put("important", request.getImportant());
            in.put("endTime", request.getEndTime());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());
            in.put("content", request.getContent());
            in.put("taskType", request.getTaskType());

            iWebQuadTaskBService.createMyQuadTask(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web createMyQuadTask error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 网页用户修改一条四象限任务
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/updateMyQuadTask")
    public Response updateMyQuadTask(@RequestBody QuadTaskRequest request,
                                     HttpServletRequest httpServletRequest
    ) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("taskTitle", request.getTaskTitle());
            in.put("important", request.getImportant());
            in.put("endTime", request.getEndTime());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());
            in.put("content", request.getContent());
            in.put("taskId", request.getTaskId());
            in.put("priority", request.getPriority());
            in.put("status", request.getStatus());
            in.put("complete", request.getComplete());

            iWebQuadTaskBService.updateMyQuadTask(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web updateMyQuadTask error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * web端用户读取一条四象限任务详情
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getMyQuadTask")
    public Response getMyQuadTask(@RequestBody QuadTaskRequest request,
                                  HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("taskId", request.getTaskId());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());

            Map out = iWebQuadTaskBService.getMyQuadTask(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web getMyQuadTask error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户设置任务为已完成
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/setMyTaskComplete")
    public Response setMyTaskComplete(@RequestBody QuadTaskRequest request,
                                      HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("taskId", request.getTaskId());

            iWebQuadTaskBService.setMyTaskComplete(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web setMyTaskComplete error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户设置任务为进行中
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/setMyTaskProgress")
    public Response setMyTaskProgress(@RequestBody QuadTaskRequest request,
                                      HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("taskId", request.getTaskId());

            iWebQuadTaskBService.setMyTaskProgress(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web setMyTaskProgress error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户增加任务优先级
     * 增加优先级就是增加task的priority值
     * 每次只能+1
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/increaseQuadTaskPriority")
    public Response increaseQuadTaskPriority(@RequestBody QuadTaskRequest request,
                                         HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("taskId", request.getTaskId());

            iWebQuadTaskBService.increaseQuadTaskPriority(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web increaseQuadTaskPriority error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户减少任务优先级
     * 减少优先级就是减少task的priority字段的值，可以为负数
     * 每次只能-1
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/decreaseQuadTaskPriority")
    public Response decreaseQuadTaskPriority(@RequestBody QuadTaskRequest request,
                                         HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("taskId", request.getTaskId());

            iWebQuadTaskBService.decreaseQuadTaskPriority(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web decreaseQuadTaskPriority error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * web端用户物理删除一个四象限任务
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteQuadTask")
    public Response deleteQuadTask(@RequestBody QuadTaskRequest request,
                                         HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("taskId", request.getTaskId());

            iWebQuadTaskBService.deleteQuadTask(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web deleteQuadTask error:" + ex.getMessage());
            }
        }
        return response;
    }
}
