package cc.cdtime.lifecapsule_v3_api.business.creativeNote;

import java.util.Map;

public interface ICreativeNoteBService {
    Map listCreativeNote(Map in) throws Exception;

    Map getCreativeNote(Map in) throws Exception;

    Map saveCreativeNote(Map in) throws Exception;

    void deleteCreativeNote(Map in) throws Exception;
}
