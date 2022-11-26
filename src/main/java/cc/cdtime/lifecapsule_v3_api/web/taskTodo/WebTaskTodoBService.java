package cc.cdtime.lifecapsule_v3_api.web.taskTodo;

import cc.cdtime.lifecapsule_v3_api.business.taskTodo.ITaskTodoBService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebTaskTodoBService implements IWebTaskTodoBService {
    private final ITaskTodoBService iTaskTodoBService;

    public WebTaskTodoBService(ITaskTodoBService iTaskTodoBService) {
        this.iTaskTodoBService = iTaskTodoBService;
    }

    @Override
    public Map listMyTaskTodo(Map in) throws Exception {
        Map out = iTaskTodoBService.listTaskTodo(in);
        return out;
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
