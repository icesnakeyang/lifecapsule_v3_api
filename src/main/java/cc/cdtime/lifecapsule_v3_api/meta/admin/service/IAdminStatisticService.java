package cc.cdtime.lifecapsule_v3_api.meta.admin.service;

import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminStatisticView;

import java.util.ArrayList;
import java.util.Map;

public interface IAdminStatisticService {
    /**
     * 统计用户登录次数
     *
     * @param qIn startTime
     *            endTime
     *            size
     *            offset
     * @return
     */
    ArrayList<AdminStatisticView> totalUserLogin(Map qIn) throws Exception;
}
