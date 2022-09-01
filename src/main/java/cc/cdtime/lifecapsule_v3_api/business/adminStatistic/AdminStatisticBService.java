package cc.cdtime.lifecapsule_v3_api.business.adminStatistic;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminStatisticView;
import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUserView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.admin.IAdminStatisticMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.admin.IAdminUserMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminStatisticBService implements IAdminStatisticBService {
    private final IAdminUserMiddle iAdminUserMiddle;
    private final IAdminStatisticMiddle iAdminStatisticMiddle;
    private final IUserMiddle iUserMiddle;

    public AdminStatisticBService(IAdminUserMiddle iAdminUserMiddle,
                                  IAdminStatisticMiddle iAdminStatisticMiddle,
                                  IUserMiddle iUserMiddle) {
        this.iAdminUserMiddle = iAdminUserMiddle;
        this.iAdminStatisticMiddle = iAdminStatisticMiddle;
        this.iUserMiddle = iUserMiddle;
    }

    @Override
    public Map totalUserLogin(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");
        Date startTime = (Date) in.get("startTime");
        Date endTime = (Date) in.get("endTime");

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        if (startTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String s = sdf.format(startTime);
            startTime = sdf.parse(s);
        }
        qIn.put("startTime", startTime);
        if (endTime != null) {
            /**
             * 如果有endtime，endtime需要+1天
             */
            long time = endTime.getTime();
            endTime.setTime(time + 1000 * 3600 * 24);
        }
        qIn.put("endTime", endTime);
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);

        ArrayList<AdminStatisticView> adminStatisticViews = iAdminStatisticMiddle.totalUserLogin(qIn);

        Map out = new HashMap();

        for (int i = 0; i < adminStatisticViews.size(); i++) {
            qIn = new HashMap();
            qIn.put("userId", adminStatisticViews.get(i).getUserId());
            UserView userView = iUserMiddle.getUser(qIn, true, false);
            adminStatisticViews.get(i).setNickname(userView.getNickname());
            adminStatisticViews.get(i).setEmail(userView.getEmail());
        }
        out.put("statistics", adminStatisticViews);

        return out;

    }
}
