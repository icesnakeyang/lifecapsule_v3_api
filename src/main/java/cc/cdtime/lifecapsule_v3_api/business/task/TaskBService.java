package cc.cdtime.lifecapsule_v3_api.business.task;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.Task;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.task.ITaskMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskBService implements ITaskBService{
    private final IUserMiddle iUserMiddle;
    private final ITaskMiddle iTaskMiddle;

    public TaskBService(IUserMiddle iUserMiddle, ITaskMiddle iTaskMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iTaskMiddle = iTaskMiddle;
    }

    @Override
    public void createTaskTodo(Map in) throws Exception {
        String token=in.get("token").toString();
        String taskTitle=in.get("taskTitle").toString();
        String taskContent=(String) in.get("taskContent");

        Map qIn=new HashMap();
        qIn.put("token", token);
        UserView userView=iUserMiddle.getUser(qIn, false,true);

        Task task=new Task();
        task.setTaskId(GogoTools.UUID32());
        task.setUserId(userView.getUserId());
        task.setTaskTitle(taskTitle);
        task.setCreateTime(new Date());
        task.setTaskType(ESTags.TASK_TODO.toString());
        task.setContent(taskContent);
        iTaskMiddle.createTask(task);
    }
}
