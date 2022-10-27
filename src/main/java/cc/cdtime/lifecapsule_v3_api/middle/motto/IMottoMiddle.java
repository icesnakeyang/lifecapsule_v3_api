package cc.cdtime.lifecapsule_v3_api.middle.motto;

import cc.cdtime.lifecapsule_v3_api.meta.motto.entity.Motto;
import cc.cdtime.lifecapsule_v3_api.meta.motto.entity.MottoLog;
import cc.cdtime.lifecapsule_v3_api.meta.motto.entity.MottoView;

import java.util.ArrayList;
import java.util.Map;

public interface IMottoMiddle {
    /**
     * 创建一条motto
     *
     * @param motto
     */
    void createMotto(Motto motto) throws Exception;

    /**
     * 查询一条motto
     *
     * @param qIn random
     *            status
     *            mottoId
     * @return
     */
    MottoView getMotto(Map qIn, Boolean returnNull) throws Exception;

    /**
     * 创建一个motto日志
     *
     * @param mottoLog
     */
    void createMottoLog(MottoLog mottoLog) throws Exception;

    /**
     * 修改motto
     *
     * @param qIn likes
     *            views
     *            status
     *            mottoId
     */
    void updateMotto(Map qIn) throws Exception;

    /**
     * 读取motto日志列表
     *
     * @param qIn userId
     *            mottoId
     * @return
     */
    ArrayList<MottoView> listMotto(Map qIn) throws Exception;

    Integer totalMotto(Map qIn) throws Exception;
}
