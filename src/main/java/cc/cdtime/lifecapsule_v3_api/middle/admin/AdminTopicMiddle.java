package cc.cdtime.lifecapsule_v3_api.middle.admin;

import cc.cdtime.lifecapsule_v3_api.meta.admin.service.IAdminTopicService;
import cc.cdtime.lifecapsule_v3_api.meta.content.entity.Content;
import cc.cdtime.lifecapsule_v3_api.meta.content.service.IContentService;
import cc.cdtime.lifecapsule_v3_api.meta.topic.entity.TopicView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class AdminTopicMiddle implements IAdminTopicMiddle {
    private final IAdminTopicService iAdminTopicService;
    private final IContentService iContentService;

    public AdminTopicMiddle(IAdminTopicService iAdminTopicService,
                            IContentService iContentService) {
        this.iAdminTopicService = iAdminTopicService;
        this.iContentService = iContentService;
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

    @Override
    public Integer totalTopic(Map qIn) throws Exception {
        Integer total = iAdminTopicService.totalTopic(qIn);
        return total;
    }

    @Override
    public TopicView getTopic(String topicId) throws Exception {
        TopicView topicView = iAdminTopicService.getTopic(topicId);
        Content content = iContentService.getContent(topicView.getTopicId());
        topicView.setContent(content.getContent());
        return topicView;
    }

    @Override
    public void deleteTopic(String topicId) throws Exception {
        iAdminTopicService.deleteTopic(topicId);
        iContentService.deleteContent(topicId);
    }
}
