package cc.cdtime.lifecapsule_v3_api.meta.motto.dao;

import cc.cdtime.lifecapsule_v3_api.meta.motto.entity.Motto;
import cc.cdtime.lifecapsule_v3_api.meta.motto.entity.MottoLog;
import cc.cdtime.lifecapsule_v3_api.meta.motto.entity.MottoView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface MottoDao {
    /**
     * 创建一条motto
     *
     * @param motto
     */
    void createMotto(Motto motto);

    /**
     * 查询一条motto
     *
     * @param qIn random
     *            status
     *            mottoId
     * @return
     */
    MottoView getMotto(Map qIn);

    /**
     * 创建一个motto日志
     *
     * @param mottoLog
     */
    void createMottoLog(MottoLog mottoLog);

    /**
     * 修改motto
     *
     * @param qIn likes
     *            views
     *            status
     *            mottoId
     */
    void updateMotto(Map qIn);

    /**
     * 读取motto日志列表
     *
     * @param qIn userId
     *            mottoId
     * @return
     */
    ArrayList<MottoView> listMotto(Map qIn);
    Integer totalMotto(Map qIn);
}
