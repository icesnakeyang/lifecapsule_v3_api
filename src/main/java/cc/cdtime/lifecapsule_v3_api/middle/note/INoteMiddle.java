package cc.cdtime.lifecapsule_v3_api.middle.note;

import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;

import java.util.ArrayList;
import java.util.Map;

public interface INoteMiddle {
    /**
     * 创建一个笔记信息
     *
     * @param noteInfo
     */
    void createNoteInfo(NoteInfo noteInfo) throws Exception;

    /**
     * 查询一条笔记详情
     *
     * @param noteId
     * @return
     */
    NoteView getNoteDetail(String noteId, Boolean returnNull, String userId) throws Exception;

    NoteView getNoteTiny(String noteId, Boolean returnNull, String userId) throws Exception;

    /**
     * 查询笔记列表
     *
     * @param qIn userId
     *            categoryId
     *            keyword
     *            offset
     *            size
     * @return
     */
    ArrayList<NoteView> listNote(Map qIn) throws Exception;

    /**
     * 统计笔记数量
     *
     * @param qIn userId
     *            categoryId
     *            keyword
     * @return
     */
    Integer totalNote(Map qIn) throws Exception;

    /**
     * 修改笔记
     *
     * @param qIn title
     *            encrypt
     *            userEncodeKey
     *            categoryId
     *            content
     *            noteId
     */
    void updateNoteInfo(Map qIn) throws Exception;

    /**
     * 物理删除笔记
     *
     * @param noteId
     */
    void deleteNote(String noteId) throws Exception;

    /**
     * 查询创建了触发器的笔记列表
     *
     * @param userId
     * @return
     */
    ArrayList<NoteView> listNoteTrigger(String userId) throws Exception;

    /**
     * 查询历史笔记
     *
     * @param qIn createTimeStart
     *            createTimeEnd
     *            userId
     * @return
     */
    ArrayList<NoteView> listHistoryNote(Map qIn) throws Exception;

    /**
     * 随机查询笔记
     *
     * @param qIn size
     * @return
     */
    ArrayList<NoteView> listHistoryRandom(Map qIn) throws Exception;
}
