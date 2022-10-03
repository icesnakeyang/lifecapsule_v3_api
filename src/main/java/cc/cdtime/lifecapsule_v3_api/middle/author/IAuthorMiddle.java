package cc.cdtime.lifecapsule_v3_api.middle.author;

import cc.cdtime.lifecapsule_v3_api.meta.author.entity.AuthorLog;
import cc.cdtime.lifecapsule_v3_api.meta.author.entity.AuthorLogView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;

import java.util.Map;

public interface IAuthorMiddle {
    void createAuthorLog(AuthorLog authorLog) throws Exception;

    /**
     * 查询一个用户信息日志
     *
     * @param qIn userInfoLogId
     *            nickname
     *            userId
     *            status
     * @return
     * @throws Exception
     */
    AuthorLogView getAuthorLog(Map qIn) throws Exception;

    /**
     * 修改用户信息日志
     *
     * @param qIn status
     *            authorLogId
     */
    void updateAuthorLog(Map qIn) throws Exception;
}
