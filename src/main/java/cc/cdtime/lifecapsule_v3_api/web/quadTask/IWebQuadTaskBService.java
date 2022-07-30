package cc.cdtime.lifecapsule_v3_api.web.quadTask;

import java.util.Map;

public interface IWebQuadTaskBService {
    /**
     * 网页用户查看我的四象限任务列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listMyQuadTask(Map in) throws Exception;

    /**
     * 网页用户创建一条四象限任务
     *
     * @param in
     * @throws Exception
     */
    void createMyQuadTask(Map in) throws Exception;

    /**
     * 网页用户修改一条四象限任务
     *
     * @param in
     * @throws Exception
     */
    void updateMyQuadTask(Map in) throws Exception;

    /**
     * web端用户读取一条四象限任务详情
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getMyQuadTask(Map in) throws Exception;

    /**
     * 用户设置任务为已完成
     *
     * @param in
     * @throws Exception
     */
    void setMyTaskComplete(Map in) throws Exception;

    /**
     * 用户设置任务为进行中
     *
     * @param in
     * @throws Exception
     */
    void setMyTaskProgress(Map in) throws Exception;

    /**
     * 用户增加任务优先级
     * 增加优先级就是增加task的priority值
     * 每次只能+1
     *
     * @param in
     * @throws Exception
     */
    void increaseQuadTaskPriority(Map in) throws Exception;

    /**
     * 用户减少任务优先级
     * 减少优先级就是减少task的priority字段的值，可以为负数
     * 每次只能-1
     *
     * @param in
     * @throws Exception
     */
    void decreaseQuadTaskPriority(Map in) throws Exception;

    /**
     * web端用户物理删除一个四象限任务
     *
     * @param in
     * @throws Exception
     */
    void deleteQuadTask(Map in) throws Exception;
}
