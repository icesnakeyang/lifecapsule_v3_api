package cc.cdtime.lifecapsule_v3_api.business.email;

import java.util.Map;

public interface IEmailBService {
    void sendVerifyCodeToEmail(Map in) throws Exception;
}
