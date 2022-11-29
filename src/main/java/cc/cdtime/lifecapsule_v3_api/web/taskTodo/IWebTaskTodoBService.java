package cc.cdtime.lifecapsule_v3_api.web.taskTodo;

import java.util.Map;

public interface IWebTaskTodoBService {
    Map listMyTaskTodo(Map in) throws Exception;

    Map getMyTaskTodo(Map in) throws Exception;

    void deleteMyTaskTodo(Map in) throws Exception;

    void updateMyTaskTodoCompleteStatus(Map in) throws Exception;

    void createMyTaskTodo(Map in) throws Exception;

    void updateMyTaskTodo(Map in) throws Exception;
}
