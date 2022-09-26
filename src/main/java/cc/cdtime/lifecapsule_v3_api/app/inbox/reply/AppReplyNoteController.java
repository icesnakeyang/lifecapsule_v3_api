package cc.cdtime.lifecapsule_v3_api.app.inbox.reply;

import cc.cdtime.lifecapsule_v3_api.framework.vo.InboxRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/reply")
public class AppReplyNoteController {
    private final IAppReplyNoteBService iAppReplyNoteBService;

    public AppReplyNoteController(IAppReplyNoteBService iAppReplyNoteBService) {
        this.iAppReplyNoteBService = iAppReplyNoteBService;
    }

    /**
     * 用户回复一条收到的笔记
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/replyReceiveNote")
    public Response replyReceiveNote(@RequestBody InboxRequest request,
                                    HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("title", request.getTitle());
            in.put("content", request.getContent());
            in.put("pid", request.getPid());
            in.put("keyToken", request.getKeyToken());
            in.put("encryptKey", request.getEncryptKey());

            iAppReplyNoteBService.replyReceiveNote(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App replyReceiveNote error:" + ex.getMessage());
            }
        }
        return response;
    }
}
