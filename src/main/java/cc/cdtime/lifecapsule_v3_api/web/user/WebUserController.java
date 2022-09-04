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
     * 用户通过web页面注册
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/register")
    public Response register(@RequestBody UserAccountRequest request) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            in.put("loginName", request.getLoginName());
            in.put("password", request.getPassword());

            Map out = iWebUserBService.register(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web user register error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户通过web页面登录
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/login")
    public Response login(@RequestBody UserAccountRequest request) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            in.put("loginName", request.getLoginName());
            in.put("password", request.getPassword());

            Map out = iWebUserBService.login(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web user login error:" + ex.getMessage());
            }
        }
        return response;
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
     * 用户通过用户登录名和密码注册账号
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/registerByLoginName")
    public Response registerByLoginName(@RequestBody UserAccountRequest request,
                                        HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memo = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("loginName", request.getLoginName());
            in.put("password", request.getPassword());

            logMap.put("UserActType", ESTags.USER_REGISTER);
            logMap.put("token", token);
            memo.put("loginName", request.getLoginName());

            Map out = iWebUserBService.registerByLoginName(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User registerByLoginName error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memo.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memo);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("User registerByLoginName user act error:" + ex3.getMessage());
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
}
