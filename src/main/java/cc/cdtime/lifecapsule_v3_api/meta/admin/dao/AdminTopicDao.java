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

    ArrayList<TopicView> listTopic(Map qIn);
}
