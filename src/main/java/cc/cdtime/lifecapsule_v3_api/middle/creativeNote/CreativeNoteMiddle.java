package cc.cdtime.lifecapsule_v3_api.middle.creativeNote;

import cc.cdtime.lifecapsule_v3_api.meta.creativeNote.entity.CreativeNote;
import cc.cdtime.lifecapsule_v3_api.meta.creativeNote.service.ICreativeNoteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class CreativeNoteMiddle implements ICreativeNoteMiddle{
    private final ICreativeNoteService iCreativeNoteService;

    public CreativeNoteMiddle(ICreativeNoteService iCreativeNoteService) {
        this.iCreativeNoteService = iCreativeNoteService;
    }

    @Override
    public void createCreativeNote(CreativeNote creativeNote) throws Exception {
        iCreativeNoteService.createCreativeNote(creativeNote);
    }

    @Override
    public ArrayList<CreativeNote> listCreativeNote(Map qIn) throws Exception {
        ArrayList<CreativeNote> creativeNotes = iCreativeNoteService.listCreativeNote(qIn);
        return creativeNotes;
    }

    @Override
    public void updateCreativeNoteDetail(Map qIn) throws Exception {
        iCreativeNoteService.updateCreativeNoteDetail(qIn);
    }

    @Override
    public void deleteCreativeNote(String noteId) throws Exception {
        iCreativeNoteService.deleteCreativeNote(noteId);
    }
}
