package cc.cdtime.lifecapsule_v3_api.admin.user;

import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUserView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.admin.IAdminUserMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminUserBService implements IAdminUserBService {
    private final IAdminUserMiddle iAdminUserMiddle;
    private final IUserMiddle iUserMiddle;


    public AdminUserBService(IAdminUserMiddle iAdminUserMiddle,
                             IUserMiddle iUserMiddle) {
        this.iAdminUserMiddle = iAdminUserMiddle;
        this.iUserMiddle = iUserMiddle;
    }

    @Override
    public Map listUsers(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        ArrayList<UserView> userViews = iUserMiddle.listUser(qIn);
        Integer totalUser = iUserMiddle.totalUser(qIn);

        Map out = new HashMap();
        out.put("userList", userViews);
        out.put("totalUser", totalUser);

        return out;

    }
}
