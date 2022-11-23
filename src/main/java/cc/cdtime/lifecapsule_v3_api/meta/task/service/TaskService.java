package cc.cdtime.lifecapsule_v3_api.meta.task.service;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.meta.task.dao.TaskDao;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskService implements ITaskService {
    private final TaskDao taskDao;

    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public void createTaskQuad(Task task) throws Exception {
        /**
         * 确定important和importantLevel
         */
        if (task.getImportant() == null) {
            /**
             * 如果没有指定重要度，就默认重要且紧急
             */
            task.setImportant(ESTags.IMPORTANT_AND_URGENT.toString());
            task.setImportantLevel(0);
        } else {
            if (task.getImportant().equals(ESTags.IMPORTANT_AND_URGENT.toString())) {
                task.setImportantLevel(0);
            } else {
                if (task.getImportant().equals(ESTags.IMPORTANT_NOT_URGENT.toString())) {
                    task.setImportantLevel(1);
                } else {
                    if (task.getImportant().equals(ESTags.URGENT_NOT_IMPORTANT.toString())) {
                        task.setImportantLevel(2);
                    } else {
                        if (task.getImportant().equals(ESTags.NOTHING)) {
                            task.setImportantLevel(3);
                        }
                    }
                }
            }
        }
        taskDao.createTask(task);
        if (task.getContent() != null) {
            Map qIn = new HashMap();
            qIn.put("indexId", task.getTaskId());
            qIn.put("content", task.getContent());
            taskDao.createTaskContent(qIn);
        }
    }

    @Override
    public ArrayList<Task> listTask(Map qIn) throws Exception {
        ArrayList<Task> tasks = taskDao.listTask(qIn);
        return tasks;
    }

    @Override
    public Integer totalTask(Map qIn) {
        Integer total = taskDao.totalTask(qIn);
        return total;
    }

    @Override
    public Task getTask(Map qIn) throws Exception {
        Task task = taskDao.getTask(qIn);
        if (task != null) {
            Task task1 = taskDao.getTaskDetail(task.getTaskId());
            if (task1 != null) {
                task.setContent(task1.getContent());
            }
        }
        return task;
    }

    /**
     * 修改任务
     *
     * @param qIn status
     *            endTime
     *            taskId
     *            taskTitle
     */
    @Override
    public void updateTask(Map qIn) throws Exception {
        String important = (String) qIn.get("important");
        if (important != null) {
            /**
             * 确定important和importantLevel
             */
            if (important.equals(ESTags.IMPORTANT_AND_URGENT.toString())) {
                qIn.put("importantLevel", 0);
            } else {
                if (important.equals(ESTags.IMPORTANT_NOT_URGENT.toString())) {
                    qIn.put("importantLevel", 1);
                } else {
                    if (important.equals(ESTags.URGENT_NOT_IMPORTANT.toString())) {
                        qIn.put("importantLevel", 2);
                    } else {
                        if (important.equals(ESTags.NOTHING.toString())) {
                            qIn.put("importantLevel", 3);
                        }
                    }
                }
            }
        }

        taskDao.updateTask(qIn);

        String content = (String) qIn.get("content");
        if (content != null) {
            taskDao.updateTaskContent(qIn);
        }
    }

    /**
     * 删除任务
     *
     * @param qIn noteId
     *            taskId
     */
    @Override
    public void deleteTask(Map qIn) throws Exception {
        /**
         * 指定taskId，就是删除一个task
         * 指定noteId，就是删除一个笔记的所有10秒行动任务
         * 如果都没有指定，就报错，否则会删除掉所有数据
         */
        String taskId = (String) qIn.get("taskId");
        String noteId = (String) qIn.get("noteId");

        if (taskId == null && noteId == null) {
            throw new Exception("10034");
        }
        taskDao.deleteTask(qIn);
    }

    @Override
    public void deleteTaskContent(String taskId) throws Exception {
        taskDao.deleteTaskContent(taskId);
    }

    @Override
    public void createTask(Task task) throws Exception {
        taskDao.createTask(task);
    }
}
