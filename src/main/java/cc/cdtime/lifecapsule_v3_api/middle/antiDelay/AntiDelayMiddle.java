package cc.cdtime.lifecapsule_v3_api.middle.antiDelay;

import cc.cdtime.lifecapsule_v3_api.meta.antiDelay.entity.AntiDelayNote;
import cc.cdtime.lifecapsule_v3_api.meta.antiDelay.entity.AntiDelayView;
import cc.cdtime.lifecapsule_v3_api.meta.antiDelay.service.AntiDelayNoteService;
import cc.cdtime.lifecapsule_v3_api.meta.antiDelay.service.IAntiDelayNoteService;
import cc.cdtime.lifecapsule_v3_api.meta.content.entity.Content;
import cc.cdtime.lifecapsule_v3_api.meta.content.service.IContentService;
import cc.cdtime.lifecapsule_v3_api.meta.creativeNote.entity.CreativeNote;
import cc.cdtime.lifecapsule_v3_api.meta.task.service.ITaskTodoService;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKey;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKeyView;
import cc.cdtime.lifecapsule_v3_api.meta.user.service.IUserEncodeKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
public class AntiDelayMiddle implements IAntiDelayMiddle {
    private final IAntiDelayNoteService iAntiDelayNoteService;
    private final IContentService iContentService;
    private final IUserEncodeKeyService iUserEncodeKeyService;
    private final ITaskTodoService iTaskTodoService;

    public AntiDelayMiddle(IAntiDelayNoteService iAntiDelayNoteService,
                           IContentService iContentService,
                           IUserEncodeKeyService iUserEncodeKeyService,
                           ITaskTodoService iTaskTodoService) {
        this.iAntiDelayNoteService = iAntiDelayNoteService;
        this.iContentService = iContentService;
        this.iUserEncodeKeyService = iUserEncodeKeyService;
        this.iTaskTodoService = iTaskTodoService;
    }

    @Override
    public void createAntiDelayNote(AntiDelayNote antiDelayNote) throws Exception {
        iAntiDelayNoteService.createAntiDelayNote(antiDelayNote);
        if (antiDelayNote.getContent() != null) {
            Content content = new Content();
            content.setContent(antiDelayNote.getContent());
            content.setIndexId(antiDelayNote.getAntiDelayId());
            iContentService.createContent(content);
        }
    }

    @Override
    public ArrayList<AntiDelayView> listAntiDelayNote(Map qIn) throws Exception {
        ArrayList<AntiDelayView> antiDelayViews = iAntiDelayNoteService.listAntiDelayNote(qIn);
        return antiDelayViews;
    }

    @Override
    public void updateAntiDelayNote(AntiDelayNote antiDelayNote) throws Exception {
        if (antiDelayNote.getContent() != null) {
            Map qIn = new HashMap();
            qIn.put("indexId", antiDelayNote.getAntiDelayId());
            qIn.put("content", antiDelayNote.getContent());
            iContentService.updateContent(qIn);
        }
        if (antiDelayNote.getUserEncodeKey() != null) {
            Map qIn = new HashMap();
            qIn.put("encodeKey", antiDelayNote.getUserEncodeKey());
            qIn.put("indexId", antiDelayNote.getAntiDelayId());
            iUserEncodeKeyService.updateUserEncodeKey(qIn);
        }
    }

    @Override
    public AntiDelayView getAntiDelayNote(String antiDelayId) throws Exception {
        AntiDelayView antiDelayView = iAntiDelayNoteService.getAntiDelayNote(antiDelayId);
        Content content = iContentService.getContent(antiDelayId);
        antiDelayView.setContent(content.getContent());
        return antiDelayView;
    }

    @Override
    public void deleteAntiDelayNote(String noteId) throws Exception {
        Map qIn = new HashMap();
        qIn.put("noteId", noteId);
        ArrayList<AntiDelayView> antiDelayViews = listAntiDelayNote(qIn);
        for (int i = 0; i < antiDelayViews.size(); i++) {
            String id = antiDelayViews.get(i).getAntiDelayId();
            iContentService.deleteContent(id);
            iUserEncodeKeyService.deleteUserEncodeKey(id);
        }
        //删除任务
        iTaskTodoService.deleteTaskTodo(qIn);
        iAntiDelayNoteService.deleteAntiDelayNote(noteId);
    }
}
