package cc.cdtime.lifecapsule_v3_api.meta.task.service;

import cc.cdtime.lifecapsule_v3_api.meta.task.dao.TaskTodoDao;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskTodo;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskTodoView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class TaskTodoService implements ITaskTodoService {
    private final TaskTodoDao taskTodoDao;

    public TaskTodoService(TaskTodoDao taskTodoDao) {
        this.taskTodoDao = taskTodoDao;
    }

    @Override
    public void createTaskTodo(TaskTodo taskTodo) throws Exception {
        taskTodoDao.createTaskTodo(taskTodo);
    }

    @Override
    public ArrayList<TaskTodoView> listTaskTodo(Map qIn) throws Exception {
        ArrayList<TaskTodoView> taskTodoViews = taskTodoDao.listTaskTodo(qIn);
        return taskTodoViews;
    }

    @Override
    public Integer totalTaskTodo(Map qIn) throws Exception {
        Integer total = taskTodoDao.totalTaskTodo(qIn);
        return total;
    }

    @Override
    public void updateTaskTodo(Map qIn) throws Exception {
        taskTodoDao.updateTaskTodo(qIn);
    }

    @Override
    public TaskTodoView getTaskTodo(String taskId) throws Exception {
        TaskTodoView taskTodoView = taskTodoDao.getTaskTodo(taskId);
        return taskTodoView;
    }

    @Override
    public void deleteTaskTodo(String taskId) throws Exception {
        taskTodoDao.deleteTaskTodo(taskId);
    }
}
