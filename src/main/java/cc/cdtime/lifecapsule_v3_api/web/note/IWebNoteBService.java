package cc.cdtime.lifecapsule_v3_api.web.note;

import java.util.Map;

public interface IWebNoteBService {
    Map listMyNote(Map in) throws Exception;

    Map getMyNote(Map in) throws Exception;

    Map listMyCategory(Map in) throws Exception;

    void saveMyCategory(Map in) throws Exception;

    void deleteMyCategory(Map in) throws Exception;

    void saveMyNote(Map in) throws Exception;

    /**
     * 用户物理删除一篇笔记
     * @param in
     * @throws Exception
     */
    void deleteMyNote(Map in) throws Exception;
}
