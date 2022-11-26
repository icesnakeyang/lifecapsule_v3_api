package cc.cdtime.lifecapsule_v3_api.business.taskTodo;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskTodo;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.security.ISecurityMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.task.ITaskTodoMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskTodoBService implements ITaskTodoBService {
    private final IUserMiddle iUserMiddle;
    private final ITaskTodoMiddle iTaskTodoMiddle;
    private final ISecurityMiddle iSecurityMiddle;

    public TaskTodoBService(
            IUserMiddle iUserMiddle, ITaskTodoMiddle iTaskTodoMiddle,
            ISecurityMiddle iSecurityMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iTaskTodoMiddle = iTaskTodoMiddle;
        this.iSecurityMiddle = iSecurityMiddle;
    }

    @Override
    public Map listTaskTodo(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");
        Boolean hideComplete = (Boolean) in.get("hideComplete");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        if (hideComplete != null) {
            if (hideComplete) {
                qIn.put("complete", false);
            }
        }
        ArrayList<TaskView> taskViews = iTaskTodoMiddle.listTaskTodo(qIn);
        Integer totalTaskTodo = iTaskTodoMiddle.totalTaskTodo(qIn);

        Map out = new HashMap();
        out.put("taskTodoList", taskViews);
        out.put("totalTaskTodo", totalTaskTodo);

        return out;
    }

    @Override
    public Map getTaskTodo(Map in) throws Exception {
        String token = in.get("token").toString();
        String taskId = in.get("taskId").toString();
        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");

        /**
         * 获取用户临时上传的加密笔记AES秘钥的AES秘钥
         */
        String strAESKey = null;
        if (keyToken != null) {
            strAESKey = iSecurityMiddle.takeNoteAES(keyToken, encryptKey);
        }

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        TaskView taskTodoView = iTaskTodoMiddle.getTaskTodo(taskId, false, userView.getUserId());

        String data = taskTodoView.getUserEncodeKey();
        //用AES秘钥加密笔记内容的AES秘钥
        String outCode = GogoTools.encryptAESKey(data, strAESKey);
        taskTodoView.setUserEncodeKey(outCode);

        Map out = new HashMap();
        out.put("taskTodo", taskTodoView);

        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteTaskTodo(Map in) throws Exception {
        String token = in.get("token").toString();
        String taskId = in.get("taskId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        TaskView taskTodoView = iTaskTodoMiddle.getTaskTodo(taskId, false, userView.getUserId());

        iTaskTodoMiddle.deleteTaskTodo(taskId);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createMyTaskTodo(Map in) throws Exception {
        String token = in.get("token").toString();
        String title = in.get("title").toString();
        String content = (String) in.get("content");
        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");

        /**
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String strAESKey = null;
        if (encryptKey != null) {
            String privateKey = iSecurityMiddle.getRSAKey(keyToken);
            strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);
            iSecurityMiddle.deleteRSAKey(keyToken);
        }

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        TaskTodo taskTodo = new TaskTodo();
        taskTodo.setTaskId(GogoTools.UUID32());
        taskTodo.setCreateTime(new Date());
        taskTodo.setTaskTitle(title);
        taskTodo.setUserId(userView.getUserId());
        taskTodo.setContent(content);
        taskTodo.setUserEncodeKey(strAESKey);
        iTaskTodoMiddle.createTaskTodo(taskTodo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateTaskTodoComplete(Map in) throws Exception {
        String token = in.get("token").toString();
        String taskId = in.get("taskId").toString();
        Boolean complete = (Boolean) in.get("complete");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("complete", complete);
        qIn.put("taskId", taskId);
        iTaskTodoMiddle.updateTaskTodo(qIn);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMyTaskTodo(Map in) throws Exception {
        String token = in.get("token").toString();
        String taskId = in.get("taskId").toString();
        String content = (String) in.get("content");
        String title = (String) in.get("title");
        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");
        Boolean complete = (Boolean) in.get("complete");

        /**
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String strAESKey = null;
        if (encryptKey != null) {
            String privateKey = iSecurityMiddle.getRSAKey(keyToken);
            strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);
            iSecurityMiddle.deleteRSAKey(keyToken);
        }


        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        TaskView taskView = iTaskTodoMiddle.getTaskTodo(taskId, false, userView.getUserId());

        qIn = new HashMap();
        qIn.put("title", title);
        qIn.put("content", content);
        qIn.put("userEncodeKey", strAESKey);
        qIn.put("taskId", taskId);
        qIn.put("complete", complete);
        iTaskTodoMiddle.updateTaskTodo(qIn);
    }
}
