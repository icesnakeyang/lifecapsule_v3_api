package cc.cdtime.lifecapsule_v3_api.business.adminUser;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUser;
import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUserView;
import cc.cdtime.lifecapsule_v3_api.middle.admin.IAdminUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminLoginBService implements IAdminLoginBService {
    private final IAdminUserMiddle iAdminUserMiddle;

    public AdminLoginBService(IAdminUserMiddle iAdminUserMiddle) {
        this.iAdminUserMiddle = iAdminUserMiddle;
    }

    @Override
    public void createAdmin(Map in) throws Exception {
        String loginName = in.get("loginName").toString();
        String password = in.get("password").toString();
        String roleType = in.get("roleType").toString();

        int cc = 0;
        if (roleType == null) {
            //用户角色不能为空
            throw new Exception("10026");
        }
        if (roleType.equals(ESTags.ADMIN_ROOT.toString())) {
            cc++;
        }
        if (cc == 0) {
            //用户角色不正确
            throw new Exception("10025");
        }

        AdminUser adminUser = new AdminUser();
        adminUser.setAdminId(GogoTools.UUID32());
        adminUser.setCreateTime(new Date());
        adminUser.setPassword(GogoTools.encoderByMd5(password));
        adminUser.setLoginName(loginName);
        adminUser.setRoleType(roleType);
        adminUser.setToken(GogoTools.UUID32());
        adminUser.setTokenTime(new Date());

        iAdminUserMiddle.createAdminUser(adminUser);
    }

    @Override
    public Map adminLogin(Map in) throws Exception {
        String loginName = in.get("loginName").toString();
        String password = in.get("password").toString();

        Map qIn = new HashMap();
        qIn.put("loginName", loginName);
        qIn.put("password", password);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn,false);

        String p=GogoTools.encoderByMd5(password);
        if(!adminUserView.getPassword().equals(p)){
            throw new Exception("10005");
        }
        Map out = new HashMap();
        Map admin = new HashMap();
        admin.put("token", adminUserView.getToken());
        admin.put("loginName", adminUserView.getLoginName());
        out.put("admin", admin);

        return out;
    }
}
