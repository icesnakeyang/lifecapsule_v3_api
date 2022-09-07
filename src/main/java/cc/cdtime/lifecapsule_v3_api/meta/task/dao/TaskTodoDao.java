package cc.cdtime.lifecapsule_v3_api.meta.task.dao;

import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskTodo;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskTodoView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface TaskTodoDao {
    /**
     * 创建一个待办任务
     *
     * @param taskTodo
     */
    void createTaskTodo(TaskTodo taskTodo);

    /**
     * 查询待办任务列表
     *
     * @param qIn userId
     *            complete
     *            size
     *            offset
     * @return
     */
    ArrayList<TaskTodoView> listTaskTodo(Map qIn);

    /**
     * 统计待办任务数量
     *
     * @param qIn userId
     *            complete
     * @return
     */
    Integer totalTaskTodo(Map qIn);

    /**
     * 修改待办任务
     *
     * @param qIn title
     *            complete
     *            priority
     *            taskId
     */
    void updateTaskTodo(Map qIn);

    /**
     * 查询一个代办任务
     *
     * @param taskId
     * @return
     */
    TaskTodoView getTaskTodo(String taskId);

    void deleteTaskTodo(String taskId);
}
