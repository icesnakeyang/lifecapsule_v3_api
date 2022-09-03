package cc.cdtime.lifecapsule_v3_api.business.noteSend;

import java.util.Map;

public interface INoteSendBService {
    void sendNote(Map in) throws Exception;

    Map searchUser(Map in) throws Exception;

    Map loadMyNoteSendStatistic(Map in) throws Exception;

    Map listNoteReceiveLog(Map in) throws Exception;

    Map listNoteSendLogSend(Map in) throws Exception;

    Map getNoteSendLog(Map in) throws Exception;

    /**
     * 发送人查看自己发送的笔记详情
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getNoteSendLogSender(Map in) throws Exception;

    void deleteSendNote(Map in) throws Exception;
}
