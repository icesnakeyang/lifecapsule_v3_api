package cc.cdtime.lifecapsule_v3_api.business.email;

import cc.cdtime.lifecapsule_v3_api.framework.common.email.IEmailToolService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.EmailLog;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmailView;
import cc.cdtime.lifecapsule_v3_api.middle.email.IEmailMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailBService implements IEmailBService {
    private final IEmailToolService iEmailToolService;
    private final IEmailMiddle iEmailMiddle;
    private final IUserMiddle iUserMiddle;

    public EmailBService(IEmailToolService iEmailToolService,
                         IEmailMiddle iEmailMiddle,
                         IUserMiddle iUserMiddle) {
        this.iEmailToolService = iEmailToolService;
        this.iEmailMiddle = iEmailMiddle;
        this.iUserMiddle = iUserMiddle;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendVerifyCodeToEmail(Map in) throws Exception {
        String email = in.get("email").toString();
        String actType = in.get("actType").toString();

        /**
         * 首先在user_email表里查询email，
         * 如果未注册，
         *      如果当前为登录操作，就提醒用户直接绑定，
         *      如果当前为绑定操作，就继续
         * 如果已注册
         *      登录操作，直接登录
         *
         */
        Map qIn = new HashMap();
        qIn.put("email", email);
        UserEmailView emailView = iUserMiddle.getUserEmail(qIn, true, null);
        if (emailView == null) {
            if (actType.equals("LOGIN")) {
                throw new Exception("10053");
            }
        }
        if (actType.equals("BIND") && emailView != null) {
            /**
             * 要绑定的email已经存在了
             */
            throw new Exception("10065");
        }

        /**
         * 查询该email是否已经发送过验证码
         */
        EmailLog emailLog = iEmailMiddle.getEmailLog(email, true);
        if (emailLog != null) {
            Date now = new Date();
            long diff = now.getTime() - emailLog.getCreateTime().getTime();
            diff = diff / 1000;
            //1分钟
            if (diff < 60) {
                //离上次发送验证码不到1分钟，不能再次发送
                throw new Exception("10064");
            }
            /**
             * 重新发送，但要删除原来的验证码
             */
            iEmailMiddle.deleteEmailLog(email);
        }

        /**
         * 随机生成一个8位数字，发送给email
         */
        Long code = GogoTools.getNumber(8);

        qIn = new HashMap();
        qIn.put("mailType", ESTags.MAIL_TYPE_VALIDATE);
        qIn.put("email", email);
        qIn.put("subject", "[LifeCapsule] Please verify your email");
        qIn.put("code", code);

        try {
            iEmailToolService.sendMail(qIn);
        } catch (Exception ex) {
            System.out.println("send email failed:" + ex.getMessage());
        }

        /**
         * 保存emailcode，等待用户验证
         */
        emailLog = new EmailLog();
        emailLog.setEmail(email);
        emailLog.setCode(code.toString());
        emailLog.setCreateTime(new Date());
        iEmailMiddle.createEmailLog(emailLog);
    }
}
