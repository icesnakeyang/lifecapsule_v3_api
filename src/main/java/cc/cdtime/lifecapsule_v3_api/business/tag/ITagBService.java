package cc.cdtime.lifecapsule_v3_api.business.tag;

import java.util.Map;

public interface ITagBService {
    void AddTagNote(Map in) throws Exception;

    Map listNoteTag(Map in) throws Exception;

    void removeNoteTag(Map in) throws Exception;

    Map listHotNoteTags(Map in) throws Exception;

    /**
     * 读取一个用户的所有已有的笔记标签列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listUserNoteTag(Map in) throws Exception;
}
