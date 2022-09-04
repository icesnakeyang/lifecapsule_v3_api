package cc.cdtime.lifecapsule_v3_api.meta.content.service;

import cc.cdtime.lifecapsule_v3_api.meta.content.dao.ContentDao;
import cc.cdtime.lifecapsule_v3_api.meta.content.entity.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ContentService implements IContentService {
    private final ContentDao contentDao;

    public ContentService(ContentDao contentDao) {
        this.contentDao = contentDao;
    }

    @Override
    public void createContent(Content content) throws Exception {
        contentDao.createContent(content);
    }

    @Override
    public Content getContent(String indexId) throws Exception {
        Content content = contentDao.getContent(indexId);
        return content;
    }

    @Override
    public void updateContent(Map qIn) throws Exception {
        contentDao.updateContent(qIn);
    }

    @Override
    public void deleteContent(String indexId) throws Exception {
        contentDao.deleteContent(indexId);
    }
}
