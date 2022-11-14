package cc.cdtime.lifecapsule_v3_api.middle.admin;

import cc.cdtime.lifecapsule_v3_api.meta.topic.entity.TopicView;

import java.util.ArrayList;
import java.util.Map;

public interface IAdminTopicMiddle {
    /**
     * 管理员修改topic
     *
     * @param qIn status
     *            topicId
     */
    void updateTopic(Map qIn) throws Exception;

    /**
     * 查询话题列表
     *
     * @param qIn isRoot
     *            status
     *            size
     *            offset
     * @return
     */
    ArrayList<TopicView> listTopic(Map qIn) throws Exception;
    Integer totalTopic(Map qIn) throws Exception;
    TopicView getTopic(String topicId) throws Exception;

    void deleteTopic(String topicId) throws Exception;
}
