package cc.cdtime.lifecapsule_v3_api.business.topic;

import java.util.Map;

public interface ITopicBService {
    /**
     * 用户把笔记发表到公共话题讨论区
     *
     * @param in
     * @throws Exception
     */
    void publishNoteToTopic(Map in) throws Exception;

    /**
     * 捞10条话题出来
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listTopicPublic(Map in) throws Exception;

    /**
     * 读取一条话题详情
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getTopicDetail(Map in) throws Exception;
    void replyComment(Map in) throws Exception;
}
