package cc.cdtime.lifecapsule_v3_api.middle.note;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.note.service.INoteService;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKeyView;
import cc.cdtime.lifecapsule_v3_api.meta.user.service.IUserEncodeKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class NoteMiddle implements INoteMiddle {
    private final INoteService iNoteService;
    private final IUserEncodeKeyService iUserEncodeKeyService;

    public NoteMiddle(INoteService iNoteService,
                      IUserEncodeKeyService iUserEncodeKeyService) {
        this.iNoteService = iNoteService;
        this.iUserEncodeKeyService = iUserEncodeKeyService;
    }

    @Override
    public void createNoteInfo(NoteInfo noteInfo) throws Exception {
        iNoteService.createNoteInfo(noteInfo);
    }

    @Override
    public NoteView getNoteTiny(String noteId, Boolean returnNull, String userId) throws Exception {
        NoteView noteView = iNoteService.getNoteTiny(noteId);
        if (noteView == null) {
            if (returnNull) {
                return null;
            }
            //没有查询到该笔记
            throw new Exception("10007");
        }
        if (userId != null) {
            if (!userId.equals(noteView.getUserId())) {
                //该笔记不属于当前用户
                throw new Exception("10008");
            }
        }
        return noteView;
    }

    @Override
    public NoteView getNoteDetail(String noteId, Boolean returnNull, String userId) throws Exception {
        NoteView noteView = iNoteService.getNoteDetail(noteId);
        if (noteView == null) {
            if (returnNull) {
                return null;
            }
            throw new Exception("10007");
        }
        if (userId != null) {
            if (!userId.equals(noteView.getUserId())) {
                //该笔记不属于当前用户
                throw new Exception("10008");
            }
        }

        /**
         * 读取userEncodeKey
         */
        if (noteView.getEncrypt() != null && noteView.getEncrypt() == 1) {
            if (noteView.getUserEncodeKey() == null) {
                //从userEncodeKey表里取秘钥
                Map qIn = new HashMap();
                qIn.put("indexId", noteView.getNoteId());
                UserEncodeKeyView userEncodeKeyView = iUserEncodeKeyService.getUserEncodeKey(qIn);
                if (userEncodeKeyView == null) {
                    throw new Exception("10037");
                }
                noteView.setUserEncodeKey(userEncodeKeyView.getEncodeKey());
            }
        }
        return noteView;
    }

    @Override
    public ArrayList<NoteView> listNote(Map qIn) throws Exception {
        ArrayList<NoteView> noteViews = iNoteService.listNote(qIn);
        return noteViews;
    }

    @Override
    public Integer totalNote(Map qIn) throws Exception {
        Integer total = iNoteService.totalNote(qIn);
        return total;
    }

    @Override
    public void updateNoteInfo(Map qIn) throws Exception {
        iNoteService.updateNoteInfo(qIn);
    }

    @Override
    public void deleteNote(String noteId) throws Exception {
        iNoteService.deleteNote(noteId);
    }

    @Override
    public ArrayList<NoteView> listNoteTrigger(String userId) throws Exception {
        ArrayList<NoteView> noteViews = iNoteService.listNoteTrigger(userId);
        return noteViews;
    }
}
