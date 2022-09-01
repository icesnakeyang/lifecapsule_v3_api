package cc.cdtime.lifecapsule_v3_api.meta.admin.dao;

import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminStatisticView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface AdminStatisticDao {
    /**
     * 统计用户登录次数
     *
     * @param qIn startTime
     *            endTime
     *            size
     *            offset
     * @return
     */
    ArrayList<AdminStatisticView> totalUserLogin(Map qIn);

}
