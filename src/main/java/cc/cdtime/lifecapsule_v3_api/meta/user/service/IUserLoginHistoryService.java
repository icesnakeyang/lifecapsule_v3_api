package cc.cdtime.lifecapsule_v3_api.meta.user.service;

import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserLoginHistory;

public interface IUserLoginHistoryService {
    void createUserLoginHistory(UserLoginHistory userLoginHistory) throws Exception;
}
