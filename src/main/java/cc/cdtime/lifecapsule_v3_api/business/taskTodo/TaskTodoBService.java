package cc.cdtime.lifecapsule_v3_api.business.taskTodo;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskTodo;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskTodoView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.task.ITaskTodoMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskTodoBService implements ITaskTodoBService {
    private final ITaskTodoMiddle iTaskTodoMiddle;
    private final IUserMiddle iUserMiddle;

    public TaskTodoBService(ITaskTodoMiddle iTaskTodoMiddle,
                            IUserMiddle iUserMiddle) {
        this.iTaskTodoMiddle = iTaskTodoMiddle;
        this.iUserMiddle = iUserMiddle;
    }

    @Override
    public Map listTaskTodo(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        ArrayList<TaskTodoView> taskTodoViews = iTaskTodoMiddle.listTaskTodo(qIn);
        Integer totalTaskTodo = iTaskTodoMiddle.totalTaskTodo(qIn);

        Map out = new HashMap();
        out.put("taskTodoList", taskTodoViews);
        out.put("totalTaskTodo", totalTaskTodo);

        return out;
    }

    @Override
    public void saveTaskTodo(Map in) throws Exception {
        String token = in.get("token").toString();
        String taskId = (String) in.get("taskId");
        Integer priority = (Integer) in.get("priority");
        Boolean complete = (Boolean) in.get("complete");
        String title = (String) in.get("title");
        String content = (String) in.get("content");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        if (taskId == null) {
            //新增
            TaskTodo taskTodo = new TaskTodo();
            taskTodo.setTaskId(GogoTools.UUID32());
            taskTodo.setComplete(false);
            taskTodo.setCreateTime(new Date());
            taskTodo.setPriority(0);
            taskTodo.setTitle(title);
            taskTodo.setUserId(userView.getUserId());
            taskTodo.setContent(content);
            iTaskTodoMiddle.createTaskTodo(taskTodo);
        } else {
            //修改
            TaskTodoView taskTodoView = iTaskTodoMiddle.getTaskTodo(taskId, false, userView.getUserId());
            qIn = new HashMap();
            qIn.put("taskId", taskId);
            qIn.put("priority", priority);
            qIn.put("complete", complete);
            qIn.put("title", title);
            qIn.put("content", content);
            iTaskTodoMiddle.updateTaskTodo(qIn);
        }
    }

    @Override
    public Map getTaskTodo(Map in) throws Exception {
        String token = in.get("token").toString();
        String taskId = in.get("taskId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        TaskTodoView taskTodoView = iTaskTodoMiddle.getTaskTodo(taskId, false, userView.getUserId());

        Map out = new HashMap();
        out.put("taskTodo", taskTodoView);

        return out;
    }

    @Override
    public void deleteTaskTodo(Map in) throws Exception {
        String token = in.get("token").toString();
        String taskId = in.get("taskId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        TaskTodoView taskTodoView = iTaskTodoMiddle.getTaskTodo(taskId, false, userView.getUserId());

        iTaskTodoMiddle.deleteTaskTodo(taskId);

    }
}
