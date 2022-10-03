package cc.cdtime.lifecapsule_v3_api.middle.topic;

import cc.cdtime.lifecapsule_v3_api.meta.topic.entity.Topic;
import cc.cdtime.lifecapsule_v3_api.meta.topic.entity.TopicView;

import java.util.ArrayList;
import java.util.Map;

public interface ITopicMiddle {
    /**
     * 创建一个话题
     *
     * @param topic
     */
    void createTopic(Topic topic) throws Exception;

    /**
     * 查询话题列表
     *
     * @param qIn size
     *            offset
     *            pid
     * @return
     */
    ArrayList<TopicView> listTopic(Map qIn) throws Exception;

    /**
     * 查看话题详情
     *
     * @param topicId
     * @return
     */
    TopicView getTopic(String topicId, Boolean returnNull) throws Exception;

    /**
     * 修改话题
     *
     * @param qIn status
     *            views
     *            comments
     *            topicId
     */
    void updateTopic(Map qIn) throws Exception;
}
