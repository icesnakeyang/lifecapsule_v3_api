package cc.cdtime.lifecapsule_v3_api.middle.admin;

import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminStatisticView;

import java.util.ArrayList;
import java.util.Map;

public interface IAdminStatisticMiddle {
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
