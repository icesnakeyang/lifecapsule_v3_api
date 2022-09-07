package cc.cdtime.lifecapsule_v3_api.meta.creativeNote.dao;

import cc.cdtime.lifecapsule_v3_api.meta.creativeNote.entity.CreativeNote;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface CreativeNoteDao {
    /**
     * 创建一个防拖延笔记
     *
     * @param creativeNote
     */
    void createCreativeNote(CreativeNote creativeNote);

    /**
     * 读取防拖延笔记列表
     *
     * @param qIn noteId
     * @return
     */
    ArrayList<CreativeNote> listCreativeNote(Map qIn);

    /**
     * 物理删除一个防拖延笔记
     *
     * @param noteId
     */
    void deleteCreativeNote(String noteId);
}
