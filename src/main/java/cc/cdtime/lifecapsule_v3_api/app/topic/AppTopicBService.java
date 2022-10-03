package cc.cdtime.lifecapsule_v3_api.app.topic;

import cc.cdtime.lifecapsule_v3_api.business.topic.ITopicBService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AppTopicBService implements IAppTopicBService {
    private final ITopicBService iTopicBService;

    public AppTopicBService(ITopicBService iTopicBService) {
        this.iTopicBService = iTopicBService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publishNoteToTopic(Map in) throws Exception {
        iTopicBService.publishNoteToTopic(in);
    }

    @Override
    public Map listTopicPublic(Map in) throws Exception {
        Map out = iTopicBService.listTopicPublic(in);
        return out;
    }

    @Override
    public Map getTopicDetail(Map in) throws Exception {
        Map out = iTopicBService.getTopicDetail(in);
        return out;
    }

    @Override
    public void replyComment(Map in) throws Exception {
        iTopicBService.replyComment(in);
    }
}
