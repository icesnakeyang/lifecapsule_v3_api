package cc.cdtime.lifecapsule_v3_api.middle.admin;

import cc.cdtime.lifecapsule_v3_api.meta.admin.service.IAdminTopicService;
import cc.cdtime.lifecapsule_v3_api.meta.topic.entity.TopicView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class AdminTopicMiddle implements IAdminTopicMiddle {
    private final IAdminTopicService iAdminTopicService;

    public AdminTopicMiddle(IAdminTopicService iAdminTopicService) {
        this.iAdminTopicService = iAdminTopicService;
    }

    @Override
    public void updateTopic(Map qIn) throws Exception {
        iAdminTopicService.updateTopic(qIn);
    }

    @Override
    public ArrayList<TopicView> listTopic(Map qIn) throws Exception {
        ArrayList<TopicView> topicViews = iAdminTopicService.listTopic(qIn);
        return topicViews;
    }
}
