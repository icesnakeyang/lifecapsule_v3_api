package cc.cdtime.lifecapsule_v3_api.app.future;

import java.util.Map;

public interface IAppFutureBService {
    Map listFuture(Map in) throws Exception;

    Map getFuture(Map in) throws Exception;

    void saveFuture(Map in) throws Exception;

    void deleteFuture(Map in) throws Exception;

    Map listFutureTrigger(Map in) throws Exception;
}
