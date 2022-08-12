package cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.service;

import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLog;
import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLogView;

import java.util.ArrayList;
import java.util.Map;

public interface INoteSendService {
    /**
     * 创建一个笔记发送记录
     *
     * @param noteSendLog
     */
    void createNoteSendLog(NoteSendLog noteSendLog) throws Exception;

    /**
     * 读取主动发送笔记记录列表
     *
     * @param qIn noteId
     *            sendUserId
     *            receiveUserId
     * @return
     */
    ArrayList<NoteSendLogView> listNoteSendLog(Map qIn) throws Exception;

    /**
     * 统计笔记发送日志
     *
     * @param qIn noteId
     *            sendUserId
     *            unread
     *            receiveUserId
     * @return
     */
    Integer totalNoteSendLog(Map qIn) throws Exception;

    NoteSendLogView getNoteSendLog(String sendLogId) throws Exception;

    /**
     * 修改笔记发送日志
     *
     * @param qIn readTime
     *            sendLogId
     */
    void updateNoteSendLog(Map qIn) throws Exception;

    /**
     * 物理删除一个发送的笔记
     *
     * @param sendLogId
     */
    void deleteNoteSendLog(String sendLogId) throws Exception;
}
