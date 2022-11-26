package cc.cdtime.lifecapsule_v3_api.app.taskTodo.business;

import java.util.Map;

public interface IAppTaskTodoBService {
    /**
     * 手机端用户查询自己的代办任务列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listMyTaskTodo(Map in) throws Exception;

    /**
     * 手机端用户读取一个代办任务详情
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getMyTaskTodo(Map in) throws Exception;

    /**
     * 手机端用户删除一个代办任务
     *
     * @param in
     * @throws Exception
     */
    void deleteMyTaskTodo(Map in) throws Exception;

    void createMyTaskTodo(Map in) throws Exception;

    void updateTaskTodoComplete(Map in) throws Exception;

    void updateMyTaskTodo(Map in) throws Exception;
}
