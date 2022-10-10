package cc.cdtime.lifecapsule_v3_api.meta.admin.service;

import cc.cdtime.lifecapsule_v3_api.meta.admin.dao.AdminTopicDao;
import cc.cdtime.lifecapsule_v3_api.meta.topic.entity.TopicView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class AdminTopicService implements IAdminTopicService {
    private final AdminTopicDao adminTopicDao;

    public AdminTopicService(AdminTopicDao adminTopicDao) {
        this.adminTopicDao = adminTopicDao;
    }

    @Override
    public void updateTopic(Map qIn) throws Exception {
        adminTopicDao.updateTopic(qIn);
    }

    @Override
    public ArrayList<TopicView> listTopic(Map qIn) throws Exception {
        ArrayList<TopicView> topicViews = adminTopicDao.listTopic(qIn);
        return topicViews;
    }
}