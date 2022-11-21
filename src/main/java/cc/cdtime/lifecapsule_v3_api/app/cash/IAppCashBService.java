package cc.cdtime.lifecapsule_v3_api.app.cash;

import java.util.Map;

public interface IAppCashBService {
    /**
     * app用户创建一个现金流水记录
     *
     * @param in
     * @throws Exception
     */
    void createLedger(Map in) throws Exception;

    Map getMyDefaultCashCategory(Map in) throws Exception;

    Map listMyCashCategory(Map in) throws Exception;

    void createMyCashCategory(Map in) throws Exception;

    Map listMyCashLedger(Map in) throws Exception;

    Map getMyCashAccount(Map in) throws Exception;

    Map getMyCashCategory(Map in) throws Exception;

    void updateMyCashCategory(Map in) throws Exception;
}
