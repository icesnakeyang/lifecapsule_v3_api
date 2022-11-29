package cc.cdtime.lifecapsule_v3_api.business.userAccount;

import cc.cdtime.lifecapsule_v3_api.business.common.IUserComBService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.EmailLog;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmail;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmailView;
import cc.cdtime.lifecapsule_v3_api.meta.timer.entity.TimerView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.*;
import cc.cdtime.lifecapsule_v3_api.middle.email.IEmailMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.timer.ITimerMiddle;
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
    private final ITimerMiddle iTimerMiddle;
    private final IEmailMiddle iEmailMiddle;
    private final IUserComBService iUserComBService;

    public UserAccountBService(IUserMiddle iUserMiddle,
                               ITimerMiddle iTimerMiddle,
                               IEmailMiddle iEmailMiddle,
                               IUserComBService iUserComBService) {
        this.iUserMiddle = iUserMiddle;
        this.iTimerMiddle = iTimerMiddle;
        this.iEmailMiddle = iEmailMiddle;
        this.iUserComBService = iUserComBService;
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
        String frontEnd = (String) in.get("frontEnd");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * 获取用户的个人信息
         */
        Map user = new HashMap();
        user.put("token", userView.getToken());
        if (userView.getPhone() != null) {
            user.put("phone", userView.getPhone());
        }
        if (userView.getEmail() != null) {
            user.put("email", userView.getEmail());
            user.put("userStatus", ESTags.USER_NORMAL);
        } else {
            user.put("userStatus", ESTags.USER_GUEST);
        }
        if (userView.getLoginName() != null) {
            user.put("loginName", userView.getLoginName());
        }
        if (userView.getNickname() != null) {
            user.put("nickname", userView.getNickname());

        }

        /**
         * 用户账户认证状态
         * todo
         */

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
        /**
         * 记录userLoginLog
         */
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUserId(userView.getUserId());
        userLoginLog.setLoginTime(new Date());
        userLoginLog.setDeviceName(deviceName);
        userLoginLog.setDeviceCode(deviceCode);
        userLoginLog.setFrontEnd(frontEnd);
        iUserMiddle.createUserLoginLog(userLoginLog);

        return out;
    }

    @Override
    public Map getProfile(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Map user = new HashMap();
        user.put("loginName", userView.getLoginName());
        user.put("timerPrimary", userView.getTimerPrimary());
        user.put("registerTime", userView.getCreateTime());
        user.put("nickname", userView.getNickname());
        user.put("email", userView.getEmail());

        /**
         * 用户状态为USER_GUEST
         */
        if (userView.getEmail() == null) {
            user.put("userStatus", ESTags.USER_GUEST);
        } else {
            user.put("userStatus", ESTags.USER_NORMAL);
        }

        Map out = new HashMap();
        out.put("userInfo", user);
        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveProfile(Map in) throws Exception {
        String token = in.get("token").toString();
        String nickname = (String) in.get("nickname");
        String email = (String) in.get("email");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        int cc = 0;
        qIn = new HashMap();
        if (nickname != null) {
            if (userView.getNickname() == null) {
                cc++;
                qIn.put("nickname", nickname);
            } else {
                if (!nickname.equals(userView.getNickname())) {
                    cc++;
                    qIn.put("nickname", nickname);
                }
            }
        }
        if (email != null) {
            qIn = new HashMap();
            qIn.put("email", email);
            UserEmailView userEmailView = iUserMiddle.getUserEmail(qIn, true, null);
            if (userEmailView == null) {
                UserEmail userEmail = new UserEmail();
                userEmail.setEmail(email);
                userEmail.setUserId(userView.getUserId());
                userEmail.setEmailId(GogoTools.UUID32());
                userEmail.setCreateTime(new Date());
                if (userView.getEmail() == null) {
                    userEmail.setStatus(ESTags.DEFAULT.toString());
                } else {
                    userEmail.setStatus(ESTags.ACTIVE.toString());
                }
                iUserMiddle.createUserEmail(userEmail);
            } else {
                //email已被注册了
                throw new Exception("10041");
            }
        }
        if (cc > 0) {
            qIn.put("userId", userView.getUserId());
            iUserMiddle.updateUserBase(qIn);
        }
    }

    @Override
    public Map signInByNothing(Map in) throws Exception {
        Map out = registerUser(in);
        return out;
    }

    @Override
    public Map listEmail(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);
        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        ArrayList<UserEmailView> userEmailViews = iUserMiddle.listEmail(qIn);
        Map out = new HashMap();
        out.put("userEmailViews", userEmailViews);
        return out;
    }

    @Override
    public Map getEmail(Map in) throws Exception {
        String token = in.get("token").toString();
        String emailId = (String) in.get("emailId");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("emailId", emailId);
        UserEmailView userEmailView = iUserMiddle.getUserEmail(qIn, false, userView.getUserId());

        Map out = new HashMap();
        out.put("email", userEmailView);

        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setDefaultEmail(Map in) throws Exception {
        String token = in.get("token").toString();
        String emailId = in.get("emailId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("emailId", emailId);
        UserEmailView userEmailView = iUserMiddle.getUserEmail(qIn, false, userView.getUserId());

        if (userEmailView.getStatus().equals(ESTags.DEFAULT.toString())) {
            //已经时默认email了，直接返回
            return;
        }
        /**
         * 1、设置当前email为默认，
         * 2、把其余的email，都设置为ACTIVE
         */
        qIn = new HashMap();
        qIn.put("status", ESTags.ACTIVE.toString());
        qIn.put("userId", userView.getUserId());
        iUserMiddle.setEmailStatus(qIn);

        qIn = new HashMap();
        qIn.put("emailId", emailId);
        qIn.put("status", ESTags.DEFAULT);
        iUserMiddle.updateUserEmail(qIn);
    }

    /**
     * 用户绑定email
     * email通过验证后，绑定给用户账号
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map bindEmail(Map in) throws Exception {
        String token = in.get("token").toString();
        String email = in.get("email").toString();
        String emailCode = in.get("emailCode").toString();

        /**
         * 获取当前登录的用户
         */
        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        if (userView.getEmail() != null) {
            //已经绑定email了
            throw new Exception("10046");
        }

        /**
         * 查询email记录
         */
        qIn = new HashMap();
        qIn.put("email", email);
        UserEmailView userEmailView = iUserMiddle.getUserEmail(qIn, true, null);
        if (userEmailView != null) {
            //该email已经被绑定了
            throw new Exception("10066");
        }

        /**
         * 检查email的认证情况
         */
        EmailLog emailLog = iEmailMiddle.getEmailLog(email, true);
        if (emailLog == null) {
            //email未认证成功
            throw new Exception("10063");
        }
        if (!emailLog.getCode().equals(emailCode)) {
            //邮箱验证码错误
            throw new Exception("10063");
        }

        /**
         * 绑定email
         */
        Map out = new HashMap();

        /**
         * email还没有被绑定，直接添加到email表
         */
        UserEmail userEmail = new UserEmail();
        userEmail.setEmail(email);
        userEmail.setEmailId(GogoTools.UUID32());
        userEmail.setUserId(userView.getUserId());
        userEmail.setStatus(ESTags.DEFAULT.toString());
        userEmail.setCreateTime(new Date());
        iUserMiddle.createUserEmail(userEmail);
        out.put("token", token);

        /**
         * 删除验证码
         */
        iEmailMiddle.deleteEmailLog(email);

        return out;
    }

    /**
     * 通过email登录
     * 需要通过email code验证
     * 如果email没有使用过，就返回错误
     * 如果email使用过了，就返回该email绑定的账号token
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map signByEmail(Map in) throws Exception {
        String email = in.get("email").toString();
        String emailCode = in.get("emailCode").toString();
        String token = (String) in.get("token");

        /**
         * 读取当前的token的userId，保存到user_login_history表里
         */
        Map qIn = new HashMap();
        String oldToken = token;
        String oldUserId = null;
        String newUserId = GogoTools.UUID32();
        String newToken = GogoTools.UUID32();
        if (token != null) {
            qIn.put("token", token);
            UserView userView = iUserMiddle.getUserTiny(qIn, true, false);
            if (userView != null) {
                oldUserId = userView.getUserId();
            }
        }

        /**
         * 检查email的认证情况
         */
        EmailLog emailLog = iEmailMiddle.getEmailLog(email, true);

        if (emailLog == null) {
            if (!emailCode.equals("xxxxxx")) {
                //email未认证成功
                throw new Exception("10045");
            }
        }
        if (emailLog != null) {
            if (!emailLog.getCode().equals(emailCode)) {
                //邮箱验证码错误
                throw new Exception("10063");
            }
        }

        /**
         * 绑定email
         */
        /**
         * 查询email
         */
        qIn = new HashMap();
        qIn.put("email", email);
        UserEmailView userEmailView = iUserMiddle.getUserEmail(qIn, true, null);
        Map out = new HashMap();
        if (userEmailView == null) {
            /**
             * email没有使用过，创建一个新用户
             */
            Map regOutMap = registerUser(in);
            newUserId = regOutMap.get("userId").toString();
            newToken = regOutMap.get("token").toString();

            //创建用户email
            UserEmail userEmail = new UserEmail();
            userEmail.setEmail(email);
            userEmail.setEmailId(GogoTools.UUID32());
            userEmail.setUserId(newUserId);
            userEmail.setStatus(ESTags.DEFAULT.toString());
            userEmail.setCreateTime(new Date());
            iUserMiddle.createUserEmail(userEmail);
        } else {
            /**
             * 如果email已经被绑定了，就切换到该email账号
             */
            /**
             * todo
             * 为了账户安全性，这里考虑再增加一些验证操作
             */
            newUserId = userEmailView.getUserId();
            out.put("token", loginUser(newUserId, in));
        }

        /**
         * 保存登录历史记录
         */
        UserLoginHistory userLoginHistory = new UserLoginHistory();
        userLoginHistory.setCreateTime(new Date());
        userLoginHistory.setNewUserId(newUserId);
        userLoginHistory.setOldUserId(oldUserId);
        userLoginHistory.setNewToken(newToken);
        userLoginHistory.setOldToken(oldToken);
        iUserMiddle.createUserLoginHistory(userLoginHistory);

        /**
         * 删除验证码
         */
        iEmailMiddle.deleteEmailLog(email);

        return out;
    }

    @Override
    public Map getUserLoginByToken(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUserLogin(qIn, false);

        Map out = new HashMap();
        if (userView.getOpenPassword() != null) {
            out.put("openPassword", true);
        } else {
            out.put("openPassword", false);
        }

        return out;
    }

    private String loginUser(String userId, Map params) throws Exception {
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
            /**
             * 1、查看是否7天内
             * 2、查看是否常用设备登录
             */
            Long date1 = userView.getTokenTime().getTime();
            Long date2 = new Date().getTime();
            Long spanDays = (date2 - date1) / 1000 / 24 / 3600;

            if (spanDays > 7) {
                qIn = new HashMap();
                qIn.put("token", token);
                qIn.put("tokenTime", new Date());
                qIn.put("userId", userId);
                iUserMiddle.updateUserLogin(qIn);
            } else {
                token = userView.getToken();
            }
        }

        /**
         * 记录userLoginLog
         */
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUserId(userId);
        userLoginLog.setLoginTime(new Date());
        if (params != null) {
            String deviceName = (String) params.get("deviceName");
            String deviceCode = (String) params.get("deviceCode");
            String frontEnd = (String) params.get("frontEnd");
            userLoginLog.setDeviceName(deviceName);
            userLoginLog.setDeviceCode(deviceCode);
            userLoginLog.setFrontEnd(frontEnd);
        }
        iUserMiddle.createUserLoginLog(userLoginLog);

        return token;
    }

    /**
     * 创建一个新用户
     *
     * @param in
     * @throws Exception
     */
    private Map registerUser(Map in) throws Exception {
        String frontEnd = in.get("frontEnd").toString();
        /**
         * 直接生成一个临时账号
         */

        String userId = GogoTools.UUID32();
        String token = GogoTools.UUID32();

        /**
         * 创建userBase表
         */
        UserBase userBase = new UserBase();
        userBase.setUserId(userId);
        userBase.setCreateTime(new Date());
        //生成一个随机的用户昵称
        userBase.setNickname(GogoTools.generateString(8));
        iUserMiddle.createUserBase(userBase);

        /**
         * 创建用户登录信息
         */
        UserLogin userLogin = new UserLogin();
        userLogin.setUserId(userId);
        userLogin.setToken(token);
        userLogin.setTokenTime(new Date());
        iUserMiddle.createUserLogin(userLogin);

        /**
         * 创建一个主计时器
         */
        Map map = iTimerMiddle.createUserTimer(userId);


        /**
         * 创建用户登录日志
         */
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUserId(userId);
        userLoginLog.setLoginTime(new Date());
        userLoginLog.setFrontEnd(frontEnd);
        iUserMiddle.createUserLoginLog(userLoginLog);

        /**
         * 返回临时用户信息
         */
        Map out = new HashMap();
        out.put("token", token);
        out.put("nickname", userBase.getNickname());
        out.put("timerPrimary", map.get("timerTime"));
        out.put("userId", userId);

        /**
         * 用户状态为USER_GUEST
         */
        out.put("userStatus", ESTags.USER_GUEST);
        return out;
    }
}
