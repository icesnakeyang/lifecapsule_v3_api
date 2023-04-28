package cc.cdtime.lifecapsule_v3_api.web.topic;

import cc.cdtime.lifecapsule_v3_api.business.topic.ITopicBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebTopicBService implements IWebTopicBService {
    private final ITopicBService iTopicBService;

    public WebTopicBService(ITopicBService iTopicBService) {
        this.iTopicBService = iTopicBService;
    }

    @Override
    public void webPublishNoteToTopic(Map in) throws Exception {
        iTopicBService.publishNoteToTopic(in);
    }
}
