package cc.cdtime.lifecapsule_v3_api.business.adminUserAct;

import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUserView;
import cc.cdtime.lifecapsule_v3_api.meta.userAct.entity.UserActView;
import cc.cdtime.lifecapsule_v3_api.middle.admin.IAdminUserMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.userAct.IUserActMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminUserActBService implements IAdminUserActBService {
    private final IAdminUserMiddle iAdminUserMiddle;
    private final IUserActMiddle iUserActMiddle;

    public AdminUserActBService(IAdminUserMiddle iAdminUserMiddle, IUserActMiddle iUserActMiddle) {
        this.iAdminUserMiddle = iAdminUserMiddle;
        this.iUserActMiddle = iUserActMiddle;
    }

    @Override
    public Map listUserAct(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageSize = (Integer) in.get("pageSize");
        Integer pageIndex = (Integer) in.get("pageIndex");
        Date startTime = (Date) in.get("startTime");
        Date endTime = (Date) in.get("endTime");
        String nickname = (String) in.get("nickname");

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        Integer offset = (pageIndex - 1) * pageSize;
        qIn = new HashMap();
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        qIn.put("logStartTime", startTime);
        qIn.put("logEndTime", endTime);
        if (nickname != null && !nickname.equals("")) {
            qIn.put("nickname", nickname);
        }
        ArrayList<UserActView> userActViews = iUserActMiddle.listUserAct(qIn);
        Integer total = iUserActMiddle.totalUserAct(qIn);

        Map out = new HashMap();
        out.put("userActList", userActViews);
        out.put("totalUserAct", total);

        return out;
    }
}
