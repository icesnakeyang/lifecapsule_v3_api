package cc.cdtime.lifecapsule_v3_api.meta.task.service;

import cc.cdtime.lifecapsule_v3_api.meta.task.dao.TaskTodoDao;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskTodo;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskView;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void createTaskTodo(TaskTodo taskTodo) {
        taskTodoDao.createTaskTodo(taskTodo);
    }

    @Override
    public ArrayList<TaskView> listTaskTodo(Map qIn) {
        ArrayList<TaskView> taskViews = taskTodoDao.listTaskTodo(qIn);
        return taskViews;
    }

    @Override
    public Integer totalTaskTodo(Map qIn) {
        Integer total = taskTodoDao.totalTaskTodo(qIn);
        return total;
    }

    @Override
    public TaskView getTaskTodo(String taskId) {
        TaskView taskView = taskTodoDao.getTaskTodo(taskId);
        return taskView;
    }

    @Override
    public void updateTaskTodo(Map qIn) {
        taskTodoDao.updateTaskTodo(qIn);
    }

    @Override
    public void deleteTaskTodo(Map qIn) throws Exception{
        taskTodoDao.deleteTaskTodo(qIn);
    }
}
