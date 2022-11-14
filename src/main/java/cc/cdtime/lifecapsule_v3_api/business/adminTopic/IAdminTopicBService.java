package cc.cdtime.lifecapsule_v3_api.business.adminTopic;

import java.util.Map;

public interface IAdminTopicBService {

    void removeTopic(Map in) throws Exception;

    /**
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listTopic(Map in) throws Exception;

    Map getTopic(Map in) throws Exception;

    void activeTopic(Map in) throws Exception;
}
