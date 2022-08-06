package cc.cdtime.lifecapsule_v3_api.meta.userAct.service;


import cc.cdtime.lifecapsule_v3_api.meta.userAct.entity.UserAct;

import java.util.Map;

public interface IUserActService {
    /**
     * 记录用户行为日志
     *
     * @param userAct
     */
    void createUserAct(UserAct userAct) throws Exception;
    Integer totalUserAct(Map qIn) throws Exception;
}
