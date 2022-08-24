package cc.cdtime.lifecapsule_v3_api.app.user.business;

import cc.cdtime.lifecapsule_v3_api.business.userAccount.IUserAccountBService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.timer.entity.TimerView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.*;
import cc.cdtime.lifecapsule_v3_api.middle.timer.ITimerMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AppUserBService implements IAppUserBService {
    private final IUserMiddle iUserMiddle;
    private final IUserAccountBService iUserAccountBService;

    public AppUserBService(IUserMiddle iUserMiddle,
                           IUserAccountBService iUserAccountBService) {
        this.iUserMiddle = iUserMiddle;
        this.iUserAccountBService = iUserAccountBService;
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
        userLoginLog.setFrontEnd(ESTags.MOBILE_CLIENT.toString());
        iUserMiddle.createUserLoginLog(userLoginLog);

        return out;
    }

    @Override
    public Map signInByToken(Map in) throws Exception {
        in.put("frontEnd", ESTags.MOBILE_CLIENT.toString());
        Map out = iUserAccountBService.signByToken(in);
        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map loginByLoginName(Map in) throws Exception {
        in.put("frontEnd", ESTags.MOBILE_CLIENT.toString());
        Map out = iUserAccountBService.loginByLoginName(in);
        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map registerByLoginName(Map in) throws Exception {
        in.put("frontEnd", ESTags.MOBILE_CLIENT.toString());
        Map out = iUserAccountBService.registerByLoginName(in);
        return out;
    }

    /**
     * App用户查询自己的个人信息
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map getMyProfile(Map in) throws Exception {
        Map out = iUserAccountBService.getProfile(in);
        return out;
    }

    @Override
    public void saveMyProfile(Map in) throws Exception {
        iUserAccountBService.saveProfile(in);
    }

    @Override
    public Map signInByNothing(Map in) throws Exception {
        in.put("frontEnd", ESTags.MOBILE_CLIENT.toString());
        Map out = iUserAccountBService.signInByNothing(in);
        return out;
    }

    @Override
    public Map listMyEmail(Map in) throws Exception {
        Map out = iUserAccountBService.listEmail(in);
        return out;
    }

    @Override
    public Map getMyEmail(Map in) throws Exception {
        Map out = iUserAccountBService.getEmail(in);
        return out;
    }

    /**
     * App用户设置一个email为默认
     *
     * @param in
     * @throws Exception
     */
    @Override
    public void setDefaultEmail(Map in) throws Exception {
        iUserAccountBService.setDefaultEmail(in);
    }

    /**
     * 用户绑定email
     * email通过验证后，绑定给用户账号
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map bindEmail(Map in) throws Exception {
        in.put("frontEnd", ESTags.MOBILE_CLIENT.toString());
        Map out = iUserAccountBService.bindEmail(in);
        return out;
    }

    /**
     * App用户通过token获取用户登录信息，查看是否需要口令登录
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map getUserLoginByToken(Map in) throws Exception {
        Map out = iUserAccountBService.getUserLoginByToken(in);
        return out;
    }

    private void verifyToken() throws Exception {
        /**
         * 校验token的有效期
         */

    }

    private String loginUser(String userId) throws Exception {
        Map qIn = new HashMap();
        qIn.put("userId", userId);
        UserView userView = iUserMiddle.getUserLogin(qIn, true);
        String token = GogoTools.UUID32();
        if (userView == null) {
            //第一次登录，创建userLogin记录
            UserLogin userLogin = new UserLogin();
            userLogin.setUserId(userId);
            userLogin.setToken(token);
            userLogin.setTokenTime(new Date());
            iUserMiddle.createUserLogin(userLogin);
        } else {
            //非第一次登录，修改登录记录
            qIn = new HashMap();
            qIn.put("token", token);
            qIn.put("tokenTime", new Date());
            qIn.put("userId", userId);
            iUserMiddle.updateUserLogin(qIn);
        }

        /**
         * 记录userLoginLog
         */
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUserId(userId);
        userLoginLog.setLoginTime(new Date());
        iUserMiddle.createUserLoginLog(userLoginLog);

        return token;
    }
}
