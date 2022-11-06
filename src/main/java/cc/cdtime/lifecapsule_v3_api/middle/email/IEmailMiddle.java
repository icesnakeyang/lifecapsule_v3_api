package cc.cdtime.lifecapsule_v3_api.middle.email;

import cc.cdtime.lifecapsule_v3_api.meta.email.entity.EmailLog;

public interface IEmailMiddle {
    void createEmailLog(EmailLog emailLog) throws Exception;
    EmailLog getEmailLog(String email, Boolean returnNull) throws Exception;
    void deleteEmailLog(String email)throws Exception;
}
