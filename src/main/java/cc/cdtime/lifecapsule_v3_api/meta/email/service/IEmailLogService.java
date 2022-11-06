package cc.cdtime.lifecapsule_v3_api.meta.email.service;

import cc.cdtime.lifecapsule_v3_api.meta.email.entity.EmailLog;

public interface IEmailLogService {
    void createEmailLog(EmailLog emailLog) throws Exception;
    EmailLog getEmailLog(String email) throws Exception;
    void deleteEmailLog(String email)throws Exception;
}
