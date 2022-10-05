package cc.cdtime.lifecapsule_v3_api.app.history;

import java.util.Map;

public interface IAppHistoryBService {
    /**
     * App端查询我的历史首页需要显示的数据
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map loadHistoryHome(Map in) throws Exception;

    /**
     * App端用户回复自己的笔记
     *
     * @param in
     * @throws Exception
     */
    void replyMyNote(Map in) throws Exception;

    Map searchHistoryNote(Map in) throws Exception;
}
