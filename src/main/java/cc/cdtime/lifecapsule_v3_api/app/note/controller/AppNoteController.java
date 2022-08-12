package cc.cdtime.lifecapsule_v3_api.app.note.controller;

import cc.cdtime.lifecapsule_v3_api.app.note.business.IAppNoteBService;
import cc.cdtime.lifecapsule_v3_api.framework.vo.CategoryRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.NoteRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    public AppNoteController(IAppNoteBService iAppNoteBService) {
        this.iAppNoteBService = iAppNoteBService;
    }

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
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());
            in.put("categoryId", request.getCategoryId());
            in.put("keyword", request.getKeyword());

            Map out = iAppNoteBService.listMyNote(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listMyNote error:" + ex.getMessage());
            }
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
            in.put("keyword", request.getKeyword());

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
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("keyToken", request.getKeyToken());
            in.put("encryptKey", request.getEncryptKey());

            Map out = iAppNoteBService.getMyNote(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getMyNote error:" + ex.getMessage());
            }
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

            Map out=iAppNoteBService.saveMyNote(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App saveMyNote error:" + ex.getMessage());
            }
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
     * 用户查看接收人列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listRecipient")
    public Response listRecipient(@RequestBody NoteRequest request,
                                  HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("triggerId", request.getTriggerId());
            in.put("noteId", request.getNoteId());

            Map out = iAppNoteBService.listRecipient(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User listRecipient error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户查看笔记的触发器
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listNoteTrigger")
    public Response listNoteTrigger(@RequestBody NoteRequest request,
                                    HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            Map out = iAppNoteBService.listNoteTrigger(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User listNoteTrigger error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户保存笔记的触发器
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/saveNoteTrigger")
    public Response saveNoteTrigger(@RequestBody NoteRequest request,
                                    HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("remark", request.getRemark());
            in.put("triggerType", request.getTriggerType());
            in.put("triggerTime", request.getTriggerTime());
            in.put("triggerId", request.getTriggerId());

            Map out = iAppNoteBService.saveNoteTrigger(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User createNoteTrigger error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户创建一个笔记的接收人
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/createNoteRecipient")
    public Response createNoteRecipient(@RequestBody NoteRequest request,
                                        HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("triggerId", request.getTriggerId());
            in.put("name", request.getName());
            in.put("phone", request.getPhone());
            in.put("email", request.getEmail());
            in.put("remark", request.getRemark());

            iAppNoteBService.createNoteRecipient(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User createNoteRecipient error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户删除笔记的接收人
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteNoteRecipient")
    public Response deleteNoteRecipient(@RequestBody NoteRequest request,
                                        HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("ids", request.getIds());

            iAppNoteBService.deleteNoteRecipient(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User deleteNoteRecipient error:" + ex.getMessage());
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
    @PostMapping("/listMyCategory")
    public Response listMyCategory(@RequestBody CategoryRequest request,
                                   HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());
            in.put("categoryId", request.getCategoryId());

            Map out = iAppNoteBService.listMyCategory(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User listMyCategory error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 查询当前用户的一个笔记分类详情
     *
     * @param
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getMyCategory")
    public Response getMyCategory(@RequestBody CategoryRequest request,
                                   HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("categoryId", request.getCategoryId());

            Map out = iAppNoteBService.getMyCategory(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getMyCategory error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 获取用户的默认笔记分类详情
     *
     * @param
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @GetMapping("/getMyDefaultCategory")
    public Response getMyDefaultCategory(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            Map out = iAppNoteBService.getMyDefaultCategory(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getMyDefaultCategory error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户创建或者修改自己的笔记分类
     *
     * @param
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/saveMyCategory")
    public Response saveMyCategory(@RequestBody CategoryRequest request,
                                   HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("categoryName", request.getCategoryName());
            in.put("remark", request.getRemark());
            in.put("categoryId", request.getCategoryId());

            iAppNoteBService.saveMyCategory(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App saveMyCategory error:" + ex.getMessage());
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
    @PostMapping("/deleteMyCategory")
    public Response deleteMyCategory(@RequestBody CategoryRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("categoryId", request.getCategoryId());
            iAppNoteBService.deleteMyCategory(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App deleteMyCategory error:" + ex.getMessage());
            }
        }
        return response;
    }
}
