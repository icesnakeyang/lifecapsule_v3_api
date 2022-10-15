package cc.cdtime.lifecapsule_v3_api.app.tag;

import java.util.Map;

public interface IAppTagBService {
    /**
     * App端用户添加一个笔记标签
     *
     * @param in
     * @throws Exception
     */
    void AddTagNote(Map in) throws Exception;

    /**
     * App端用户读取一个笔记的所有标签
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listNoteTag(Map in) throws Exception;

    /**
     * App端用户删除一个笔记标签
     *
     * @param in
     * @throws Exception
     */
    void removeNoteTag(Map in) throws Exception;

    /**
     * 读取用户的所有笔记标签列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listUserNoteTag(Map in) throws Exception;

    /**
     * App查询最热的10条笔记标签
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listHotNoteTags(Map in) throws Exception;
}
