package cc.cdtime.lifecapsule_v3_api.meta.content.dao;

import cc.cdtime.lifecapsule_v3_api.meta.content.entity.Content;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ContentDao {
    void createContent(Content content);

    Content getContent(String indexId);

    /**
     * 修改内容表
     *
     * @param qIn indexId
     *            content
     */
    void updateContent(Map qIn);

    void deleteContent(String indexId);
}
