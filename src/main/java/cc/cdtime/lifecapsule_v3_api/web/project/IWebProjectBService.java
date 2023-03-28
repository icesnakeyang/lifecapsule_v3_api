package cc.cdtime.lifecapsule_v3_api.web.project;

import java.util.Map;

public interface IWebProjectBService {
    void saveMyProject(Map in) throws Exception;

    Map listMyProject(Map in) throws Exception;

    Map getProject(Map in) throws Exception;
}
