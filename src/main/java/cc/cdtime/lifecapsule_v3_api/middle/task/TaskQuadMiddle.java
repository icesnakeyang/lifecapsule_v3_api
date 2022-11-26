package cc.cdtime.lifecapsule_v3_api.middle.task;

import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskQuad;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class TaskQuadMiddle implements ITaskQuadMiddle{
    @Override
    public ArrayList<TaskView> listTaskQuad(Map qIn) throws Exception {
        return null;
    }

    @Override
    public Integer totalTaskQuad(Map qIn) throws Exception {
        return null;
    }

    @Override
    public void createTaskQuad(TaskQuad taskQuad) throws Exception {

    }

    @Override
    public TaskQuad getTaskQuad(Map qIn, boolean b, String userId) throws Exception {
        return null;
    }

    @Override
    public void updateTaskQuad(Map qIn) throws Exception {

    }

    @Override
    public void deleteTaskQuad(Map qIn) throws Exception {

    }
}
