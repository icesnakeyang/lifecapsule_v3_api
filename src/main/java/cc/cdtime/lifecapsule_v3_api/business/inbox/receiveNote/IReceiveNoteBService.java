package cc.cdtime.lifecapsule_v3_api.business.inbox.receiveNote;

import java.util.Map;

public interface IReceiveNoteBService {
    Map listReceiveNote(Map in) throws Exception;

    Map getMyReceiveNote(Map in) throws Exception;
}
