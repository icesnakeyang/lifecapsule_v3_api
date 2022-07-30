package cc.cdtime.lifecapsule_v3_api.web.creativeNote;

import java.util.Map;

public interface IWebCreativeNoteBService {
    Map listMyCreativeNote(Map in) throws Exception;

    Map getMyCreativeNote(Map in) throws Exception;

    Map saveCreativeNote(Map in) throws Exception;

    /**
     * 物理删除防拖延日记
     *
     * @param in
     * @throws Exception
     */
    void deleteCreativeNote(Map in) throws Exception;
}
