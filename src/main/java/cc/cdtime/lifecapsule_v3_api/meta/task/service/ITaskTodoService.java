package cc.cdtime.lifecapsule_v3_api.meta.task.service;

import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskTodo;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskTodoView;

import java.util.ArrayList;
import java.util.Map;

public interface ITaskTodoService {
    /**
     * 创建一个待办任务
     *
     * @param taskTodo
     */
    void createTaskTodo(TaskTodo taskTodo) throws Exception;

    /**
     * 查询待办任务列表
     *
     * @param qIn userId
     *            size
     *            offset
     * @return
     */
    ArrayList<TaskTodoView> listTaskTodo(Map qIn) throws Exception;

    /**
     * 统计待办任务数量
     *
     * @param qIn userId
     *            complete
     * @return
     */
    Integer totalTaskTodo(Map qIn) throws Exception;

    /**
     * 修改待办任务
     *
     * @param qIn title
     *            complete
     *            priority
     *            taskId
     */
    void updateTaskTodo(Map qIn) throws Exception;

    /**
     * 查询一个代办任务
     *
     * @param taskId
     * @return
     */
    TaskTodoView getTaskTodo(String taskId) throws Exception;

    void deleteTaskTodo(String taskId) throws Exception;
}
