package cc.cdtime.lifecapsule_v3_api.business.adminUserAct;


import java.util.Map;

public interface IAdminUserActBService {
    /**
     * 查询用户行为日志列表
     *
     * @param in logStartTime
     *            logEndTime
     *            size
     *            offset
     * @return
     */
    Map listUserAct(Map in) throws Exception;
}
