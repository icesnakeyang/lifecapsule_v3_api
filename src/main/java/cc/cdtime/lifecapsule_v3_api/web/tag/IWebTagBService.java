package cc.cdtime.lifecapsule_v3_api.web.tag;

import java.util.Map;

public interface IWebTagBService {
    Map listUserNoteTag(Map in) throws Exception;

    Map listHotNoteTags(Map in) throws Exception;
}
