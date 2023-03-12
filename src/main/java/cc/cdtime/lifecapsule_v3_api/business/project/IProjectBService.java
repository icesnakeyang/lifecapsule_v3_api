package cc.cdtime.lifecapsule_v3_api.business.project;

import java.util.Map;

public interface IProjectBService {
    Map listProject(Map in) throws Exception;

    Map getProject(Map in) throws Exception;

    void saveProject(Map in) throws Exception;
}
