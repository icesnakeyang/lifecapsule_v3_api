package cc.cdtime.lifecapsule_v3_api.business.adminStatistic;

import java.util.Map;

public interface IAdminStatisticBService {
    /**
     * 统计用户登录次数
     *
     * @param in
     * @return
     */
    Map totalUserLogin(Map in) throws Exception;
}
