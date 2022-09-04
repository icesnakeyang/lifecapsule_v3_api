package cc.cdtime.lifecapsule_v3_api.meta.note.service;

import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import java.util.ArrayList;
import java.util.Map;

public interface INoteService {
    /**
     * 创建一个笔记信息
     *
     * @param noteInfo
     */
    void createNoteInfo(NoteInfo noteInfo) throws Exception;

    /**
     * 查询一条笔记信息
     *
     * @param noteId
     * @return
     */
    NoteView getNoteInfo(String noteId) throws Exception;

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
    ArrayList<NoteView> listNoteInfo(Map qIn) throws Exception;

    /**
     * 统计笔记数量
     *
     * @param qIn userId
     *            categoryId
     *            keyword
     * @return
     */
    Integer totalNoteInfo(Map qIn) throws Exception;

    /**
     * 修改笔记
     *
     * @param qIn title
     *            encrypt
     *            userEncodeKey
     *            categoryId
     *            content
     */
    void updateNoteInfo(Map qIn) throws Exception;

    /**
     * 物理删除笔记
     *
     * @param noteId
     */
    void deleteNoteInfo(String noteId) throws Exception;

    /**
     * 查询创建了触发器的笔记列表
     *
     * @param userId
     * @return
     */
    ArrayList<NoteView> listNoteTrigger(String userId) throws Exception;
}
