package cc.cdtime.lifecapsule_v3_api.middle.user;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmail;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmailView;
import cc.cdtime.lifecapsule_v3_api.meta.timer.entity.TimerView;
import cc.cdtime.lifecapsule_v3_api.meta.timer.service.IUserTimerService;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.*;
import cc.cdtime.lifecapsule_v3_api.meta.user.service.IUserBaseService;
import cc.cdtime.lifecapsule_v3_api.meta.email.service.IUserEmailService;
import cc.cdtime.lifecapsule_v3_api.meta.user.service.IUserLoginHistoryService;
import cc.cdtime.lifecapsule_v3_api.meta.user.service.IUserLoginNameService;
import cc.cdtime.lifecapsule_v3_api.meta.user.service.IUserLoginService;
import cc.cdtime.lifecapsule_v3_api.middle.timer.ITimerMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserMiddle implements IUserMiddle {
    private final IUserBaseService iUserBaseService;
    private final IUserLoginService iUserLoginService;
    private final IUserLoginNameService iUserLoginNameService;
    private final IUserEmailService iUserEmailService;
    private final IUserTimerService iUserTimerService;
    private final ITimerMiddle iTimerMiddle;
    private final IUserLoginHistoryService iUserLoginHistoryService;

    public UserMiddle(IUserBaseService iUserBaseService,
                      IUserLoginService iUserLoginService,
                      IUserLoginNameService iUserLoginNameService,
                      IUserEmailService iUserEmailService,
                      IUserTimerService iUserTimerService,
                      ITimerMiddle iTimerMiddle,
                      IUserLoginHistoryService iUserLoginHistoryService) {
        this.iUserBaseService = iUserBaseService;
        this.iUserLoginService = iUserLoginService;
        this.iUserLoginNameService = iUserLoginNameService;
        this.iUserEmailService = iUserEmailService;
        this.iUserTimerService = iUserTimerService;
        this.iTimerMiddle = iTimerMiddle;
        this.iUserLoginHistoryService = iUserLoginHistoryService;
    }

    @Override
    public void createUserBase(UserBase userBase) throws Exception {
        iUserBaseService.createUserBase(userBase);
    }

    @Override
    public UserView getUserTiny(String userId, Boolean returnNull, Boolean isLogin) throws Exception {
        UserView userView = iUserBaseService.getUserBase(userId);
        if (userView == null) {
            if (returnNull) {
                return null;
            }
            //没有查询到用户信息
            throw new Exception("10002");
        }
        if (isLogin) {
            /**
             * 检查登录有效性
             */
            //todo
        }
        return userView;
    }

    @Override
    public void createUserLoginLog(UserLoginLog userLoginLog) throws Exception {
        iUserLoginService.createUserLoginLog(userLoginLog);
    }

    @Override
    public ArrayList<UserView> listUserLoginLog(Map qIn) throws Exception {
        ArrayList<UserView> userViews = iUserLoginService.listUserLoginLog(qIn);
        return userViews;
    }

    @Override
    public Integer totalUserLoginLog(Map qIn) throws Exception {
        Integer total = iUserLoginService.totalUserLoginLog(qIn);
        return total;
    }

    @Override
    public void createUserLogin(UserLogin userLogin) throws Exception {
        iUserLoginService.createUserLogin(userLogin);
    }

    @Override
    public void updateUserLogin(Map qIn) throws Exception {
        iUserLoginService.updateUserLogin(qIn);
    }

    @Override
    public UserView getUserLogin(Map qIn, Boolean returnNull) throws Exception {
        UserView userView = iUserLoginService.getUserLogin(qIn);
        if (userView == null) {
            if (returnNull) {
                return null;
            }
            //没有查询该用户登录信息
            throw new Exception("10003");
        }
        return userView;
    }

    @Override
    public void createUserLoginName(UserLoginName userLoginName) {
        iUserLoginNameService.createUserLoginName(userLoginName);
    }

    @Override
    public UserView getLoginName(Map qIn) {
        UserView userView = iUserLoginNameService.getLoginName(qIn);
        return userView;
    }

    /**
     * 获取一个用户的详细信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserView getUser(Map qIn, Boolean returnNull, Boolean isLogin) throws Exception {
        /**
         * 中间应用层查询一个用户信息，可以通过token，userId，email，phone等方式，只要任意一个符合条件，则返回组合的用户数据
         */
        String userId = (String) qIn.get("userId");
        String token = (String) qIn.get("token");
        String email = (String) qIn.get("email");
        String loginName = (String) qIn.get("loginName");

        /**
         * 获取用户基础信息
         */
        UserView userView = null;

        /**
         * 有token，就通过token读取用户Id
         */
        if (token != null) {
            userView = iUserLoginService.getUserLogin(qIn);
            if (userView != null && userId == null) {
                userId = userView.getUserId();
            }
        } else {
            /**
             * 如果有userId，通过userId读取用户
             */
            if (userId != null) {
                userView = iUserBaseService.getUserBase(userId);
            } else {
                /**
                 * 通过email读取用户
                 */
                if (email != null) {
                    qIn = new HashMap();
                    qIn.put("email", email);
                    UserEmailView userEmailView = iUserEmailService.getUserEmail(qIn);
                    if (userEmailView != null) {
                        userView = iUserBaseService.getUserBase(userEmailView.getUserId());
                    }
                } else {
                    /**
                     * 通过loginName读取用户
                     */
                    userView = iUserLoginNameService.getLoginName(qIn);
                }
            }
        }
        if (userView == null) {
            //没有查询到用户信息，登录信息过期
            throw new Exception("10047");
        }

        /**
         * 获取用户基本信息
         */
        UserView userViewBase = iUserBaseService.getUserBase(userView.getUserId());
        userView.setCreateTime(userViewBase.getCreateTime());
        userView.setNickname(userViewBase.getNickname());
        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        qIn.put("type", ESTags.TIMER_TYPE_PRIMARY.toString());
        //获取用户的主倒计时结束时间
        TimerView timerView = iUserTimerService.getUserTimer(qIn);
        if (timerView == null) {
            Map timer = iTimerMiddle.createUserTimer(userView.getUserId());
            Long tl = (Long) timer.get("timerTime");
            Timestamp ts = new Timestamp(tl);
            userView.setTimerPrimary(ts);
        } else {
            userView.setTimerPrimary(timerView.getTimerTime());
        }

        /**
         * 获取用户email
         */
        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        UserEmailView userViewEmail = iUserEmailService.getUserEmail(qIn);
        if (userViewEmail != null) {
            userView.setEmail(userViewEmail.getEmail());
        }
        /**
         * 是否返回空值
         */
        if (userView == null) {
            if (returnNull) {
                return null;
            }
            throw new Exception("10002");
        }
        if (isLogin) {
            /**
             * todo
             * 检查登录有效性
             */
        }
        return userView;
    }

    @Override
    public ArrayList<UserView> listUser(Map qIn) throws Exception {
        ArrayList<UserView> userViews = iUserBaseService.listUser(qIn);
        return userViews;
    }

    @Override
    public Integer totalUser(Map qIn) throws Exception {
        Integer total = iUserBaseService.totalUser(qIn);
        return total;
    }

    @Override
    public void updateUserBase(Map qIn) throws Exception {
        iUserBaseService.updateUserBase(qIn);
    }

    @Override
    public void createUserEmail(UserEmail userEmail) throws Exception {
        iUserEmailService.createUserEmail(userEmail);
    }

    @Override
    public UserEmailView getUserEmail(Map qIn, Boolean returnNull, String userId) throws Exception {
        UserEmailView userEmailView = iUserEmailService.getUserEmail(qIn);
        if (userEmailView == null) {
            if (returnNull) {
                return null;
            }
            //email不存在
            throw new Exception("10042");
        }
        if (userId != null) {
            if (!userEmailView.getUserId().equals(userId)) {
                //不是当前用户的email
                throw new Exception("10043");
            }
        }
        return userEmailView;
    }

    @Override
    public void updateUserEmail(Map qIn) throws Exception {
        iUserEmailService.updateUserEmail(qIn);
    }

    @Override
    public ArrayList<UserEmailView> listEmail(Map qIn) throws Exception {
        ArrayList<UserEmailView> UserEmailView = iUserEmailService.listEmail(qIn);
        return UserEmailView;
    }

    @Override
    public void setEmailStatus(Map qIn) throws Exception {
        iUserEmailService.setEmailStatus(qIn);
    }

    @Override
    public void createUserLoginHistory(UserLoginHistory userLoginHistory) throws Exception {
        iUserLoginHistoryService.createUserLoginHistory(userLoginHistory);
    }

    @Override
    public void updateLoginName(Map qIn) throws Exception {
        iUserLoginNameService.updateLoginName(qIn);
    }
}
