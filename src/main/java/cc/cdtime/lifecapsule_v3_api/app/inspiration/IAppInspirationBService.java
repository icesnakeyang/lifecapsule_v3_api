package cc.cdtime.lifecapsule_v3_api.app.inspiration;

import java.util.Map;

public interface IAppInspirationBService {
    Map listInspiration(Map in) throws Exception;

    Map getInspiration(Map in) throws Exception;

    void saveInspiration(Map in) throws Exception;

    void deleteInspiration(Map in) throws Exception;
}
