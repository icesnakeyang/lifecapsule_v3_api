package cc.cdtime.lifecapsule_v3_api.app.lastWords;

import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/lastWords")
public class AppLastWordsController {
    private final IAppLastWordsBService iAppLastWordsBService;
    private final ICommonService iCommonService;

    public AppLastWordsController(IAppLastWordsBService iAppLastWordsBService,
                                  ICommonService iCommonService) {
        this.iAppLastWordsBService = iAppLastWordsBService;
        this.iCommonService = iCommonService;
    }

    /**
     * 用户创建一封遗书
     */
    @ResponseBody
    @PostMapping("/saveLastWords")
    public Response saveLastWords(@RequestBody LastWordsRequest request,
                                  HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("toEmail", request.getToEmail());
            in.put("content", request.getContent());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());
            in.put("title", request.getTitle());
            in.put("toName", request.getToName());
            in.put("fromName", request.getFromName());
            in.put("noteId", request.getNoteId());

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.SAVE_LAST_WORDS);

            memoMap.put("title", request.getTitle());

            iAppLastWordsBService.saveLastWords(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App saveLastWords error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App saveLastWords user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * 用户查询自己的遗书列表
     */
    @ResponseBody
    @PostMapping("/listLastWords")
    public Response listLastWords(@RequestBody LastWordsRequest request,
                                  HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.LIST_LAST_WORDS);

            Map out = iAppLastWordsBService.listLastWords(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listLastWords error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App listLastWords user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * 用户查询自己的遗书详情
     */
    @ResponseBody
    @PostMapping("/getMyLastWords")
    public Response getMyLastWords(@RequestBody LastWordsRequest request,
                                   HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.GET_LAST_WORDS);
            memoMap.put("noteId", request.getNoteId());


            Map out = iAppLastWordsBService.getMyLastWords(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getMyLastWords error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App getMyLastWords user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * 用户删除自己的遗书
     */
    @ResponseBody
    @PostMapping("/deleteMyLastWords")
    public Response deleteMyLastWords(@RequestBody LastWordsRequest request,
                                      HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.DELETE_LAST_WORDS);
            memoMap.put("noteId", request.getNoteId());


            iAppLastWordsBService.deleteMyLastWords(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App deleteMyLastWords error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App deleteMyLastWords user act error:" + ex3.getMessage());
        }
        return response;
    }
}
