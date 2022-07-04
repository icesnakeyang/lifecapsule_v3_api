package cc.cdtime.lifecapsule_v3_api.app.note.business;

import java.util.Map;

public interface IAppNoteBService {
    /**
     * 用户查询自己的笔记列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listMyNote(Map in) throws Exception;

    /**
     * App用户查询自己的笔记详情
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getMyNote(Map in) throws Exception;

    /**
     * App端用户保存笔记
     *
     * @param in
     * @throws Exception
     */
    void saveMyNote(Map in) throws Exception;

    /**
     * App端用户删除笔记
     *
     * @param in
     * @throws Exception
     */
    void deleteMyNote(Map in) throws Exception;

    /**
     * 用户查看接收人列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listRecipient(Map in) throws Exception;

    /**
     * 用户查看笔记的触发器
     *
     * @param in
     * @return
     */
    Map listNoteTrigger(Map in) throws Exception;

    /**
     * 用户保存笔记的触发器
     *
     * @param in
     * @return
     */
    Map saveNoteTrigger(Map in) throws Exception;

    /**
     * 用户创建一个笔记的接收人
     *
     * @param in
     */
    void createNoteRecipient(Map in) throws Exception;

    /**
     * 用户删除笔记的接收人
     *
     * @param in
     * @throws Exception
     */
    void deleteNoteRecipient(Map in) throws Exception;

    /**
     * 查询当前用户的所有笔记分类列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listMyCategory(Map in) throws Exception;

    /**
     * 用户创建或者修改自己的笔记分类
     *
     * @param in
     * @throws Exception
     */
    void saveMyCategory(Map in) throws Exception;

    void deleteMyCategory(Map in) throws Exception;

    /**
     * 查询当前用户的一个笔记分类详情
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getMyCategory(Map in) throws Exception;

    /**
     * 获取用户的默认笔记分类详情
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getMyDefaultCategory(Map in) throws Exception;

    /**
     * App用户统计自己的笔记数量
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map totalMyNote(Map in) throws Exception;
}
