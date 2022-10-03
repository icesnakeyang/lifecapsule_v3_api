package cc.cdtime.lifecapsule_v3_api.meta.topic.dao;

import cc.cdtime.lifecapsule_v3_api.meta.topic.entity.Topic;
import cc.cdtime.lifecapsule_v3_api.meta.topic.entity.TopicView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface TopicDao {
    /**
     * 创建一个话题
     *
     * @param topic
     */
    void createTopic(Topic topic);

    /**
     * 查询话题列表
     *
     * @param qIn size
     *            offset
     *            pid
     * @return
     */
    ArrayList<TopicView> listTopic(Map qIn);

    /**
     * 查看话题详情
     *
     * @param topicId
     * @return
     */
    TopicView getTopic(String topicId);

    /**
     * 修改话题
     *
     * @param qIn status
     *            views
     *            comments
     *            topicId
     */
    void updateTopic(Map qIn);
}
