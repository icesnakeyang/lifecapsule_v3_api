package cc.cdtime.lifecapsule_v3_api.app.antiDelay;

import java.util.Map;

public interface IAppAntiDelayBService {
    /**
     * app端用户读取自己的防拖延笔记列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listMyAntiDelayNote(Map in) throws Exception;

    /**
     * app端用户创建自己的防拖延笔记
     *
     * @param in
     * @return
     * @throws Exception
     */
    void createMyAntiDelayNote(Map in) throws Exception;

    /**
     * app端用户读取一个防拖延笔记详情
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getMyAntiDelayNote(Map in) throws Exception;

    void updateMyAntiDelayNote(Map in) throws Exception;

    void deleteMyAntiDelayNote(Map in) throws Exception;
}
