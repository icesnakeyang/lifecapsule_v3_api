package cc.cdtime.lifecapsule_v3_api.business.cash;

import java.util.Map;

public interface ICashBService {
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

    Map getMyCashCategory(Map in)throws Exception;

    void updateCashCategory(Map in) throws Exception;

    Map getCashLedger(Map in) throws Exception;

    void updateMyCashLedger(Map in) throws Exception;

    Map statisticCashLedgerMonth(Map in) throws Exception;
}
