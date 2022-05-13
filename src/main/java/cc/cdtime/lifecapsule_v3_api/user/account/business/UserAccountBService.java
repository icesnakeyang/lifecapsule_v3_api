package cc.cdtime.lifecapsule_v3_api.user.account.business;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserBase;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserLogin;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserLoginLog;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserAccountBService implements IUserAccountBService {
    private final IUserMiddle iUserMiddle;

    public UserAccountBService(IUserMiddle iUserMiddle) {
        this.iUserMiddle = iUserMiddle;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map signInByDevice(Map in) throws Exception {
        String deviceCode = in.get("deviceCode").toString();
        String deviceName = in.get("deviceName").toString();

        /**
         * 1、从deviceLog表里查询是否存在该deviceCode
         * 2、如果存在，就根据userId，查询用户登录信息是否有效，如果有效，就返回用户信息
         * 3、如果不存在，就创建用户基础信息
         */

        Map out = new HashMap();

        Map qIn = new HashMap();
        qIn.put("deviceCode", deviceCode);
        ArrayList<UserView> userViews = iUserMiddle.listUserLoginLog(qIn);
        String userId = null;
        if (userViews.size() > 0) {
            /**
             * 已经使用过了，查询是否需要登录
             */
            UserView userView = userViews.get(0);
            qIn = new HashMap();
            qIn.put("userId", userView.getUserId());
            UserView userLogin = iUserMiddle.getUserLogin(qIn, true);
            if (userLogin == null) {
                //从未登录过，先登录再说吧
                UserLogin userLogin1 = new UserLogin();
                userLogin1.setUserId(GogoTools.UUID32());
                userLogin1.setToken(GogoTools.UUID32());
                userLogin1.setTokenTime(new Date());
                iUserMiddle.createUserLogin(userLogin1);

                userId = userLogin1.getUserId();
                out.put("token", userLogin1.getToken());
            } else {
                /**
                 * 有登录信息
                 * 检查登录有效性
                 * todo
                 */
                userId = userLogin.getUserId();
                out.put("token", userLogin.getToken());
            }
        } else {
            /**
             * 新设备登录
             * 创建userBase
             * 创建UserLogin
             */
            UserBase userBase = new UserBase();
            userBase.setUserId(GogoTools.UUID32());
            userBase.setCreateTime(new Date());
            iUserMiddle.createUserBase(userBase);

            UserLogin userLogin = new UserLogin();
            userLogin.setUserId(userBase.getUserId());
            userLogin.setToken(GogoTools.UUID32());
            userLogin.setTokenTime(new Date());
            iUserMiddle.createUserLogin(userLogin);

            userId = userBase.getUserId();
            out.put("token", userLogin.getToken());
        }

        /**
         * 记录userLoginLog
         */
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUserId(userId);
        userLoginLog.setDeviceCode(deviceCode);
        userLoginLog.setDeviceName(deviceName);
        userLoginLog.setLoginTime(new Date());
        iUserMiddle.createUserLoginLog(userLoginLog);

        return out;
    }

    @Override
    public Map signInByToken(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUserLogin(qIn, false);


        Map user = new HashMap();
        user.put("token", userView.getToken());

        Map out = new HashMap();
        out.put("user", user);

        /**
         * 记录userLoginLog
         */
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUserId(userView.getUserId());
        userLoginLog.setLoginTime(new Date());
        iUserMiddle.createUserLoginLog(userLoginLog);

        return out;
    }

    private void verifyToken() throws Exception {
        /**
         * 校验token的有效期
         */

    }
}
