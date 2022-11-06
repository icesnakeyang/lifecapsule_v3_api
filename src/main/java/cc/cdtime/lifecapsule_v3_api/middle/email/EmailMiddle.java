package cc.cdtime.lifecapsule_v3_api.middle.email;

import cc.cdtime.lifecapsule_v3_api.meta.email.entity.EmailLog;
import cc.cdtime.lifecapsule_v3_api.meta.email.service.IEmailLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailMiddle implements IEmailMiddle {
    private final IEmailLogService iEmailLogService;

    public EmailMiddle(IEmailLogService iEmailLogService) {
        this.iEmailLogService = iEmailLogService;
    }

    @Override
    public void createEmailLog(EmailLog emailLog) throws Exception {
        iEmailLogService.createEmailLog(emailLog);
    }

    @Override
    public EmailLog getEmailLog(String email, Boolean returnNull) throws Exception {
        EmailLog emailLog = iEmailLogService.getEmailLog(email);
        if (emailLog == null) {
            if (returnNull) {
                return null;
            }
            //没有查询到该email的验证码信息
            throw new Exception("10062");
        }
        return emailLog;
    }

    @Override
    public void deleteEmailLog(String email) throws Exception {
        iEmailLogService.deleteEmailLog(email);
    }
}
