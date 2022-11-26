package cc.cdtime.lifecapsule_v3_api.middle.task;

import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskQuad;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskView;

import java.util.ArrayList;
import java.util.Map;

public interface ITaskQuadMiddle {
    ArrayList<TaskView> listTaskQuad(Map qIn) throws Exception;

    Integer totalTaskQuad(Map qIn) throws Exception;

    void createTaskQuad(TaskQuad taskQuad) throws Exception;

    TaskQuad getTaskQuad(Map qIn, boolean b, String userId) throws Exception;

    void updateTaskQuad(Map qIn) throws Exception;

    void deleteTaskQuad(Map qIn) throws Exception;
}
