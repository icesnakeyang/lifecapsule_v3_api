package cc.cdtime.lifecapsule_v3_api.meta.admin.service;

import cc.cdtime.lifecapsule_v3_api.meta.topic.entity.TopicView;

import java.util.ArrayList;
import java.util.Map;

public interface IAdminTopicService {
    /**
     * 管理员修改topic
     *
     * @param qIn status
     *            topicId
     */
    void updateTopic(Map qIn) throws Exception;

    ArrayList<TopicView> listTopic(Map qIn) throws Exception;
}
