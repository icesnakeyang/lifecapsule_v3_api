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
     * 手机端用户创建或者保存自己的代办任务列表
     *
     * @param in
     * @throws Exception
     */
    void saveMyTaskTodo(Map in) throws Exception;

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
}
