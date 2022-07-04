package cc.cdtime.lifecapsule_v3_api.middle.task;

import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskTodo;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskTodoView;
import cc.cdtime.lifecapsule_v3_api.meta.task.service.ITaskTodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class TaskTodoMiddle implements ITaskTodoMiddle {
    private final ITaskTodoService iTaskTodoService;

    public TaskTodoMiddle(ITaskTodoService iTaskTodoService) {
        this.iTaskTodoService = iTaskTodoService;
    }

    @Override
    public void createTaskTodo(TaskTodo taskTodo) throws Exception {
        iTaskTodoService.createTaskTodo(taskTodo);
    }

    @Override
    public ArrayList<TaskTodoView> listTaskTodo(Map qIn) throws Exception {
        ArrayList<TaskTodoView> taskTodoViews = iTaskTodoService.listTaskTodo(qIn);
        return taskTodoViews;
    }

    @Override
    public Integer totalTaskTodo(Map qIn) throws Exception {
        Integer total = iTaskTodoService.totalTaskTodo(qIn);
        return total;
    }

    @Override
    public void updateTaskTodo(Map qIn) throws Exception {
        iTaskTodoService.updateTaskTodo(qIn);
    }

    @Override
    public TaskTodoView getTaskTodo(String taskId, Boolean returnNull, String userId) throws Exception {
        TaskTodoView taskTodoView = iTaskTodoService.getTaskTodo(taskId);
        if (taskTodoView == null) {
            if (returnNull) {
                //没有查询到待办任务
                throw new Exception("10023");
            }
        }
        if (userId != null) {
            if (!taskTodoView.getUserId().equals(userId)) {
                //不能查询不是自己的待办任务
                throw new Exception("10024");
            }
        }
        return taskTodoView;
    }

    @Override
    public void deleteTaskTodo(String taskId) throws Exception {
        iTaskTodoService.deleteTaskTodo(taskId);
    }
}
