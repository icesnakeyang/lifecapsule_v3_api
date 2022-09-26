package cc.cdtime.lifecapsule_v3_api.app.home;

import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/home")
public class AppHomeController {
    private final IAppHomeBService iAppHomeBService;

    public AppHomeController(IAppHomeBService iAppHomeBService) {
        this.iAppHomeBService = iAppHomeBService;
    }

    /**
     * App端用户首页查询统计数据
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/loadMyNoteSendStatistic")
    public Response loadMyNoteSendStatistic(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            Map out = iAppHomeBService.loadMyNoteSendStatistic(in);
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
}
