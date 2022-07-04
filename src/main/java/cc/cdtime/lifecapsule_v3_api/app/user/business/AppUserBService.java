package cc.cdtime.lifecapsule_v3_api.app.user.business;

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
public class AppUserBService implements IAppUserBService{
    private final IUserMiddle iUserMiddle;
    private final ITimerMiddle iTimerMiddle;

    public AppUserBService(IUserMiddle iUserMiddle,
                           ITimerMiddle iTimerMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iTimerMiddle = iTimerMiddle;
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
        String deviceName = (String) in.get("deviceName");
        String deviceCode = (String) in.get("deviceCode");

        Map qIn = new HashMap();
        qIn.put("token", token);
//        UserView userView = iUserMiddle.getUserLogin(qIn, false);
        UserView userView = iUserMiddle.getUser(qIn, false,false);

        /**
         * todo
         * 检查用户token是否过期，是否重新登录
         */

        Map user = new HashMap();
        user.put("token", userView.getToken());
        if(userView.getPhone()!=null){
            user.put("userName", userView.getPhone());
        }else{
            if(userView.getEmail()!=null){
                user.put("userName", userView.getEmail());
            }else{
                if(userView.getLoginName()!=null){
                    user.put("userName", userView.getLoginName());
                }
            }
        }

        Map out = new HashMap();
        out.put("user", user);

        /////////////////////

        /**
         * 查询用户的主计时器到期时间
         */
        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        qIn.put("type", ESTags.TIMER_TYPE_PRIMARY);
        TimerView timerView = iTimerMiddle.getUserTimer(qIn, true);
        if (timerView == null) {
            /**
             * 没有主计时器，就创建一个
             */
            Map map = iTimerMiddle.createUserTimer(userView.getUserId());
            out.put("timerPrimary", map.get("timerTime"));
        } else {
            out.put("timerPrimary", timerView.getTimerTime().getTime());
        }
        //////////////////////


        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUserId(userView.getUserId());
        userLoginLog.setLoginTime(new Date());
        userLoginLog.setDeviceName(deviceName);
        userLoginLog.setDeviceCode(deviceCode);

            return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map loginByLoginName(Map in) throws Exception {
        String loginName = in.get("loginName").toString();
        String password = in.get("password").toString();

        Map qIn = new HashMap();
        qIn.put("loginName", loginName);
        qIn.put("password", GogoTools.encoderByMd5(password));
        UserView userView = iUserMiddle.getLoginName(qIn);
        if (userView == null) {
            //用户名或密码不正确
            throw new Exception("10005");
        }

        String token = loginUser(userView.getUserId());

        Map out = new HashMap();
        out.put("token", token);

        /////////////////////

        /**
         * 查询用户的主计时器到期时间
         */
        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        qIn.put("type", ESTags.TIMER_TYPE_PRIMARY);
        TimerView timerView = iTimerMiddle.getUserTimer(qIn, true);
        if (timerView == null) {
            /**
             * 没有主计时器，就创建一个
             */
            Map map = iTimerMiddle.createUserTimer(userView.getUserId());
            out.put("timerPrimary", map.get("timerTime"));
        } else {
            out.put("timerPrimary", timerView.getTimerTime().getTime());
        }
        //////////////////////

        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map registerByLoginName(Map in) throws Exception {
        String loginName = in.get("loginName").toString();
        String password = in.get("password").toString();

        /**
         * 1、检查loginName是否已经注册过了
         * 2、注册流程
         *      user_base表，创建用户基础信息表，生成userId
         *      user_login_name表，创建用户的用户名，密码表记录，用于用户名密码登录
         *      user_login表，登录用户，记录用户目前的登录状态，生成token
         *      user_login_log表，记录登录日志
         */

        Map qIn = new HashMap();
        qIn.put("loginName", loginName);
        UserView userView = iUserMiddle.getLoginName(qIn);
        if (userView != null) {
            //该账号已经被注册了
            throw new Exception("10006");
        }

        UserBase userBase = new UserBase();
        userBase.setUserId(GogoTools.UUID32());
        userBase.setCreateTime(new Date());
        iUserMiddle.createUserBase(userBase);

        String userId = userBase.getUserId();

        UserLoginName userLoginName = new UserLoginName();
        userLoginName.setLoginName(loginName);
        userLoginName.setUserId(userId);
        userLoginName.setPassword(GogoTools.encoderByMd5(password));
        iUserMiddle.createUserLoginName(userLoginName);

        String token = loginUser(userLoginName.getUserId());

        Map out = new HashMap();
        out.put("token", token);
        out.put("userName", loginName);

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
