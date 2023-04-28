package cc.cdtime.lifecapsule_v3_api.middle.publicWeb;

import cc.cdtime.lifecapsule_v3_api.meta.notePublic.INotePublicService;
import cc.cdtime.lifecapsule_v3_api.meta.notePublic.NotePublic;
import cc.cdtime.lifecapsule_v3_api.meta.notePublic.NotePublicView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class PublicWebMiddle implements IPublicWebMiddle {
    private final INotePublicService iNotePublicService;

    public PublicWebMiddle(INotePublicService iNotePublicService) {
        this.iNotePublicService = iNotePublicService;
    }

    @Override
    public void createNotePublic(NotePublic notePublic) throws Exception {
        iNotePublicService.createNotePublic(notePublic);
    }

    @Override
    public ArrayList<NotePublicView> listNotePublic(Map qIn) throws Exception {
        ArrayList<NotePublicView> notePublicViews = iNotePublicService.listNotePublic(qIn);
        return notePublicViews;
    }

    @Override
    public NotePublicView getNotePublic(String noteId) throws Exception {
        NotePublicView notePublicView = iNotePublicService.getNotePublic(noteId);
        return notePublicView;
    }

    @Override
    public void updateNotePublic(Map qIn) throws Exception {
        iNotePublicService.updateNotePublic(qIn);
    }
}
