package cc.cdtime.lifecapsule_v3_api.middle.task;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.content.entity.Content;
import cc.cdtime.lifecapsule_v3_api.meta.content.service.IContentService;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.Task;
import cc.cdtime.lifecapsule_v3_api.meta.task.service.ITaskService;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKey;
import cc.cdtime.lifecapsule_v3_api.meta.user.service.IUserEncodeKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskMiddle implements ITaskMiddle {
    private final ITaskService iTaskService;
    private final IContentService iContentService;
    private final IUserEncodeKeyService iUserEncodeKeyService;

    public TaskMiddle(ITaskService iTaskService,
                      IContentService iContentService,
                      IUserEncodeKeyService iUserEncodeKeyService) {
        this.iTaskService = iTaskService;
        this.iContentService = iContentService;
        this.iUserEncodeKeyService = iUserEncodeKeyService;
    }

    @Override
    public void createTask(Task task) throws Exception {
        iTaskService.createTaskQuad(task);
    }

    @Override
    public void createTaskQuad(Task task) throws Exception {
        iTaskService.createTask(task);
        if (task.getContent() != null) {
            Content content = new Content();
            content.setContent(task.getContent());
            content.setIndexId(task.getTaskId());
            iContentService.createContent(content);
            UserEncodeKey userEncodeKey = new UserEncodeKey();
            userEncodeKey.setEncodeKeyId(GogoTools.UUID32());
            userEncodeKey.setUserId(task.getUserId());
            userEncodeKey.setEncodeKey(task.getUserEncodeKey());
            userEncodeKey.setCreateTime(new Date());
            userEncodeKey.setIndexId(task.getTaskId());
            iUserEncodeKeyService.createUserEncodeKey(userEncodeKey);
        }
    }

    @Override
    public ArrayList<Task> listTask(Map qIn) throws Exception {
        ArrayList<Task> tasks = iTaskService.listTask(qIn);
        return tasks;
    }

    @Override
    public Integer totalTask(Map qIn) {
        Integer total = iTaskService.totalTask(qIn);
        return total;
    }

    @Override
    public Task getTask(Map qIn, Boolean returnNull, String userId) throws Exception {
        Task task = iTaskService.getTask(qIn);
        if (task == null) {
            if (returnNull) {
                return null;
            }
            throw new Exception("10031");
        }
        if (userId != null) {
            if (!task.getUserId().equals(userId)) {
                //不能查询不属于自己的任务
                throw new Exception("10036");
            }
        }
        return task;
    }

    @Override
    public void updateTask(Map qIn) throws Exception {
        iTaskService.updateTask(qIn);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteTask(Map qIn) throws Exception {
        /**
         * 删除任务有3种场景
         * 1、creativeNote保存时，creativeNote的task是一个数组，在creativeNote的business层会遍历这个数组，如果数据库里的task不在前端提交的数组里，则删除
         * 2、删除Note时，如果note是creativeNote，则需要删除noteId的所有task
         * 3、删除task时，包括使命任务，四象限任务的删除。目前未启用子任务，所以，直接删除即可
         *
         * 注意：删除task时，一定要删除对应的content_detail，如果以noteId来删除，则需要查询出所有task，然后以taskId逐条删除content_detail
         */
        String taskId = (String) qIn.get("taskId");
        String noteId = (String) qIn.get("noteId");
        if (noteId != null) {
            //根据noteId，查询所有的task
            Map qIn2 = new HashMap();
            qIn2.put("noteId", noteId);
            ArrayList<Task> tasks = iTaskService.listTask(qIn2);
            if (tasks.size() > 0) {
                for (int i = 0; i < tasks.size(); i++) {
                    //删除任务详情
                    iTaskService.deleteTaskContent(tasks.get(i).getTaskId());
                }
                //删除所有noteId对应的任务
                iTaskService.deleteTask(qIn2);
            }
        } else {
            if (taskId != null) {
                Map qIn2 = new HashMap();
                qIn2.put("taskId", taskId);
                //删除任务
                iTaskService.deleteTask(qIn2);
                //删除任务详情
                iTaskService.deleteTaskContent(taskId);
            }
        }
    }
}
