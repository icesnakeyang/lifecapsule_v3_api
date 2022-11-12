package cc.cdtime.lifecapsule_v3_api.app.note.controller;

import cc.cdtime.lifecapsule_v3_api.app.note.business.IAppNoteBService;
import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.vo.CategoryRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.NoteRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.metal.MetalFileChooserUI;
import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 手机端用户访问笔记的api服务
 * Api services request form mobile end
 */
@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/note")
public class AppNoteController {
    private final IAppNoteBService iAppNoteBService;
    private final ICommonService iCommonService;

    public AppNoteController(IAppNoteBService iAppNoteBService,
                             ICommonService iCommonService) {
        this.iAppNoteBService = iAppNoteBService;
        this.iCommonService = iCommonService;
    }

    /**
     * 笔记编辑基础操作，增删改查/////////////////////////////////////////////////////////////////////////
     */

    /**
     * App用户查询自己的笔记列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listMyNote")
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
            in.put("categoryId", request.getCategoryId());
            in.put("searchKey", request.getSearchKey());
            in.put("tagList", request.getTagList());

            logMap.put("UserActType", ESTags.USER_LIST_MYNOTE);
            logMap.put("token", token);

            Map out = iAppNoteBService.listMyNote(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listMyNote error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App listMyNote user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * App用户统计自己的笔记数量
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/totalMyNote")
    public Response totalMyNote(@RequestBody NoteRequest request,
                                HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("categoryId", request.getCategoryId());
            in.put("searchKey", request.getSearchKey());

            Map out = iAppNoteBService.totalMyNote(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App totalMyNote error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App用户查询自己的笔记详情
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getMyNote")
    public Response getMyNote(@RequestBody NoteRequest request,
                              HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("keyToken", request.getKeyToken());
            in.put("encryptKey", request.getEncryptKey());

            logMap.put("UserActType", ESTags.USER_GET_NOTE);
            logMap.put("token", token);
            memoMap.put("noteId", request.getNoteId());

            Map out = iAppNoteBService.getMyNote(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getMyNote error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App getMyNote user act error:" + ex3.getMessage());
        }
        return response;

    }

    /**
     * App用户查询自己的笔记的简要信息，不包含笔记内容
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getMyNoteTiny")
    public Response getMyNoteTiny(@RequestBody NoteRequest request,
                                  HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            Map out = iAppNoteBService.getMyNoteTiny(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getMyNoteTiny error:" + ex.getMessage());
            }
        }
        return response;

    }

    /**
     * App端用户保存笔记，如果没有就新增
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/saveMyNote")
    public Response saveMyNote(@RequestBody NoteRequest request,
                               HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("title", request.getTitle());
            in.put("content", request.getContent());
            in.put("keyToken", request.getKeyToken());
            in.put("encryptKey", request.getEncryptKey());
            in.put("encrypt", request.getEncrypt());
            in.put("categoryId", request.getCategoryId());
            in.put("tagList", request.getTagList());

            logMap.put("UserActType", ESTags.USER_SAVE_NOTE);
            logMap.put("token", token);
            memoMap.put("noteId", request.getNoteId());
            memoMap.put("title", request.getTitle());

            Map out = iAppNoteBService.saveMyNote(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App saveMyNote error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App saveMyNote user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * App端用户删除笔记
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteMyNote")
    public Response deleteMyNote(@RequestBody NoteRequest request,
                                 HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            iAppNoteBService.deleteMyNote(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App deleteMyNote error:" + ex.getMessage());
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

            iAppNoteBService.saveMyNoteTags(in);
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
