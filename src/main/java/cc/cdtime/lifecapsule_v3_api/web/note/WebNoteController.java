package cc.cdtime.lifecapsule_v3_api.web.note;

import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.vo.NoteRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.NoteSendRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/web/note")
public class WebNoteController {
    private final IWebNoteBService iWebNoteBService;
    private final ICommonService iCommonService;

    public WebNoteController(IWebNoteBService iWebNoteBService,
                             ICommonService iCommonService) {
        this.iWebNoteBService = iWebNoteBService;
        this.iCommonService = iCommonService;
    }

    @ResponseBody
    @PostMapping("/list_my_note")
    public Response listMyNote(@RequestBody NoteRequest request,
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
            in.put("tagList", request.getTagList());
            in.put("searchKey", request.getSearchKey());

            logMap.put("UserActType", ESTags.USER_LIST_MYNOTE);
            logMap.put("token", token);

            Map out = iWebNoteBService.listMyNote(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web user listMyNote error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web user listMyNote user act error:" + ex3.getMessage());
        }
        return response;
    }

    @ResponseBody
    @PostMapping("/get_my_note")
    public Response getMyNote(@RequestBody NoteRequest request,
                              HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("keyToken", request.getKeyToken());
            in.put("encryptKey", request.getEncryptKey());

            Map out = iWebNoteBService.getMyNote(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web user getMyNote error:" + ex.getMessage());
            }
        }
        return response;
    }

    @ResponseBody
    @PostMapping("/save_my_note")
    public Response saveMyNote(@RequestBody NoteRequest request,
                               HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("keyToken", request.getKeyToken());
            in.put("encryptKey", request.getEncryptKey());
            in.put("encrypt", request.getEncrypt());
            in.put("title", request.getTitle());
            in.put("content", request.getContent());
            in.put("tagList", request.getTagList());

            Map out = iWebNoteBService.saveMyNote(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web user saveMyNote error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户物理删除一篇笔记
     *
     * @param
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/delete_my_note")
    public Response deleteMyNote(@RequestBody NoteRequest request,
                                 HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            iWebNoteBService.deleteMyNote(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web deleteMyNote error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * web端用户读取自己的note发送和接收统计信息
     * 未读note数
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/loadMyNoteSendStatistic")
    public Response loadMyNoteSendStatistic(@RequestBody NoteSendRequest request,
                                            HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            Map out = iWebNoteBService.loadMyNoteSendStatistic(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web loadMyNoteSendStatistic error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 保存笔记标签的更改
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/saveMyNoteTags")
    public Response saveMyNoteTags(@RequestBody NoteRequest request,
                                   HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("tagList", request.getTagList());
            in.put("noteId", request.getNoteId());

            iWebNoteBService.saveMyNoteTags(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web saveMyNoteTags error:" + ex.getMessage());
            }
        }
        return response;
    }
}
