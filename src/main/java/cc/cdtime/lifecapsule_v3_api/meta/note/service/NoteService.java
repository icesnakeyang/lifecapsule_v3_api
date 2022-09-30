package cc.cdtime.lifecapsule_v3_api.meta.note.service;

import cc.cdtime.lifecapsule_v3_api.meta.note.dao.NoteDao;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class NoteService implements INoteService {
    private final NoteDao noteDao;

    public NoteService(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    public void createNoteInfo(NoteInfo noteInfo) throws Exception {
        noteDao.createNoteInfo(noteInfo);
    }

    @Override
    public NoteView getNoteInfo(String noteId) throws Exception {
        NoteView noteView = noteDao.getNoteInfo(noteId);
        return noteView;
    }

    @Override
    public ArrayList<NoteView> listNoteInfo(Map qIn) throws Exception {
        ArrayList<NoteView> noteViews = noteDao.listNoteInfo(qIn);
        return noteViews;
    }

    @Override
    public Integer totalNoteInfo(Map qIn) throws Exception {
        Integer total = noteDao.totalNoteInfo(qIn);
        return total;
    }

    @Override
    public void updateNoteInfo(Map qIn) throws Exception {
        /**
         * 检查是否有需要修改的字段
         */
        String noteId = qIn.get("noteId").toString();
        int cc = 0;
        if (qIn.get("title") != null) {
            cc++;
        }
        if (qIn.get("encrypt") != null) {
            cc++;
        }
        if (qIn.get("userEncodeKey") != null) {
            cc++;
        }
        if (qIn.get("categoryId") != null) {
            cc++;
        }
        if (cc > 0) {
            noteDao.updateNoteInfo(qIn);
        }
    }

    @Override
    public void deleteNoteInfo(String noteId) throws Exception {
        noteDao.deleteNoteInfo(noteId);
    }

    @Override
    public ArrayList<NoteView> listNoteTrigger(String userId) throws Exception {
        ArrayList<NoteView> noteViews = noteDao.listNoteTrigger(userId);
        return noteViews;
    }

    @Override
    public ArrayList<NoteView> listHistoryNote(Map qIn) throws Exception {
        ArrayList<NoteView> noteViews = noteDao.listHistoryNote(qIn);
        return noteViews;
    }

    @Override
    public ArrayList<NoteView> listHistoryRandom(Map qIn) throws Exception {
        String userId = qIn.get("userId").toString();
        ArrayList<NoteView> noteViews = noteDao.listHistoryRandom(qIn);
        return noteViews;
    }
}
