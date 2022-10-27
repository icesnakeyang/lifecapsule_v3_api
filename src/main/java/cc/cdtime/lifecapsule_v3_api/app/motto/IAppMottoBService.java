package cc.cdtime.lifecapsule_v3_api.app.motto;

import java.util.Map;

public interface IAppMottoBService {
    /**
     * 用户发布一条motto
     *
     * @param in
     * @throws Exception
     */
    void publishMotto(Map in) throws Exception;

    /**
     * 随机读取一条motto
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getMottoRandom(Map in) throws Exception;

    void likeMotto(Map in) throws Exception;
}
