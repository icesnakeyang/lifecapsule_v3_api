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
        noteDao.createNoteDetail(noteInfo);
    }

    @Override
    public NoteView getNoteTiny(String noteId) throws Exception {
        NoteView noteView = noteDao.getNoteTiny(noteId);
        return noteView;
    }

    @Override
    public NoteView getNoteDetail(String noteId) throws Exception {
        NoteView noteView = noteDao.getNoteDetail(noteId);
        return noteView;
    }

    @Override
    public ArrayList<NoteView> listNote(Map qIn) throws Exception {
        ArrayList<NoteView> noteViews = noteDao.listNote(qIn);
        return noteViews;
    }

    @Override
    public Integer totalNote(Map qIn) throws Exception {
        Integer total = noteDao.totalNote(qIn);
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
        String content = (String) qIn.get("content");
        if (content != null) {
            NoteView noteView = noteDao.getNoteDetail(noteId);
            if (noteView == null || noteView.getContent() == null) {
                NoteInfo noteInfo = new NoteInfo();
                noteInfo.setNoteId(noteId);
                noteInfo.setContent(content);
                noteDao.createNoteDetail(noteInfo);
            } else {
                if (!noteView.getContent().equals(content)) {
                    noteDao.updateNoteDetail(qIn);
                }
            }
        }
    }

    @Override
    public void deleteNote(String noteId) throws Exception {
        noteDao.deleteNote(noteId);
        noteDao.deleteNoteDetail(noteId);
    }
}
