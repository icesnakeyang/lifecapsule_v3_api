package cc.cdtime.lifecapsule_v3_api.meta.task.service;


import cc.cdtime.lifecapsule_v3_api.meta.task.entity.Task;

import java.util.ArrayList;
import java.util.Map;

public interface ITaskService {
    /**
     * 创建一个新任务
     *
     * @param task
     */
    void createTask(Task task) throws Exception;

    /**
     * 读取任务列表
     *
     * @param qIn userId
     *            noteId
     *            taskType
     *            status
     *            odc（按ids desc排序）
     *            opdc（按priority desc排序）
     *            offset
     *            size
     * @return
     */
    ArrayList<Task> listTask(Map qIn) throws Exception;

    /**
     * 统计任务总数
     *
     * @param qIn
     * @return
     */
    Integer totalTask(Map qIn);

    /**
     * 读取任务详情
     *
     * @param qIn taskId
     * @return
     */
    Task getTask(Map qIn) throws Exception;

    /**
     * 修改任务
     *
     * @param qIn taskId
     *            taskTitle
     *            priority
     *            status
     *            taskType
     *            important
     *            complete
     *            endTime
     *            userEncodeKey
     */
    void updateTask(Map qIn) throws Exception;

    /**
     * 删除任务
     *
     * @param qIn noteId
     *            taskId
     */
    void deleteTask(Map qIn) throws Exception;

    /**
     * 删除任务详情
     *
     * @param taskId
     */
    void deleteTaskContent(String taskId) throws Exception;
}
