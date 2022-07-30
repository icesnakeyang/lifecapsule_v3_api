package cc.cdtime.lifecapsule_v3_api.business.adminUser;

import java.util.Map;

public interface IAdminLoginBService {
    void createAdmin(Map in) throws Exception;

    Map adminLogin(Map in) throws Exception;
}
