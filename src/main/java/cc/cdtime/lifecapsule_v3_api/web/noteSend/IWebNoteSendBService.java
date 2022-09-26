package cc.cdtime.lifecapsule_v3_api.web.noteSend;

import java.util.Map;

public interface IWebNoteSendBService {

    Map listMyNoteReceiveLog(Map in) throws Exception;

    Map getMyReceiveNote(Map in) throws Exception;

    /**
     * web端用户读取自己发送的记录列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listMyNoteSendOutLog(Map in) throws Exception;

    Map getMyNoteSendOutLog(Map in) throws Exception;
}
