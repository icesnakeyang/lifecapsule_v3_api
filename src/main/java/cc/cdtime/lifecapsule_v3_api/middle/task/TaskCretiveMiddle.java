package cc.cdtime.lifecapsule_v3_api.middle.task;

import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskCreative;
import cc.cdtime.lifecapsule_v3_api.meta.task.service.ITaskCreativeService;
import cc.cdtime.lifecapsule_v3_api.meta.task.service.TaskCreativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskCretiveMiddle implements ITaskCretiveMiddle {
    private final ITaskCreativeService iTaskCreativeService;

    public TaskCretiveMiddle(ITaskCreativeService iTaskCreativeService) {
        this.iTaskCreativeService = iTaskCreativeService;
    }

    @Override
    public void deleteTaskCreative(Map qIn) throws Exception {
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
            ArrayList<TaskCreative> tasks = iTaskCreativeService.listTaskCreative(qIn2);
            if (tasks.size() > 0) {
                for (int i = 0; i < tasks.size(); i++) {
                    //删除任务详情
//                    iTaskCreativeService.deleteTaskContent(tasks.get(i).getTaskId());
                }
                //删除所有noteId对应的任务
//                iTaskService.deleteTask(qIn2);
            }
        } else {
            if (taskId != null) {
                Map qIn2 = new HashMap();
                qIn2.put("taskId", taskId);
                //删除任务
//                iTaskService.deleteTask(qIn2);
                //删除任务详情
//                iTaskService.deleteTaskContent(taskId);
            }
        }
    }
}
