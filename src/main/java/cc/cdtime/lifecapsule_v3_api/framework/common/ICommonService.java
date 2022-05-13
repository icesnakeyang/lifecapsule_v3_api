package cc.cdtime.lifecapsule_v3_api.framework.common;

import java.util.Map;

public interface ICommonService {
    /**
     * 记录用户行为日志
     *
     * @param in
     * @throws Exception
     */
    void createUserActLog(Map in) throws Exception;
}
