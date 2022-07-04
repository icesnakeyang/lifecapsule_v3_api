package cc.cdtime.lifecapsule_v3_api.business.note;

import java.util.Map;

public interface INoteBService {
    Map listNote(Map in) throws Exception;

    Map getMyNote(Map in) throws Exception;

    void saveNote(Map in) throws Exception;

    /**
     * 统计笔记数量
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map totalNote(Map in) throws Exception;

    void deleteNote(Map in) throws Exception;
}
