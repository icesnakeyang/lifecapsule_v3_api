package cc.cdtime.lifecapsule_v3_api.meta.admin.dao;

import cc.cdtime.lifecapsule_v3_api.meta.topic.entity.TopicView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface AdminTopicDao {
    /**
     * 管理员修改topic
     *
     * @param qIn status
     *            topicId
     */
    void updateTopic(Map qIn);

    /**
     * 查询话题列表
     *
     * @param qIn isRoot
     *            status
     *            size
     *            offset
     * @return
     */
    ArrayList<TopicView> listTopic(Map qIn);

    Integer totalTopic(Map qIn);

    TopicView getTopic(String topicId);

    void deleteTopic(String topicId);
}
