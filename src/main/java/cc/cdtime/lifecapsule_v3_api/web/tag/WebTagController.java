package cc.cdtime.lifecapsule_v3_api.web.tag;

import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import cc.cdtime.lifecapsule_v3_api.framework.vo.TagRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/web/tag")
public class WebTagController {
    private final IWebTagBService iWebTagBService;

    public WebTagController(IWebTagBService iWebTagBService) {
        this.iWebTagBService = iWebTagBService;
    }

    /**
     * 读取用户的所有笔记标签列表
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @GetMapping("/listUserNoteTag")
    public Response listUserNoteTag(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            Map out = iWebTagBService.listUserNoteTag(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web listUserNoteTag error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 查询最热的10条笔记标签
     *
     */
    @ResponseBody
    @GetMapping("/listHotNoteTags")
    public Response listHotNoteTags() {
        Response response = new Response();
        Map in = new HashMap();
        try {
            Map out = iWebTagBService.listHotNoteTags(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listHotNoteTags error:" + ex.getMessage());
            }
        }
        return response;
    }
}
