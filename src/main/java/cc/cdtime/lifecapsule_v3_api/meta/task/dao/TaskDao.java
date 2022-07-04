package cc.cdtime.lifecapsule_v3_api.meta.task.dao;

import cc.cdtime.lifecapsule_v3_api.meta.task.entity.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface TaskDao {
    /**
     * 创建一个新任务
     *
     * @param task
     */
    void createTask(Task task);

    void createTaskContent(Map qIn);

    /**
     * 读取任务列表
     *
     * @param qIn userId
     *            noteId
     *            taskType
     *            status
     *            odc （按 ids desc 排序）
     *            opdc（按priority desc排序）
     *            offset
     *            size
     * @return
     */
    ArrayList<Task> listTask(Map qIn);

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
    Task getTask(Map qIn);

    /**
     * 读取一个任务的详情
     *
     * @param taskId
     * @return
     */
    Task getTaskDetail(String taskId);

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
    void updateTask(Map qIn);

    void updateTaskContent(Map qIn);

    /**
     * 删除任务
     *
     * @param qIn noteId
     *            taskId
     */
    void deleteTask(Map qIn);

    /**
     * 删除任务详情
     *
     * @param taskId
     */
    void deleteTaskContent(String taskId);
}
