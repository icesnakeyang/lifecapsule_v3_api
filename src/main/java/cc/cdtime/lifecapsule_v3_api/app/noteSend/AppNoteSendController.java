package cc.cdtime.lifecapsule_v3_api.app.noteSend;

import cc.cdtime.lifecapsule_v3_api.framework.vo.NoteSendRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/noteSend")
public class AppNoteSendController {
    private final IAppNoteSendBService iAppNoteSendBService;

    public AppNoteSendController(IAppNoteSendBService iAppNoteSendBService) {
        this.iAppNoteSendBService = iAppNoteSendBService;
    }

    /**
     * App端用户发送笔记给其他用户
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/sendNote")
    public Response sendNote(@RequestBody NoteSendRequest request,
                             HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("phone", request.getPhone());
            in.put("email", request.getEmail());
            in.put("noteContent", request.getNoteContent());
            in.put("title", request.getTitle());

            iAppNoteSendBService.sendNote(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App sendNote error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App端用户查询其他用户
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/searchUser")
    public Response searchUser(@RequestBody NoteSendRequest request,
                             HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("searchKey", request.getSearchKey());

            Map out=iAppNoteSendBService.searchUser(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App searchUser error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App端用户读取自己的note发送和接收统计信息
     * 未读note数
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/loadMyNoteSendStatistic")
    public Response loadMyNoteSendStatistic(@RequestBody NoteSendRequest request,
                             HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            Map out=iAppNoteSendBService.loadMyNoteSendStatistic(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App loadMyNoteSendStatistic error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App端用户读取自己接收的记录列表
     * 未读note数
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listMyNoteReceiveLog")
    public Response listMyNoteSendLog(@RequestBody NoteSendRequest request,
                             HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());

            Map out=iAppNoteSendBService.listMyNoteReceiveLog(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listMyNoteReceiveLog error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App端用户读取自己发送的笔记列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listMyNoteSendLog")
    public Response listMyNoteSendLogSend(@RequestBody NoteSendRequest request,
                             HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());

            Map out=iAppNoteSendBService.listMyNoteSendLogSend(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listMyNoteSendLogSend error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App端用户读取一个发送的笔记详情
     * 未读note数
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getMyReceiveNote")
    public Response getMyReceiveNote(@RequestBody NoteSendRequest request,
                             HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("sendLogId", request.getSendLogId());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());

            Map out=iAppNoteSendBService.getMyReceiveNote(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getMyReceiveNote error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App端用户物理删除一个发送的笔记
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteMySendNote")
    public Response deleteMySendNote(@RequestBody NoteSendRequest request,
                             HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("sendLogId", request.getSendLogId());

            iAppNoteSendBService.deleteMySendNote(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App deleteMySendNote error:" + ex.getMessage());
            }
        }
        return response;
    }
}