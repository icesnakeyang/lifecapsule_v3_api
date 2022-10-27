package cc.cdtime.lifecapsule_v3_api.admin.motto;

import java.util.Map;

public interface IAdminMottoBService {
    /**
     * @param in
     * @return
     * @throws Exception
     */
    Map listMotto(Map in) throws Exception;

    Map getMotto(Map in) throws Exception;

    void setMottoStop(Map in) throws Exception;

    void setMottoActive(Map in) throws Exception;
}
