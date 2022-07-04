package cc.cdtime.lifecapsule_v3_api.business.taskTodo;

import java.util.Map;

public interface ITaskTodoBService {
    /**
     * 查询代办任务列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listTaskTodo(Map in) throws Exception;

    /**
     * 新增或者保存一个待办任务
     *
     * @param in
     * @throws Exception
     */
    void saveTaskTodo(Map in) throws Exception;

    Map getTaskTodo(Map in) throws Exception;

    /**
     * 删除一个代办任务
     *
     * @param in
     * @throws Exception
     */
    void deleteTaskTodo(Map in) throws Exception;
}
