package cc.cdtime.lifecapsule_v3_api.admin.maintenance;

import java.util.Map;

public interface IAdminMaintenanceBService {
    void restoreOldDatabase(Map in) throws Exception;

    Map moveContentToIndex(Map in) throws Exception;
}
