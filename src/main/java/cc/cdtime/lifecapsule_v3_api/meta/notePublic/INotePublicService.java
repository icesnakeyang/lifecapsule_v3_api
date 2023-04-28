package cc.cdtime.lifecapsule_v3_api.meta.notePublic;

import java.util.ArrayList;
import java.util.Map;

public interface INotePublicService {
    void createNotePublic(NotePublic notePublic) throws Exception;

    ArrayList<NotePublicView> listNotePublic(Map qIn) throws Exception;

    NotePublicView getNotePublic(String noteId) throws Exception;

    void updateNotePublic(Map qIn) throws Exception;
}
