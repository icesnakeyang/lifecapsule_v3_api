package cc.cdtime.lifecapsule_v3_api.meta.topic.service;

import cc.cdtime.lifecapsule_v3_api.meta.topic.entity.Topic;
import cc.cdtime.lifecapsule_v3_api.meta.topic.entity.TopicView;

import java.util.ArrayList;
import java.util.Map;

public interface ITopicService {
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
     *            status
     * @return
     */
    ArrayList<TopicView> listTopic(Map qIn) throws Exception;

    /**
     * 查看话题详情
     *
     * @param topicId
     * @return
     */
    TopicView getTopic(String topicId) throws Exception;

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
