package cc.cdtime.lifecapsule_v3_api.app.topic;

import java.util.Map;

public interface IAppTopicBService {
    /**
     * App端用户把笔记发表到公共话题讨论区
     *
     * @param in
     * @throws Exception
     */
    void publishNoteToTopic(Map in) throws Exception;

    /**
     * App端捞10条话题出来
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listTopicPublic(Map in) throws Exception;

    /**
     * App读取一条话题详情
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getTopicDetail(Map in) throws Exception;

    /**
     * App回复一条话题
     *
     * @param in
     * @throws Exception
     */
    void replyComment(Map in) throws Exception;

    /**
     * App查询最热的话题标签
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listHotTopicTags(Map in) throws Exception;
}
