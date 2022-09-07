package cc.cdtime.lifecapsule_v3_api.middle.task;

import cc.cdtime.lifecapsule_v3_api.meta.content.entity.Content;
import cc.cdtime.lifecapsule_v3_api.meta.content.service.IContentService;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskTodo;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskTodoView;
import cc.cdtime.lifecapsule_v3_api.meta.task.service.ITaskTodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskTodoMiddle implements ITaskTodoMiddle {
    private final ITaskTodoService iTaskTodoService;
    private final IContentService iContentService;

    public TaskTodoMiddle(ITaskTodoService iTaskTodoService, IContentService iContentService) {
        this.iTaskTodoService = iTaskTodoService;
        this.iContentService = iContentService;
    }

    @Override
    public void createTaskTodo(TaskTodo taskTodo) throws Exception {
        iTaskTodoService.createTaskTodo(taskTodo);
        if (taskTodo.getContent() != null) {
            Content content = new Content();
            content.setContent(taskTodo.getContent());
            content.setIndexId(taskTodo.getTaskId());
            iContentService.createContent(content);
        }
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
        int cc = 0;
        String taskId = qIn.get("taskId").toString();
        if (qIn.get("title") != null) {
            cc++;
        }
        if (qIn.get("complete") != null) {
            cc++;
        }
        if (qIn.get("priority") != null) {
            cc++;
        }
        if (cc > 0) {
            iTaskTodoService.updateTaskTodo(qIn);
        }
        Content contentDB = iContentService.getContent(taskId);
        String content = (String) qIn.get("content");
        if (content!= null) {
            /**
             * 提交内容不为空，则判断数据库为空，修改，不为空，比较是否一致，不一致修改
             */
            if (contentDB == null) {
                Content content1 = new Content();
                content1.setIndexId(taskId);
                content1.setContent(content);
                iContentService.createContent(content1);
            } else {
                if (!content.equals(contentDB.getContent())) {
                    qIn = new HashMap();
                    qIn.put("indexId", taskId);
                    qIn.put("content", content);
                    iContentService.updateContent(qIn);
                }
            }
        }
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
        Content content = iContentService.getContent(taskId);
        if (content != null) {
            taskTodoView.setContent(content.getContent());
        }
        return taskTodoView;
    }

    @Override
    public void deleteTaskTodo(String taskId) throws Exception {
        iTaskTodoService.deleteTaskTodo(taskId);
        iContentService.deleteContent(taskId);
    }
}
