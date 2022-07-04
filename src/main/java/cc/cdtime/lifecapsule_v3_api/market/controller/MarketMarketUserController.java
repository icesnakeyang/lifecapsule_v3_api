package cc.cdtime.lifecapsule_v3_api.market.controller;

import cc.cdtime.lifecapsule_v3_api.framework.vo.MarketUserRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import cc.cdtime.lifecapsule_v3_api.market.business.IMarketMarketUserBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/market/marketUser")
public class MarketMarketUserController {
    private final IMarketMarketUserBService iMarketMarketUserBService;

    public MarketMarketUserController(IMarketMarketUserBService iMarketMarketUserBService) {
        this.iMarketMarketUserBService = iMarketMarketUserBService;
    }

    @ResponseBody
    @PostMapping("/createMarketUser")
    public Response createMarketUser(@RequestBody MarketUserRequest request) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            in.put("loginName", request.getLoginName());
            in.put("loginPassword", request.getLoginPassword());

            iMarketMarketUserBService.createMarketUser(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Market createMarketUser error:" + ex.getMessage());
            }
        }
        return response;
    }

    @ResponseBody
    @PostMapping("/login")
    public Response login(@RequestBody MarketUserRequest request) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            in.put("loginName", request.getLoginName());
            in.put("loginPassword", request.getLoginPassword());

            Map out = iMarketMarketUserBService.login(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Market login error:" + ex.getMessage());
            }
        }
        return response;
    }
}
