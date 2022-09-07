package cc.cdtime.lifecapsule_v3_api.meta.creativeNote.service;

import cc.cdtime.lifecapsule_v3_api.meta.creativeNote.dao.CreativeNoteDao;
import cc.cdtime.lifecapsule_v3_api.meta.creativeNote.entity.CreativeNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class CreativeNoteService implements ICreativeNoteService{
    private final CreativeNoteDao creativeNoteDao;

    public CreativeNoteService(CreativeNoteDao creativeNoteDao) {
        this.creativeNoteDao = creativeNoteDao;
    }

    @Override
    public void createCreativeNote(CreativeNote creativeNote) throws Exception {
        creativeNoteDao.createCreativeNote(creativeNote);
    }

    @Override
    public ArrayList<CreativeNote> listCreativeNote(Map qIn) throws Exception {
        ArrayList<CreativeNote> creativeNotes = creativeNoteDao.listCreativeNote(qIn);
        return creativeNotes;
    }

    @Override
    public void deleteCreativeNote(String noteId) throws Exception {
        creativeNoteDao.deleteCreativeNote(noteId);
    }
}
