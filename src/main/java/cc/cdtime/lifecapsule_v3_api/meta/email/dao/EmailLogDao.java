package cc.cdtime.lifecapsule_v3_api.meta.email.dao;

import cc.cdtime.lifecapsule_v3_api.meta.email.entity.EmailLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailLogDao {
    void createEmailLog(EmailLog emailLog);
    EmailLog getEmailLog(String email);
    void deleteEmailLog(String email);
}
