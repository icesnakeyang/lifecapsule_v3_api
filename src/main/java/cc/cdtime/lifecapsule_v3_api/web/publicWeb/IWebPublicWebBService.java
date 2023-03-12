package cc.cdtime.lifecapsule_v3_api.web.publicWeb;

import java.util.Map;

public interface IWebPublicWebBService {
    /**
     * Web端用户把笔记发布到公共网页上
     *
     * @param in
     * @throws Exception
     */
    void publishNoteToPublicWeb(Map in) throws Exception;

    /**
     * Web端用户查询我的公开笔记列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listMyPublicNote(Map in) throws Exception;

    Map getMyPublicNote(Map in) throws Exception;
}
