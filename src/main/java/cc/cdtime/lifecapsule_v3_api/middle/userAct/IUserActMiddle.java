package cc.cdtime.lifecapsule_v3_api.middle.userAct;


import cc.cdtime.lifecapsule_v3_api.meta.userAct.entity.UserAct;

public interface IUserActMiddle {
    /**
     * 记录用户行为日志
     *
     * @param userAct
     */
    void createUserAct(UserAct userAct) throws Exception;

}
