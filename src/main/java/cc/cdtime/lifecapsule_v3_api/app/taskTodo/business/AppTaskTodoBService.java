package cc.cdtime.lifecapsule_v3_api.app.taskTodo.business;

import cc.cdtime.lifecapsule_v3_api.business.taskTodo.ITaskTodoBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppTaskTodoBService implements IAppTaskTodoBService {
    private final ITaskTodoBService iTaskTodoBService;

    public AppTaskTodoBService(ITaskTodoBService iTaskTodoBService) {
        this.iTaskTodoBService = iTaskTodoBService;
    }

    @Override
    public Map listMyTaskTodo(Map in) throws Exception {
        Map out = iTaskTodoBService.listTaskTodo(in);
        return out;
    }

    @Override
    public void saveMyTaskTodo(Map in) throws Exception {
        iTaskTodoBService.saveTaskTodo(in);
    }

    @Override
    public Map getMyTaskTodo(Map in) throws Exception {
        Map out = iTaskTodoBService.getTaskTodo(in);
        return out;
    }

    @Override
    public void deleteMyTaskTodo(Map in) throws Exception {
        iTaskTodoBService.deleteTaskTodo(in);
    }
}
