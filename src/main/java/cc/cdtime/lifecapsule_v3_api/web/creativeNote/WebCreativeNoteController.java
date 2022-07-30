package cc.cdtime.lifecapsule_v3_api.web.creativeNote;

import cc.cdtime.lifecapsule_v3_api.framework.vo.CreativeNoteRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/web/creativeNote")
public class WebCreativeNoteController {
    private final IWebCreativeNoteBService iWebCreativeNoteBService;

    public WebCreativeNoteController(IWebCreativeNoteBService iWebCreativeNoteBService) {
        this.iWebCreativeNoteBService = iWebCreativeNoteBService;
    }

    @ResponseBody
    @PostMapping("/listMyCreativeNote")
    public Response listMyCreativeNote(@RequestBody CreativeNoteRequest request,
                                       HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());

            Map out = iWebCreativeNoteBService.listMyCreativeNote(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web listMyCreativeNote error:" + ex.getMessage());
            }
        }
        return response;
    }

    @ResponseBody
    @PostMapping("/getMyCreativeNote")
    public Response getMyCreativeNote(@RequestBody CreativeNoteRequest request,
                                      HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());

            Map out = iWebCreativeNoteBService.getMyCreativeNote(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web getMyCreativeNote error:" + ex.getMessage());
            }
        }
        return response;
    }

    @ResponseBody
    @PostMapping("/saveCreativeNote")
    public Response saveCreativeNote(@RequestBody CreativeNoteRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("detail1", request.getDetail1());
            in.put("detail2", request.getDetail2());
            in.put("detail3", request.getDetail3());
            in.put("detail4", request.getDetail4());
            in.put("noteId", request.getNoteId());
            in.put("categoryId", request.getCategoryId());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());
            in.put("tasks", request.getTasks());
            in.put("noteTitle", request.getNoteTitle());

            Map out = iWebCreativeNoteBService.saveCreativeNote(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web saveCreativeNote error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 物理删除防拖延日记
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteCreativeNote")
    public Response deleteCreativeNote(@RequestBody CreativeNoteRequest request,
                                       HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            iWebCreativeNoteBService.deleteCreativeNote(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web deleteCreativeNote error:" + ex.getMessage());
            }
        }
        return response;
    }
}
