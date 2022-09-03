package cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.dao;

import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLog;
import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLogView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface NoteSendDao {
    /**
     * 创建一个笔记发送记录
     *
     * @param noteSendLog
     */
    void createNoteSendLog(NoteSendLog noteSendLog);

    void createContentDetail(NoteSendLog noteSendLog);

    void createUserEncodeKey(UserEncodeKey userEncodeKey);

    /**
     * 读取主动发送笔记记录列表
     *
     * @param qIn noteId
     *            sendUserId
     *            receiveUserId
     *            triggerType
     * @return
     */
    ArrayList<NoteSendLogView> listNoteSendLog(Map qIn);

    /**
     * 统计笔记发送日志
     *
     * @param qIn noteId
     *            sendUserId
     *            unread
     *            receiveUserId
     * @return
     */
    Integer totalNoteSendLog(Map qIn);

    NoteSendLogView getNoteSendLog(String sendLogId);
    NoteSendLogView getNoteSendContent(String sendLogId);
    NoteSendLogView getNoteSendEncodeKey(String sendLogId);

    /**
     * 修改笔记发送日志
     *
     * @param qIn readTime
     *            sendLogId
     */
    void updateNoteSendLog(Map qIn);

    /**
     * 物理删除一个发送的笔记
     *
     * @param sendLogId
     */
    void deleteNoteSendLog(String sendLogId);

    /**
     * 物理删除发送笔记的内容
     *
     * @param sendLogId
     */
    void deleteNoteSendContent(String sendLogId);
}
