package cc.cdtime.lifecapsule_v3_api.meta.author.dao;

import cc.cdtime.lifecapsule_v3_api.meta.author.entity.AuthorLog;
import cc.cdtime.lifecapsule_v3_api.meta.author.entity.AuthorLogView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface AuthorLogDao {
    void createAuthorLog(AuthorLog authorLog);

    /**
     * 查询一个用户信息日志
     *
     * @param qIn authorLogId
     *            authorName
     *            userId
     *            status
     * @return
     */
    AuthorLogView getAuthorLog(Map qIn);

    /**
     * 修改用户信息日志
     *
     * @param qIn status
     *            authorLogId
     */
    void updateAuthorLog(Map qIn);
}
