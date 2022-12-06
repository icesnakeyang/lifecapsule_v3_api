package cc.cdtime.lifecapsule_v3_api.middle.antiDelay;

import cc.cdtime.lifecapsule_v3_api.meta.antiDelay.entity.AntiDelayNote;
import cc.cdtime.lifecapsule_v3_api.meta.antiDelay.entity.AntiDelayView;

import java.util.ArrayList;
import java.util.Map;

public interface IAntiDelayMiddle {
    /**
     * 创建一条方拖延笔记内容
     *
     * @param antiDelayNote
     */
    void createAntiDelayNote(AntiDelayNote antiDelayNote) throws Exception;

    /**
     * 读取防拖延笔记的子项内容列表
     *
     * @param qIn userId
     *            noteId
     * @return
     */
    ArrayList<AntiDelayView> listAntiDelayNote(Map qIn) throws Exception;

    void updateAntiDelayNote(AntiDelayNote antiDelayNote) throws Exception;

    AntiDelayView getAntiDelayNote(String antiDelayId)throws Exception;

    /**
     * 物理删除一个防拖延笔记
     *
     * @param noteId
     */
    void deleteAntiDelayNote(String noteId) throws Exception;
}
