package cc.cdtime.lifecapsule_v3_api.middle.topic;

import cc.cdtime.lifecapsule_v3_api.meta.content.entity.Content;
import cc.cdtime.lifecapsule_v3_api.meta.content.service.IContentService;
import cc.cdtime.lifecapsule_v3_api.meta.topic.entity.Topic;
import cc.cdtime.lifecapsule_v3_api.meta.topic.entity.TopicView;
import cc.cdtime.lifecapsule_v3_api.meta.topic.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class TopicMiddle implements ITopicMiddle {
    private final ITopicService iTopicService;
    private final IContentService iContentService;

    public TopicMiddle(ITopicService iTopicService,
                       IContentService iContentService) {
        this.iTopicService = iTopicService;
        this.iContentService = iContentService;
    }

    @Override
    public void createTopic(Topic topic) throws Exception {
        iTopicService.createTopic(topic);
        if (topic.getContent() != null) {
            Content content = new Content();
            content.setContent(topic.getContent());
            content.setIndexId(topic.getTopicId());
            iContentService.createContent(content);
            /**
             * 如果要加密，这里还需要保存userEncodeKey
             */
        }
    }

    @Override
    public ArrayList<TopicView> listTopic(Map qIn) throws Exception {
        ArrayList<TopicView> topicViews = iTopicService.listTopic(qIn);
        return topicViews;
    }

    @Override
    public TopicView getTopic(String topicId, Boolean returnNull) throws Exception {
        TopicView topicView = iTopicService.getTopic(topicId);
        if (topicView == null) {
            if (returnNull) {
                return null;
            }
            //没有查询到该话题
            throw new Exception("10058");
        }
        return topicView;
    }

    @Override
    public void updateTopic(Map qIn) throws Exception {
        iTopicService.updateTopic(qIn);
    }
}
