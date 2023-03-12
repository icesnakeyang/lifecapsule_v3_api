package cc.cdtime.lifecapsule_v3_api.app.project;

import java.util.Map;

public interface IAppProjectBService {
    Map listMyProject(Map in) throws Exception;

    Map getProject(Map in) throws Exception;

    void saveMyProject(Map in) throws Exception;
}
