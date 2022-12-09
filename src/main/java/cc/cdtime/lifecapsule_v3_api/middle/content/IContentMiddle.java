package cc.cdtime.lifecapsule_v3_api.middle.content;

import cc.cdtime.lifecapsule_v3_api.meta.content.entity.Content;

public interface IContentMiddle {
    Content getContent(String indexId) throws Exception;
}
