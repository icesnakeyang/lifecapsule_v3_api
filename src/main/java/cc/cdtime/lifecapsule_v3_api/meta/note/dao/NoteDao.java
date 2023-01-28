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
     * 查询一条笔记简要信息
     *
     * @param noteId
     * @return
     */
    NoteView getNoteInfo(String noteId);

    /**
     * 查询笔记列表
     *
     * @param qIn userId
     *            keyword
     *            tagName
     *            noteType
     *            offset
     *            size
     * @return
     */
    ArrayList<NoteView> listNoteInfo(Map qIn);

    /**
     * 统计笔记数量
     *
     * @param qIn userId
     *            keyword
     *            noteType
     * @return
     */
    Integer totalNoteInfo(Map qIn);

    /**
     * 修改笔记
     *
     * @param qIn title
     *            encrypt
     *            userEncodeKey
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
    void deleteNoteInfo(String noteId);

    void deleteNoteDetail(String noteId);

    /**
     * 查询创建了触发器的笔记列表
     *
     * @param userId
     * @return
     */
    ArrayList<NoteView> listNoteTrigger(String userId);

    /**
     * 查询历史笔记
     *
     * @param qIn createTimeStart
     *            createTimeEnd
     *            userId
     *            titleKey
     * @return
     */
    ArrayList<NoteView> listHistoryNote(Map qIn);

    /**
     * 随机查询笔记
     *
     * @param qIn size
     *            userId
     * @return
     */
    ArrayList<NoteView> listHistoryRandom(Map qIn);
}
