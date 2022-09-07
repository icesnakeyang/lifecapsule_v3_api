package cc.cdtime.lifecapsule_v3_api.meta.creativeNote.service;

import cc.cdtime.lifecapsule_v3_api.meta.creativeNote.entity.CreativeNote;

import java.util.ArrayList;
import java.util.Map;

public interface ICreativeNoteService {
    /**
     * 创建一个防拖延笔记
     *
     * @param creativeNote
     */
    void createCreativeNote(CreativeNote creativeNote) throws Exception;

    /**
     * 读取防拖延笔记列表
     *
     * @param qIn noteId
     * @return
     */
    ArrayList<CreativeNote> listCreativeNote(Map qIn) throws Exception;

    /**
     * 物理删除一个防拖延笔记
     *
     * @param noteId
     */
    void deleteCreativeNote(String noteId) throws Exception;
}
