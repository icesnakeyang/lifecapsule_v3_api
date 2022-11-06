package cc.cdtime.lifecapsule_v3_api.meta.email.service;

import cc.cdtime.lifecapsule_v3_api.meta.email.dao.EmailLogDao;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.EmailLog;
import org.springframework.stereotype.Service;

@Service
public class EmailLogService implements IEmailLogService {
    private final EmailLogDao emailLogDao;

    public EmailLogService(EmailLogDao emailLogDao) {
        this.emailLogDao = emailLogDao;
    }

    @Override
    public void createEmailLog(EmailLog emailLog) throws Exception {
        emailLogDao.createEmailLog(emailLog);
    }

    @Override
    public EmailLog getEmailLog(String email) throws Exception {
        EmailLog emailLog = emailLogDao.getEmailLog(email);
        return emailLog;
    }

    @Override
    public void deleteEmailLog(String email) throws Exception {
        emailLogDao.deleteEmailLog(email);
    }
}
