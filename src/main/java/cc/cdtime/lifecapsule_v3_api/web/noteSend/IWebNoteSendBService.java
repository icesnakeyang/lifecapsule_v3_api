package cc.cdtime.lifecapsule_v3_api.web.noteSend;

import java.util.Map;

public interface IWebNoteSendBService {
    void webSendNote(Map in) throws Exception;

    Map listMyNoteReceiveLog(Map in) throws Exception;

    Map getMyReceiveNote(Map in) throws Exception;
}
