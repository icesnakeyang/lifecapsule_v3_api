package cc.cdtime.lifecapsule_v3_api.web.note;

import cc.cdtime.lifecapsule_v3_api.framework.vo.CategoryRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.NoteRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.NoteSendRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/web/note")
public class WebNoteController {
    private final IWebNoteBService iWebNoteBService;

    public WebNoteController(IWebNoteBService iWebNoteBService) {
        this.iWebNoteBService = iWebNoteBService;
    }

    @ResponseBody
    @PostMapping("/list_my_note")
    public Response listMyNote(@RequestBody NoteRequest request,
                               HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());
            in.put("categoryId", request.getCategoryId());

            Map out = iWebNoteBService.listMyNote(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web user listMyNote error:" + ex.getMessage());
            }
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
            in.put("categoryId", request.getCategoryId());
            in.put("title", request.getTitle());
            in.put("content", request.getContent());
            in.put("encrypt", request.getEncrypt());

            Map out=iWebNoteBService.saveMyNote(in);
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
     * 查询当前用户的所有笔记分类列表
     *
     * @param
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/list_my_category")
    public Response listMyCategory(@RequestBody CategoryRequest request,
                                   HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("categoryId", request.getCategoryId());

            Map out = iWebNoteBService.listMyCategory(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User web listMyCategory error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 新增或者修改一个笔记分类信息
     *
     * @param
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/save_my_category")
    public Response saveMyCategory(@RequestBody CategoryRequest request,
                                   HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            if (token == null || token.equals("")) {
                throw new Exception("10029");
            }
            in.put("token", token);
            in.put("categoryId", request.getCategoryId());
            in.put("categoryName", request.getCategoryName());
            in.put("remark", request.getRemark());

            iWebNoteBService.saveMyCategory(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User web saveMyCategory error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户删除一个笔记分类
     * 如果该分类下有笔记，就自动转到NORMAL分类里
     *
     * @param
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/delete_my_category")
    public Response deleteMyCategory(@RequestBody CategoryRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("categoryId", request.getCategoryId());
            iWebNoteBService.deleteMyCategory(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web deleteMyCategory error:" + ex.getMessage());
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

            Map out=iWebNoteBService.loadMyNoteSendStatistic(in);
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
}
