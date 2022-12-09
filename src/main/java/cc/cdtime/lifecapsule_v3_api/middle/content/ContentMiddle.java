package cc.cdtime.lifecapsule_v3_api.middle.content;

import cc.cdtime.lifecapsule_v3_api.meta.content.entity.Content;
import cc.cdtime.lifecapsule_v3_api.meta.content.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentMiddle implements IContentMiddle {
    private final IContentService iContentService;

    public ContentMiddle(IContentService iContentService) {
        this.iContentService = iContentService;
    }

    @Override
    public Content getContent(String indexId) throws Exception {
        Content content = iContentService.getContent(indexId);
        return content;
    }
}
