package cc.cdtime.lifecapsule_v3_api.business.tag;

import java.util.Map;

public interface ITagBService {
    void AddTagNote(Map in) throws Exception;

    Map listNoteTag(Map in) throws Exception;

    void removeNoteTag(Map in) throws Exception;
}
