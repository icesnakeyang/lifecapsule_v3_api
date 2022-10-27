package cc.cdtime.lifecapsule_v3_api.admin.motto;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUserView;
import cc.cdtime.lifecapsule_v3_api.meta.motto.entity.MottoView;
import cc.cdtime.lifecapsule_v3_api.middle.admin.IAdminUserMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.motto.IMottoMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminMottoBService implements IAdminMottoBService {
    private final IAdminUserMiddle iAdminUserMiddle;
    private final IMottoMiddle iMottoMiddle;

    public AdminMottoBService(IAdminUserMiddle iAdminUserMiddle,
                              IMottoMiddle iMottoMiddle) {
        this.iAdminUserMiddle = iAdminUserMiddle;
        this.iMottoMiddle = iMottoMiddle;
    }

    @Override
    public Map listMotto(Map in) throws Exception {
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
        ArrayList<MottoView> mottoViews = iMottoMiddle.listMotto(qIn);
        Integer totalMotto = iMottoMiddle.totalMotto(qIn);

        Map out = new HashMap();
        out.put("mottoList", mottoViews);
        out.put("totalMotto", totalMotto);

        return out;
    }

    @Override
    public Map getMotto(Map in) throws Exception {
        String token = in.get("token").toString();
        String mottoId = in.get("mottoId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        qIn.put("mottoId", mottoId);
        MottoView mottoView = iMottoMiddle.getMotto(qIn, false);

        Map out = new HashMap();
        out.put("motto", mottoView);

        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setMottoStop(Map in) throws Exception {
        String token = in.get("token").toString();
        String mottoId = in.get("mottoId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        qIn.put("mottoId", mottoId);
        MottoView mottoView = iMottoMiddle.getMotto(qIn, false);

        qIn = new HashMap();
        qIn.put("status", ESTags.STOP);
        qIn.put("mottoId", mottoId);
        iMottoMiddle.updateMotto(qIn);
    }

    @Override
    public void setMottoActive(Map in) throws Exception {
        String token = in.get("token").toString();
        String mottoId = in.get("mottoId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        qIn.put("mottoId", mottoId);
        MottoView mottoView = iMottoMiddle.getMotto(qIn, false);

        qIn = new HashMap();
        qIn.put("status", ESTags.ACTIVE);
        qIn.put("mottoId", mottoId);
        iMottoMiddle.updateMotto(qIn);
    }
}
