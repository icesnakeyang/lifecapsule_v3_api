package cc.cdtime.lifecapsule_v3_api.meta.author.service;

import cc.cdtime.lifecapsule_v3_api.meta.author.entity.AuthorLog;
import cc.cdtime.lifecapsule_v3_api.meta.author.entity.AuthorLogView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;

import java.util.ArrayList;
import java.util.Map;

public interface IAuthorLogService {
    void createAuthorLog(AuthorLog authorLog) throws Exception;

    /**
     * 查询一个用户信息日志
     *
     * @param qIn userInfoLogId
     *            nickname
     *            userId
     *            status
     * @return
     */
    AuthorLogView getAuthorLog(Map qIn) throws Exception;

    /**
     * 读取我的笔名列表
     *
     * @param qIn userId
     * @return
     */
    ArrayList<AuthorLogView> listAuthorLog(Map qIn) throws Exception;

    /**
     * 修改用户信息日志
     *
     * @param qIn status
     *            authorLogId
     */
    void updateAuthorLog(Map qIn) throws Exception;
}
