package cc.cdtime.lifecapsule_v3_api.app.loveLetter;

import cc.cdtime.lifecapsule_v3_api.app.lastWords.LastWordsRequest;
import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/loveLetter")
public class AppLoveLetterController {
    private final ICommonService iCommonService;
    private final IAppLoveLetterBService iAppLoveLetterBService;

    public AppLoveLetterController(ICommonService iCommonService,
                                   IAppLoveLetterBService iAppLoveLetterBService) {
        this.iCommonService = iCommonService;
        this.iAppLoveLetterBService = iAppLoveLetterBService;
    }

    /**
     * App用户查询自己的情书列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listLoveLetter")
    public Response listLoveLetter(@RequestBody LoveLetterRequest request,
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

            logMap.put("UserActType", ESTags.LIST_LOVE_LETTER);
            logMap.put("token", token);

            Map out = iAppLoveLetterBService.listLoveLetter(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listLoveLetter error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App listLoveLetter user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * App用户查询自己的情书详情
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getLoveLetter")
    public Response getLoveLetter(@RequestBody LoveLetterRequest request,
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

            logMap.put("UserActType", ESTags.GET_LOVE_LETTER);
            logMap.put("token", token);

            Map out = iAppLoveLetterBService.getLoveLetter(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getLoveLetter error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App getLoveLetter user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * 用户创建或者修改一封情书
     */
    @ResponseBody
    @PostMapping("/saveLoveLetter")
    public Response saveLoveLetter(@RequestBody LoveLetterRequest request,
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
            in.put("sendDateTime", request.getSendDateTime());

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.SAVE_LOVE_LETTER);

            memoMap.put("title", request.getTitle());

            iAppLoveLetterBService.saveLoveLetter(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App saveLoveLetter error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App saveLoveLetter user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * 用户删除自己的情书
     */
    @ResponseBody
    @PostMapping("/deleteMyLoveLetter")
    public Response deleteMyLoveLetter(@RequestBody LastWordsRequest request,
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
            logMap.put("UserActType", ESTags.DELETE_LOVE_LETTER);
            memoMap.put("noteId", request.getNoteId());

            iAppLoveLetterBService.deleteMyLoveLetter(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App deleteMyLoveLetter error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App deleteMyLoveLetter user act error:" + ex3.getMessage());
        }
        return response;
    }
}
