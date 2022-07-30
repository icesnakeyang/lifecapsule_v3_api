package cc.cdtime.lifecapsule_v3_api.business.quadTask;

import java.util.Map;

public interface IQuadTaskBService {
    Map listQuadTask(Map in) throws Exception;
    void createQuadTask(Map in) throws Exception;
    void updateQuadTask(Map in) throws Exception;
    Map getQuadTask(Map in) throws Exception;
    void setTaskStatus(Map in) throws Exception;
    void deleteTask(Map in) throws Exception;

    void increaseQuadTaskPriority(Map in) throws Exception;

    void decreaseQuadTaskPriority(Map in) throws Exception;
}
