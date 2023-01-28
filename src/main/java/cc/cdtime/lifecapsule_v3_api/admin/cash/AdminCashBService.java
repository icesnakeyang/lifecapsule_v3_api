package cc.cdtime.lifecapsule_v3_api.admin.cash;

import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUserView;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashView;
import cc.cdtime.lifecapsule_v3_api.middle.admin.IAdminUserMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.cash.ICashMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminCashBService implements IAdminCashBService {
    private final IAdminUserMiddle iAdminUserMiddle;
    private final ICashMiddle iCashMiddle;

    public AdminCashBService(IAdminUserMiddle iAdminUserMiddle,
                             ICashMiddle iCashMiddle) {
        this.iAdminUserMiddle = iAdminUserMiddle;
        this.iCashMiddle = iCashMiddle;
    }

    @Override
    public Map listUserCash(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");
        String userId = (String) in.get("userId");

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        qIn.put("userId", userId);
        ArrayList<CashView> cashViews = iCashMiddle.listCashLedger(qIn);

        Map out = new HashMap();
        out.put("cashList", cashViews);

        return out;
    }
}
