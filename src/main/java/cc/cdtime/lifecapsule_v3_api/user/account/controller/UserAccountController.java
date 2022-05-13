package cc.cdtime.lifecapsule_v3_api.user.account.controller;

import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import cc.cdtime.lifecapsule_v3_api.framework.vo.UserAccountRequest;
import cc.cdtime.lifecapsule_v3_api.user.account.business.IUserAccountBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/user/account")
public class UserAccountController {
    private final IUserAccountBService iUserAccountBService;
    private final ICommonService iCommonService;

    public UserAccountController(IUserAccountBService iUserAccountBService,
                                 ICommonService iCommonService) {
        this.iUserAccountBService = iUserAccountBService;
        this.iCommonService = iCommonService;
    }

    /**
     * 用户通过手机设备ID登录或者注册
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/signInByDevice")
    public Response signInByDevice(@RequestBody UserAccountRequest request) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memo = new HashMap();
        try {
            if (request.getDeviceCode() == null) {
                throw new Exception("10004");
            }
            in.put("deviceCode", request.getDeviceCode());
            in.put("deviceName", request.getDeviceName());

            logMap.put("UserActType", ESTags.USER_LOGIN);

            memo.put("deviceCode", request.getDeviceCode());
            memo.put("deviceName", request.getDeviceName());

            Map out = iUserAccountBService.signInByDevice(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User signInByDevice error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memo.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memo);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("User signInByDevice user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * 用户通过token登录
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @GetMapping("/signInByToken")
    public Response signInByToken(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memo = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            logMap.put("UserActType", ESTags.USER_LOGIN);
            logMap.put("token", token);

            Map out = iUserAccountBService.signInByToken(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User signInByToken error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memo.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memo);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("User signInByToken user act error:" + ex3.getMessage());
        }
        return response;
    }

    @ResponseBody
    @GetMapping("/testFetch")
    public Response testFetch() {
        Response response = new Response();
        return response;
    }
}
