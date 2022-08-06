package cc.cdtime.lifecapsule_v3_api.app.noteSend;

import java.util.Map;

public interface IAppNoteSendBService {
    /**
     * App端用户发送笔记给其他用户
     *
     * @param in
     * @throws Exception
     */
    void sendNote(Map in) throws Exception;

    /**
     * App端用户查询其他用户
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map searchUser(Map in) throws Exception;

    /**
     * App端用户读取自己的note发送和接收统计信息
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map loadMyNoteSendStatistic(Map in) throws Exception;

    /**
     * App端用户读取自己的note发送和接收记录列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listMyNoteReceiveLog(Map in) throws Exception;

    Map getMyReceiveNote(Map in) throws Exception;
}
