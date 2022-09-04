package cc.cdtime.lifecapsule_v3_api.meta.content.service;

import cc.cdtime.lifecapsule_v3_api.meta.content.entity.Content;

import java.util.Map;

public interface IContentService {
    void createContent(Content content) throws Exception;

    Content getContent(String indexId) throws Exception;

    /**
     * 修改内容表
     *
     * @param qIn indexId
     *            content
     */
    void updateContent(Map qIn) throws Exception;

    void deleteContent(String indexId) throws Exception;
}
