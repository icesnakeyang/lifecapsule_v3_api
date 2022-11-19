package cc.cdtime.lifecapsule_v3_api.framework.common.email;

import java.util.Map;

public interface IEmailToolService {
    void sendMail(Map qIn) throws Exception;
}
