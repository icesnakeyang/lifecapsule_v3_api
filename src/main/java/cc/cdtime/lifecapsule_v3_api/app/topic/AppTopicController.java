package cc.cdtime.lifecapsule_v3_api.app.topic;

import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import cc.cdtime.lifecapsule_v3_api.framework.vo.TopicRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/topic")
public class AppTopicController {
    private final IAppTopicBService iAppTopicBService;
    private final ICommonService iCommonService;

    public AppTopicController(IAppTopicBService iAppTopicBService,
                              ICommonService iCommonService) {
        this.iAppTopicBService = iAppTopicBService;
        this.iCommonService = iCommonService;
    }

    /**
     * App端用户把笔记发表到公共话题讨论区
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/publishNoteToTopic")
    public Response publishNoteToTopic(@RequestBody TopicRequest request,
                                       HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("title", request.getTitle());
            in.put("content", request.getContent());
            in.put("authorName", request.getAuthorName());

            iAppTopicBService.publishNoteToTopic(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App publishNoteToTopic error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App读取一条话题详情
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getTopicDetail")
    public Response getTopicDetail(@RequestBody TopicRequest request,
                                   HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap=new HashMap();
        Map memoMap=new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("topicId", request.getTopicId());

            logMap.put("UserActType", ESTags.READ_TOPIC);
            logMap.put("token", token);
            memoMap.put("topicId", request.getTopicId());

            Map out = iAppTopicBService.getTopicDetail(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getTopicDetail error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        }catch (Exception ex3){
            log.error("App getTopicDetail user act error:"+ex3.getMessage());
        }
        return response;
    }

    /**
     * App回复一条话题
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/replyComment")
    public Response replyComment(@RequestBody TopicRequest request,
                                 HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("topicId", request.getTopicId());
            in.put("title", request.getTitle());
            in.put("content", request.getContent());
            in.put("comment", request.getComment());
            in.put("authorName", request.getAuthorName());

            iAppTopicBService.replyComment(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App replyComment error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App查询最热的话题标签
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listHotTopicTags")
    public Response listHotTopicTags(@RequestBody TopicRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map memoMap = new HashMap();
        Map logMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            logMap.put("UserActType", ESTags.READ_TOPIC);
            logMap.put("token", token);

            Map out = iAppTopicBService.listHotTopicTags(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listHotTopicTags error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App listHotTopicTags user act error:" + ex3.getMessage());
        }
        return response;
    }


}
