package cc.cdtime.lifecapsule_v3_api.framework.common;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.meta.userAct.entity.UserAct;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.userAct.IUserActMiddle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommonService implements ICommonService {
    private final IUserActMiddle iUserActMiddle;
    private final IUserMiddle iUserMiddle;

    public CommonService(IUserActMiddle iUserActMiddle, IUserMiddle iUserMiddle) {
        this.iUserActMiddle = iUserActMiddle;
        this.iUserMiddle = iUserMiddle;
    }

    @Override
    public void createUserActLog(Map in) throws Exception {
        ESTags actType = (ESTags) in.get("UserActType");
        String token = (String) in.get("token");
        HashMap memoMap = (HashMap) in.get("memo");
        ESTags result = (ESTags) in.get("result");

        String userId = null;
        if (token != null) {
            Map qIn = new HashMap();
            qIn.put("token", token);
            UserView userView = iUserMiddle.getUserLogin(qIn, true);
            if (userView != null) {
                userId = userView.getUserId();
            }
        } else {

            String deviceCode = (String) memoMap.get("deviceCode");
            if (deviceCode != null) {
                Map qIn = new HashMap();
                qIn.put("deviceCode", deviceCode);
                ArrayList<UserView> userViews = iUserMiddle.listUserLoginLog(qIn);
                if (userViews.size() > 0) {
                    userId = userViews.get(0).getUserId();
                }
            }
        }

        UserAct userAct = new UserAct();
        userAct.setActType(actType.toString());
        userAct.setCreateTime(new Date());
        userAct.setUserId(userId);
        userAct.setMemo(GogoTools.convertMapToString(memoMap));
        userAct.setResult(result.toString());
        iUserActMiddle.createUserAct(userAct);
    }
}
