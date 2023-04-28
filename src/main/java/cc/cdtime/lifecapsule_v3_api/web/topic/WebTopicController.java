package cc.cdtime.lifecapsule_v3_api.web.topic;

import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import cc.cdtime.lifecapsule_v3_api.framework.vo.TopicRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/web/topic")
public class WebTopicController {

    private final IWebTopicBService iWebTopicBService;
    private final ICommonService iCommonService;

    public WebTopicController(IWebTopicBService iWebTopicBService, ICommonService iCommonService) {
        this.iWebTopicBService = iWebTopicBService;
        this.iCommonService = iCommonService;
    }

    /**
     * Web端用户把笔记发表到公共话题讨论区
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/webPublishNoteToTopic")
    public Response webPublishNoteToTopic(@RequestBody TopicRequest request,
                                          HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("title", request.getTitle());
            in.put("content", request.getContent());
            in.put("authorName", request.getAuthorName());

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.USER_PUBLISH_TOPIC);
            memoMap.put("title", request.getTitle());

            iWebTopicBService.webPublishNoteToTopic(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("webPublishNoteToTopic error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web webPublishNoteToTopic user act error:" + ex3.getMessage());
        }
        return response;
    }
}
