package cc.cdtime.lifecapsule_v3_api.business.history;

import java.util.Map;

public interface IHistoryBService {
    Map loadHistoryHome(Map in) throws Exception;

    void replyMyNote(Map in) throws Exception;
}
