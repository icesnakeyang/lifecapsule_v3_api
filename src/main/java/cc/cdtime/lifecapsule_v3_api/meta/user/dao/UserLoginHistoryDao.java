package cc.cdtime.lifecapsule_v3_api.meta.user.dao;

import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserLoginHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoginHistoryDao {
    void createUserLoginHistory(UserLoginHistory userLoginHistory);
}
