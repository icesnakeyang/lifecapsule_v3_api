package cc.cdtime.lifecapsule_v3_api.business.publicWeb;

import java.util.Map;

public interface IPublicWebBService {
    void publishNoteToPublicWeb(Map in) throws Exception;

    Map listPublicNote(Map in) throws Exception;

    Map getMyPublicNote(Map in) throws Exception;
}
