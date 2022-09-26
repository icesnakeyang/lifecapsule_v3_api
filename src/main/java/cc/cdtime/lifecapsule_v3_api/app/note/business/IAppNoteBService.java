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
    Map saveMyNote(Map in) throws Exception;

    /**
     * App端用户删除笔记
     *
     * @param in
     * @throws Exception
     */
    void deleteMyNote(Map in) throws Exception;

    /**
     * 用户查看笔记的触发器
     *
     * @param in
     * @return
     */
    Map listNoteTrigger(Map in) throws Exception;

    /**
     * 用户创建一个笔记的接收人
     *
     * @param in
     */
    void createNoteRecipient(Map in) throws Exception;

    /**
     * App用户统计自己的笔记数量
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map totalMyNote(Map in) throws Exception;

    /**
     * App用户查询自己的笔记的简要信息，不包含笔记内容
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getMyNoteTiny(Map in) throws Exception;
}
