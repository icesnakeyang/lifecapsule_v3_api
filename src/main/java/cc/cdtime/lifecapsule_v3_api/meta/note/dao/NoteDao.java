package cc.cdtime.lifecapsule_v3_api.meta.note.dao;

import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface NoteDao {
    /**
     * 创建一个笔记信息
     *
     * @param noteInfo
     */
    void createNoteInfo(NoteInfo noteInfo);

    /**
     * 创建笔记的内容详情记录
     *
     * @param noteInfo
     */
    void createNoteDetail(NoteInfo noteInfo);

    /**
     * 查询一条笔记简要信息
     *
     * @param noteId
     * @return
     */
    NoteView getNoteTiny(String noteId);

    /**
     * 查询一条笔记详细信息
     *
     * @param noteId
     * @return
     */
    NoteView getNoteDetail(String noteId);

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
    ArrayList<NoteView> listNote(Map qIn);

    /**
     * 统计笔记数量
     *
     * @param qIn userId
     *            categoryId
     *            keyword
     * @return
     */
    Integer totalNote(Map qIn);

    /**
     * 修改笔记
     *
     * @param qIn title
     *            encrypt
     *            userEncodeKey
     *            categoryId
     *            noteId
     */
    void updateNoteInfo(Map qIn);

    /**
     * 修改笔记详情
     *
     * @param qIn content
     *            noteId
     */
    void updateNoteDetail(Map qIn);

    /**
     * 物理删除笔记
     *
     * @param noteId
     */
    void deleteNote(String noteId);

    void deleteNoteDetail(String noteId);

    /**
     * 查询创建了触发器的笔记列表
     *
     * @param userId
     * @return
     */
    ArrayList<NoteView> listNoteTrigger(String userId);
}
