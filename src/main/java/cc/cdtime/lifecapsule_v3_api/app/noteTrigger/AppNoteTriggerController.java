package cc.cdtime.lifecapsule_v3_api.app.noteTrigger;

import cc.cdtime.lifecapsule_v3_api.framework.vo.NoteTriggerRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/noteTrigger")
public class AppNoteTriggerController {
    private final IAppNoteTriggerBService iAppNoteTriggerBService;

    public AppNoteTriggerController(IAppNoteTriggerBService iAppNoteTriggerBService) {
        this.iAppNoteTriggerBService = iAppNoteTriggerBService;
    }

    /**
     * App端用户查询自己的触发笔记列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listMyNoteTrigger")
    public Response listMyNoteTrigger(@RequestBody NoteTriggerRequest request,
                                      HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            Map out = iAppNoteTriggerBService.listMyNoteTrigger(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listMyNoteTrigger error:" + ex.getMessage());
            }
        }
        return response;
    }
}
