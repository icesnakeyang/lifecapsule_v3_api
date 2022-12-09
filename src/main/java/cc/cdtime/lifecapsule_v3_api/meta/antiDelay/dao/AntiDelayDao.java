package cc.cdtime.lifecapsule_v3_api.meta.antiDelay.dao;

import cc.cdtime.lifecapsule_v3_api.meta.antiDelay.entity.AntiDelayNote;
import cc.cdtime.lifecapsule_v3_api.meta.antiDelay.entity.AntiDelayView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface AntiDelayDao {
    /**
     * 创建一条方拖延笔记内容
     *
     * @param antiDelayNote
     */
    void createAntiDelayNote(AntiDelayNote antiDelayNote);

    /**
     * 读取防拖延笔记的子项内容列表
     *
     * @param qIn userId
     *            noteId
     * @return
     */
    ArrayList<AntiDelayView> listAntiDelayNote(Map qIn);

    AntiDelayView getAntiDelayNote(String antiDelayId);

    /**
     * 物理删除防拖延笔记
     * 按照noteId，删除所有的子内容
     *
     * @param noteId
     */
    void deleteAntiDelayNote(String noteId);

    /**
     * 读取一个方拖延笔记的详细内容
     *
     * @param qIn userId
     *            antiDelayType
     * @return
     */
    AntiDelayView loadAntiDelayNote(Map qIn);

}
