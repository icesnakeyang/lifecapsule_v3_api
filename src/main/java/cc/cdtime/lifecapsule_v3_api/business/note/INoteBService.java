package cc.cdtime.lifecapsule_v3_api.business.note;

import java.util.Map;

public interface INoteBService {
    Map listNote(Map in) throws Exception;

    Map getMyNote(Map in) throws Exception;

    Map saveNote(Map in) throws Exception;

    /**
     * 统计笔记数量
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map totalNote(Map in) throws Exception;

    void deleteNote(Map in) throws Exception;

    /**
     * 查询笔记的简要信息，不包含笔记内容
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getNoteTiny(Map in) throws Exception;

    void replyNote(Map in) throws Exception;
}
