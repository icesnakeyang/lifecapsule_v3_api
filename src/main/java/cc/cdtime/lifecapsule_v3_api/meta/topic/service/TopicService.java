package cc.cdtime.lifecapsule_v3_api.meta.topic.service;

import cc.cdtime.lifecapsule_v3_api.meta.topic.dao.TopicDao;
import cc.cdtime.lifecapsule_v3_api.meta.topic.entity.Topic;
import cc.cdtime.lifecapsule_v3_api.meta.topic.entity.TopicView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class TopicService implements ITopicService {
    private final TopicDao topicDao;

    public TopicService(TopicDao topicDao) {
        this.topicDao = topicDao;
    }

    @Override
    public void createTopic(Topic topic) throws Exception {
        topicDao.createTopic(topic);
    }

    @Override
    public ArrayList<TopicView> listTopic(Map qIn) throws Exception {
        ArrayList<TopicView> topicViews = topicDao.listTopic(qIn);
        return topicViews;
    }

    @Override
    public TopicView getTopic(String topicId) throws Exception {
        TopicView topicView = topicDao.getTopic(topicId);
        return topicView;
    }

    @Override
    public void updateTopic(Map qIn) throws Exception {
        topicDao.updateTopic(qIn);
    }
}
