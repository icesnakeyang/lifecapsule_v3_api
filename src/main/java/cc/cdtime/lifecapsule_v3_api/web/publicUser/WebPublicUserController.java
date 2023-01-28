package cc.cdtime.lifecapsule_v3_api.web.publicUser;

import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import cc.cdtime.lifecapsule_v3_api.framework.vo.TriggerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/web/publicUser")
public class WebPublicUserController {
    private final ICommonService iCommonService;
    private final IWebPublicUserBService iWebPublicUserBService;

    public WebPublicUserController(ICommonService iCommonService,
                                   IWebPublicUserBService iWebPublicUserBService) {
        this.iCommonService = iCommonService;
        this.iWebPublicUserBService = iWebPublicUserBService;
    }

    /**
     * 用户从email邮箱跳转到web页面查看发送的笔记内容
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/getNoteFromMail")
    public Response getNoteFromMail(@RequestBody TriggerRequest request) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            in.put("triggerId", request.getTriggerId());

            logMap.put("", ESTags.GET_NOTE_FROM_MAIL);
            memoMap.put("triggerId", request.getTriggerId());

            Map out = iWebPublicUserBService.getNoteFromMail(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web getNoteFromMail error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web getNoteFromMail user act error:" + ex3.getMessage());
        }
        return response;
    }
}
