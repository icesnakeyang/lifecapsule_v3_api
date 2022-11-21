package cc.cdtime.lifecapsule_v3_api.web.user;

import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import cc.cdtime.lifecapsule_v3_api.framework.vo.UserAccountRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/web/user")
public class WebUserController {
    private final IWebUserBService iWebUserBService;
    private final ICommonService iCommonService;

    public WebUserController(IWebUserBService iWebUserBService,
                             ICommonService iCommonService) {
        this.iWebUserBService = iWebUserBService;
        this.iCommonService = iCommonService;
    }

    /**
     * token登录
     */
    @ResponseBody
    @GetMapping("/sign_token")
    public Response signByToken(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            if (token == null || token.equals("")) {
                //token不正确
                throw new Exception("10029");
            }
            in.put("token", token);

            Map out = iWebUserBService.signByToken(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web user signByToken error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * web端用户通过token获取个人账户信息
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/get_user_by_token")
    public Response getUserInfoByToken(@RequestBody UserAccountRequest request,
                                       HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            Map out = iWebUserBService.getUserInfoByToken(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User web loginByToken error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 当用户第一次使用web，web storage上没有token信息时，直接创建一个用户账号
     */
    @ResponseBody
    @GetMapping("/signInByNothing")
    public Response signInByNothing() {
        Response response = new Response();
        Map in = new HashMap();
        try {
            Map out = iWebUserBService.signInByNothing(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("web user signInByNothing error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户绑定email
     * email通过验证后，绑定给用户账号
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/bindEmail")
    public Response bindEmail(@RequestBody UserAccountRequest request,
                              HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("email", request.getEmail());
            in.put("emailCode", request.getEmailCode());

            Map out = iWebUserBService.bindEmail(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web user bindEmail error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * web 用户保存昵称
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/saveUserNickname")
    public Response saveUserNickname(@RequestBody UserAccountRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("nickname", request.getNickname());

            iWebUserBService.saveUserNickname(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web saveUserNickname error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App用户通过email验证登录
     */
    @ResponseBody
    @PostMapping("/signByEmail")
    public Response signByEmail(@RequestBody UserAccountRequest request) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            in.put("email", request.getEmail());
            in.put("emailCode", request.getEmailCode());

            Map out=iWebUserBService.signByEmail(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web user signByEmail error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户请求发送邮箱验证码
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/sendVerifyCodeToEmail")
    public Response sendVerifyCodeToEmail(@RequestBody UserAccountRequest request) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            in.put("email", request.getEmail());
            in.put("actType", request.getActType());

            iWebUserBService.sendVerifyCodeToEmail(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web user sendVerifyCodeToEmail error:" + ex.getMessage());
            }
        }
        return response;
    }
}
