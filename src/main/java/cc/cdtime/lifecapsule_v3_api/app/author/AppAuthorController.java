package cc.cdtime.lifecapsule_v3_api.app.author;

import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/author")
public class AppAuthorController {
    private final IAppAuthorBService iAppAuthorBService;

    public AppAuthorController(IAppAuthorBService iAppAuthorBService) {
        this.iAppAuthorBService = iAppAuthorBService;
    }

    /**
     * App用户读取自己默认的笔名
     */
    @ResponseBody
    @GetMapping("/getMyAuthorInfo")
    public Response getMyAuthorInfo(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token=httpServletRequest.getHeader("token");
            in.put("token",token);

            Map out=iAppAuthorBService.getMyAuthorInfo(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getMyAuthorInfo error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App用户读取自己默认的笔名
     */
    @ResponseBody
    @PostMapping("/listMyAuthorInfo")
    public Response listMyAuthorInfo(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token=httpServletRequest.getHeader("token");
            in.put("token",token);

            Map out=iAppAuthorBService.listMyAuthorInfo(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listMyAuthorInfo error:" + ex.getMessage());
            }
        }
        return response;
    }
}
