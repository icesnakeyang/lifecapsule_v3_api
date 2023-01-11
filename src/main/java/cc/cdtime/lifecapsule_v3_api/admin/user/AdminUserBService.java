package cc.cdtime.lifecapsule_v3_api.admin.user;

import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUserView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.admin.IAdminUserMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
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
    private final INoteMiddle iNoteMiddle;


    public AdminUserBService(IAdminUserMiddle iAdminUserMiddle,
                             IUserMiddle iUserMiddle,
                             INoteMiddle iNoteMiddle) {
        this.iAdminUserMiddle = iAdminUserMiddle;
        this.iUserMiddle = iUserMiddle;
        this.iNoteMiddle = iNoteMiddle;
    }

    @Override
    public Map listUsers(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");
        String searchKey = (String) in.get("searchKey");

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        if(searchKey!=null && !searchKey.equals("")) {
            qIn.put("email", searchKey);
        }
        ArrayList<UserView> userViews = iUserMiddle.listUser(qIn);
        Integer totalUser = iUserMiddle.totalUser(qIn);

        Map out = new HashMap();
        out.put("userList", userViews);
        out.put("totalUser", totalUser);

        return out;

    }

    @Override
    public Map listUserLoginLog(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");
        String searchKey = (String) in.get("searchKey");

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        qIn.put("loginName", searchKey);
        ArrayList<UserView> userViews = iUserMiddle.listUserLoginLog(qIn);
        Integer totalUserLogs = iUserMiddle.totalUserLoginLog(qIn);

        Map out = new HashMap();
        out.put("userList", userViews);
        out.put("totalUserLogs", totalUserLogs);

        return out;
    }
}
