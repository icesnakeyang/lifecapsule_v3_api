package cc.cdtime.lifecapsule_v3_api.business.quadTask;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.Task;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskTodoView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.security.ISecurityMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.task.ITaskMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class QuadTaskBService implements IQuadTaskBService {
    private final IUserMiddle iUserMiddle;
    private final ITaskMiddle iTaskMiddle;
    private final ISecurityMiddle iSecurityMiddle;

    public QuadTaskBService(IUserMiddle iUserMiddle,
                            ITaskMiddle iTaskMiddle,
                            ISecurityMiddle iSecurityMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iTaskMiddle = iTaskMiddle;
        this.iSecurityMiddle = iSecurityMiddle;
    }

    @Override
    public Map listQuadTask(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");
        String status = (String) in.get("status");
        Boolean odc = (Boolean) in.get("odc");
        Boolean opdc = (Boolean) in.get("opdc");
        String important = (String) in.get("important");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        qIn.put("status", status);
        if (odc != null) {
            qIn.put("odc", true);
        }
        if (opdc != null) {
            qIn.put("opdc", true);
        }
        qIn.put("opdc", true);
        qIn.put("important", important);
        ArrayList<Task> tasks = iTaskMiddle.listTask(qIn);

        Map out = new HashMap();
        out.put("taskList", tasks);

        Integer total = iTaskMiddle.totalTask(qIn);
        out.put("totalTask", total);

        return out;
    }

    @Override
    public void createQuadTask(Map in) throws Exception {
        String token = in.get("token").toString();
        String taskTitle = in.get("taskTitle").toString();
        String important = (String) in.get("important");
        Date endTime = (Date) in.get("endTime");
        String encryptKey = in.get("encryptKey").toString();
        String keyToken = in.get("keyToken").toString();
        String content = in.get("content").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String strAESKey = iSecurityMiddle.takeNoteAES(keyToken, encryptKey);

        /**
         * 保存task
         */
        Task task = new Task();
        task.setTaskId(GogoTools.UUID32());
        task.setUserId(userView.getUserId());
        task.setTaskTitle(taskTitle);
        task.setPriority(0);
        task.setStatus(ESTags.PROGRESS.toString());
        task.setTaskType(ESTags.TASK_QUAD.toString());
        task.setCreateTime(new Date());
        task.setImportant(important);
        task.setEndTime(endTime);
        task.setUserEncodeKey(strAESKey);
        task.setContent(content);
        iTaskMiddle.createTask(task);
    }

    @Override
    public void updateQuadTask(Map in) throws Exception {
        String token = in.get("token").toString();
        String taskId = in.get("taskId").toString();
        String taskTitle = in.get("taskTitle").toString();
        Integer priority = (Integer) in.get("priority");
        String status = (String) in.get("status");
        String taskType = (String) in.get("taskType");
        String important = (String) in.get("important");
        Boolean complete = (Boolean) in.get("complete");
        Date endTime = (Date) in.get("endTime");
        String encryptKey = in.get("encryptKey").toString();
        String keyToken = in.get("keyToken").toString();
        String content = in.get("content").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * 更新
         */
        //读取任务
        qIn = new HashMap();
        qIn.put("taskId", taskId);
        Task task = iTaskMiddle.getTask(qIn, false, userView.getUserId());

        /**
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String strAESKey = iSecurityMiddle.takeNoteAES(keyToken, encryptKey);

        /**
         * 保存task
         */
        qIn = new HashMap();
        qIn.put("taskId", taskId);
        qIn.put("taskTitle", taskTitle);
        qIn.put("priority", priority);
        qIn.put("status", status);
        qIn.put("taskType", taskType);
        qIn.put("important", important);
        qIn.put("complete", complete);
        qIn.put("endTime", endTime);
        qIn.put("userEncodeKey", strAESKey);
        qIn.put("content", content);
        iTaskMiddle.updateTask(qIn);
    }

    @Override
    public Map getQuadTask(Map in) throws Exception {
        String token = in.get("token").toString();
        String taskId = in.get("taskId").toString();
        String encryptKey = in.get("encryptKey").toString();
        String keyToken = (String) in.get("keyToken");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false,true);

        qIn = new HashMap();
        qIn.put("taskId", taskId);
        Task task = iTaskMiddle.getTask(qIn, false, userView.getUserId());

        /**
         * 获取用户临时上传的加密笔记AES秘钥的AES秘钥
         */
        String strAESKey = iSecurityMiddle.takeNoteAES(keyToken, encryptKey);

        //用AES秘钥加密笔记内容的AES秘钥
        String data = task.getUserEncodeKey();
        if (task.getUserEncodeKey() != null) {
            String outCode = GogoTools.encryptAESKey(data, strAESKey);
            task.setUserEncodeKey(outCode);
        }

        Map out = new HashMap();
        out.put("taskQuad", task);

        return out;
    }

    @Override
    public void setTaskStatus(Map in) throws Exception {
        String token=in.get("token").toString();
        String taskId=in.get("taskId").toString();
        String status=in.get("status").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false,true);

        qIn = new HashMap();
        qIn.put("taskId", taskId);
        Task task = iTaskMiddle.getTask(qIn, false, userView.getUserId());

        if(!status.equals(task.getStatus())){
            if(status.equals(ESTags.COMPLETE.toString())||
            status.equals(ESTags.PROGRESS.toString())){
                qIn.put("status", status);
                iTaskMiddle.updateTask(qIn);
            }
        }
    }

    @Override
    public void deleteTask(Map in) throws Exception {
        String token=in.get("token").toString();
        String taskId=in.get("taskId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false,true);

        qIn = new HashMap();
        qIn.put("taskId", taskId);
        Task task = iTaskMiddle.getTask(qIn, false, userView.getUserId());

        iTaskMiddle.deleteTask(qIn);
    }

    @Override
    public void increaseQuadTaskPriority(Map in) throws Exception {
        String token = in.get("token").toString();
        String taskId = in.get("taskId").toString();

        /**
         * 根据token读取用户
         */
        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false,true);

        /**
         * 根据taskId读取task，并与判断是否为用户创建的task
         */
        qIn = new HashMap();
        qIn.put("taskId", taskId);
        Task task = iTaskMiddle.getTask(qIn, false, userView.getUserId());

        /**
         * 增加优先级
         */
        qIn = new HashMap();
        qIn.put("taskId", task.getTaskId());
        Integer pp = task.getPriority();
        if (pp == null) {
            pp = 0;
        }
        pp++;
        qIn.put("priority", pp);
        iTaskMiddle.updateTask(qIn);
    }

    @Override
    public void decreaseQuadTaskPriority(Map in) throws Exception {
        String token = in.get("token").toString();
        String taskId = in.get("taskId").toString();

        /**
         * 根据token读取用户
         */
        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false,true);

        /**
         * 根据taskId读取task，并与判断是否为用户创建的task
         */
        qIn = new HashMap();
        qIn.put("taskId", taskId);
        Task task = iTaskMiddle.getTask(qIn, false, userView.getUserId());

        /**
         * 减少优先级
         */
        qIn = new HashMap();
        qIn.put("taskId", task.getTaskId());
        Integer pp = task.getPriority();
        if (pp == null) {
            pp = 0;
        }
        pp--;
        qIn.put("priority", pp);
        iTaskMiddle.updateTask(qIn);
    }
}
