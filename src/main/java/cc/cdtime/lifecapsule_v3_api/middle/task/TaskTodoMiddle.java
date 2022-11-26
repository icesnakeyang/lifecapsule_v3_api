package cc.cdtime.lifecapsule_v3_api.middle.task;

import cc.cdtime.lifecapsule_v3_api.meta.content.entity.Content;
import cc.cdtime.lifecapsule_v3_api.meta.content.service.IContentService;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskTodo;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskView;
import cc.cdtime.lifecapsule_v3_api.meta.task.service.ITaskTodoService;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKey;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKeyView;
import cc.cdtime.lifecapsule_v3_api.meta.user.service.IUserEncodeKeyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskTodoMiddle implements ITaskTodoMiddle {
    private final ITaskTodoService iTaskTodoService;
    private final IContentService iContentService;
    private final IUserEncodeKeyService iUserEncodeKeyService;

    public TaskTodoMiddle(ITaskTodoService iTaskTodoService,
                          IContentService iContentService,
                          IUserEncodeKeyService iUserEncodeKeyService) {
        this.iTaskTodoService = iTaskTodoService;
        this.iContentService = iContentService;
        this.iUserEncodeKeyService = iUserEncodeKeyService;
    }

    @Override
    public void createTaskTodo(TaskTodo taskTodo) throws Exception {
        iTaskTodoService.createTaskTodo(taskTodo);
        if (taskTodo.getContent() != null) {
            Content content = new Content();
            content.setContent(taskTodo.getContent());
            content.setIndexId(taskTodo.getTaskId());
            iContentService.createContent(content);

            UserEncodeKey userEncodeKey = new UserEncodeKey();
            userEncodeKey.setEncodeKey(taskTodo.getUserEncodeKey());
            userEncodeKey.setIndexId(taskTodo.getTaskId());
            iUserEncodeKeyService.createUserEncodeKey(userEncodeKey);
        }
    }

    @Override
    public ArrayList<TaskView> listTaskTodo(Map qIn) throws Exception {
        ArrayList<TaskView> taskViews = iTaskTodoService.listTaskTodo(qIn);
        return taskViews;
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
        if (content != null) {
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
                    Map qIn2 = new HashMap();
                    qIn2.put("indexId", taskId);
                    qIn2.put("content", content);
                    iContentService.updateContent(qIn2);
                }
            }
        }
        String userEncodeKey = (String) qIn.get("userEncodeKey");
        if (userEncodeKey != null) {
            UserEncodeKeyView userEncodeKeyView = iUserEncodeKeyService.getUserEncodeKey(taskId);
            if (userEncodeKeyView == null) {
                UserEncodeKey userEncodeKey1 = new UserEncodeKey();
                userEncodeKey1.setEncodeKey(userEncodeKey);
                userEncodeKey1.setIndexId(taskId);
                iUserEncodeKeyService.createUserEncodeKey(userEncodeKey1);
            } else {
                if (!userEncodeKeyView.getEncodeKey().equals(userEncodeKey)) {
                    Map qIn3 = new HashMap();
                    qIn3.put("encodeKey", userEncodeKey);
                    qIn3.put("indexId", taskId);
                    iUserEncodeKeyService.updateUserEncodeKey(qIn3);
                }
            }
        }
    }

    @Override
    public TaskView getTaskTodo(String taskId, Boolean returnNull, String userId) throws Exception {
        TaskView taskView = iTaskTodoService.getTaskTodo(taskId);
        if (taskView == null) {
            if (returnNull) {
                return null;
            }
            throw new Exception("10031");
        }
        if (userId != null) {
            if (!taskView.getUserId().equals(userId)) {
                //不能查询不属于自己的任务
                throw new Exception("10036");
            }
        }
        Content content = iContentService.getContent(taskId);
        if (content != null) {
            taskView.setContent(content.getContent());
            UserEncodeKeyView userEncodeKeyView = iUserEncodeKeyService.getUserEncodeKey(taskId);
            if (userEncodeKeyView != null) {
                taskView.setUserEncodeKey(userEncodeKeyView.getEncodeKey());
            }
        }
        return taskView;
    }

    @Override
    public void deleteTaskTodo(String taskId) throws Exception {
        iTaskTodoService.deleteTaskTodo(taskId);
        iContentService.deleteContent(taskId);
        iUserEncodeKeyService.deleteUserEncodeKey(taskId);
    }
}
