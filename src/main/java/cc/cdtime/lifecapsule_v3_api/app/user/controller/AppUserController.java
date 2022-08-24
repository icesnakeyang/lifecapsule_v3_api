package cc.cdtime.lifecapsule_v3_api.app.user.controller;

import cc.cdtime.lifecapsule_v3_api.app.user.business.IAppUserBService;
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
@RequestMapping("/lifecapsule3_api/app/user")
public class AppUserController {
    private final IAppUserBService iAppUserBService;
    private final ICommonService iCommonService;

    public AppUserController(IAppUserBService iAppUserBService,
                             ICommonService iCommonService) {
        this.iAppUserBService = iAppUserBService;
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
            memo.put("frontEnd", ESTags.MOBILE_CLIENT);

            Map out = iAppUserBService.signInByDevice(in);
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
     * 当用户第一次使用app，或者打开app时，手机上没有token信息时，直接创建一个用户账号
     */
    @ResponseBody
    @GetMapping("/signInByNothing")
    public Response signInByNothing() {
        Response response = new Response();
        Map in = new HashMap();
        try {
            Map out = iAppUserBService.signInByNothing(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User signInByNothing error:" + ex.getMessage());
            }
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
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            Map out = iAppUserBService.signInByToken(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User signInByToken error:" + ex.getMessage());
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

            Map out = iAppUserBService.bindEmail(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User signInByEmail error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户通过用户登录名和密码登录
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/loginByLoginName")
    public Response loginByLoginName(@RequestBody UserAccountRequest request,
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

            logMap.put("UserActType", ESTags.USER_LOGIN);
            logMap.put("token", token);
            memo.put("loginName", request.getLoginName());
            memo.put("frontEnd", ESTags.MOBILE_CLIENT);

            Map out = iAppUserBService.loginByLoginName(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User loginByLoginName error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memo.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memo);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("User loginByLoginName user act error:" + ex3.getMessage());
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

            Map out = iAppUserBService.registerByLoginName(in);
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
     * App用户查询自己的个人信息
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getMyProfile")
    public Response getMyProfile(@RequestBody UserAccountRequest request,
                                 HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            Map out = iAppUserBService.getMyProfile(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User getMyProfile error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App用户保存自己的个人信息
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/saveMyProfile")
    public Response saveMyProfile(@RequestBody UserAccountRequest request,
                                  HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("nickname", request.getNickname());
            in.put("email", request.getEmail());

            iAppUserBService.saveMyProfile(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User saveMyProfile error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App用户查询自己的所有email列表
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listMyEmail")
    public Response listMyEmail(@RequestBody UserAccountRequest request,
                                HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());

            Map out = iAppUserBService.listMyEmail(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User listMyEmail error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App用户查询自己的email详情
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getMyEmail")
    public Response getMyEmail(@RequestBody UserAccountRequest request,
                               HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("emailId", request.getEmailId());

            Map out = iAppUserBService.getMyEmail(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User getMyEmail error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App用户设置一个email为默认
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/setDefaultEmail")
    public Response setDefaultEmail(@RequestBody UserAccountRequest request,
                                    HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("emailId", request.getEmailId());

            iAppUserBService.setDefaultEmail(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User setDefaultEmail error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App用户通过token获取用户登录信息，查看是否需要口令登录
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getUserLoginByToken")
    public Response getUserLoginByToken(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            Map out=iAppUserBService.getUserLoginByToken(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User getUserLoginByToken error:" + ex.getMessage());
            }
        }
        return response;
    }

    @ResponseBody
    @GetMapping("/testFetch")
    public Response testFetch() {
        Response response = new Response();
        Map out = new HashMap();
        out.put("value", 33);
        response.setData(out);
        return response;
    }
}
