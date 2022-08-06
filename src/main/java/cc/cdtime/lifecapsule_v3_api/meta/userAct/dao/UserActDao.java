package cc.cdtime.lifecapsule_v3_api.meta.userAct.dao;

import cc.cdtime.lifecapsule_v3_api.meta.userAct.entity.UserAct;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserActDao {
    /**
     * 记录用户行为日志
     *
     * @param userAct
     */
    void createUserAct(UserAct userAct);

    Integer totalUserAct(Map qIn);
}
