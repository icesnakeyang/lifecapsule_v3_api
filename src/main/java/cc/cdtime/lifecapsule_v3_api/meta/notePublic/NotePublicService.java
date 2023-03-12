package cc.cdtime.lifecapsule_v3_api.meta.notePublic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class NotePublicService implements INotePublicService {
    private final NotePublicDao notePublicDao;

    public NotePublicService(NotePublicDao notePublicDao) {
        this.notePublicDao = notePublicDao;
    }

    @Override
    public void createNotePublic(NotePublic notePublic) throws Exception {
        notePublicDao.createNotePublic(notePublic);
    }

    @Override
    public ArrayList<NotePublicView> listNotePublic(Map qIn) throws Exception {
        ArrayList<NotePublicView> notePublicViews = notePublicDao.listNotePublic(qIn);
        return notePublicViews;
    }

    @Override
    public NotePublicView getNotePublic(String noteId) throws Exception {
        NotePublicView notePublicView = notePublicDao.getNotePublic(noteId);
        return notePublicView;
    }
}
