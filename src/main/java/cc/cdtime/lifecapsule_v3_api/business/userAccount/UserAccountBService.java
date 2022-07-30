package cc.cdtime.lifecapsule_v3_api.business.userAccount;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.category.entity.CategoryView;
import cc.cdtime.lifecapsule_v3_api.meta.timer.entity.TimerView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.*;
import cc.cdtime.lifecapsule_v3_api.middle.category.ICategoryMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.timer.ITimerMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserAccountBService implements IUserAccountBService {
    private final IUserMiddle iUserMiddle;
    private final ITimerMiddle iTimerMiddle;
    private final ICategoryMiddle iCategoryMiddle;

    public UserAccountBService(IUserMiddle iUserMiddle,
                               ITimerMiddle iTimerMiddle,
                               ICategoryMiddle iCategoryMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iTimerMiddle = iTimerMiddle;
        this.iCategoryMiddle = iCategoryMiddle;
    }

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

        /**
         * 读取用户的账号信息
         */
        out.put("loginName", loginName);

        CategoryView categoryView = iCategoryMiddle.getDefaultCategory(userView.getUserId());

        out.put("defaultCategoryId", categoryView.getCategoryId());
        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        ArrayList<CategoryView> categoryViews = iCategoryMiddle.listCategory(qIn);

        out.put("categoryList", categoryViews);

        return out;
    }

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
        out.put("loginName", loginName);

        return out;
    }

    @Override
    public Map getUserInfo(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Map user = new HashMap();
        user.put("loginName", userView.getLoginName());
//        user.put("")
        Map out = new HashMap();
        out.put("userInfo", userView);
        return out;
    }

    /**
     * 通过token登录
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map signByToken(Map in) throws Exception {
        String token = in.get("token").toString();
        String deviceName = (String) in.get("deviceName");
        String deviceCode = (String) in.get("deviceCode");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Map user = new HashMap();
        user.put("token", userView.getToken());
        if (userView.getPhone() != null) {
            user.put("userName", userView.getPhone());
        } else {
            if (userView.getEmail() != null) {
                user.put("userName", userView.getEmail());
            } else {
                if (userView.getLoginName() != null) {
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
