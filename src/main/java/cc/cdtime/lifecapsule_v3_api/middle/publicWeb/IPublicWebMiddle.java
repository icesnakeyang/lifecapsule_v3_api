package cc.cdtime.lifecapsule_v3_api.middle.publicWeb;

import cc.cdtime.lifecapsule_v3_api.meta.notePublic.NotePublic;
import cc.cdtime.lifecapsule_v3_api.meta.notePublic.NotePublicView;

import java.util.ArrayList;
import java.util.Map;

public interface IPublicWebMiddle {
    void createNotePublic(NotePublic notePublic) throws Exception;

    ArrayList<NotePublicView> listNotePublic(Map qIn) throws Exception;

    NotePublicView getNotePublic(String noteId) throws Exception;
}
