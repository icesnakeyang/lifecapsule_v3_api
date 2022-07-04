package cc.cdtime.lifecapsule_v3_api.middle.task;

import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskTodo;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskTodoView;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;

import java.util.ArrayList;
import java.util.Map;

public interface ITaskTodoMiddle {
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
    TaskTodoView getTaskTodo(String taskId, Boolean returnNull, String userId) throws Exception;

    void deleteTaskTodo(String taskId) throws Exception;
}
