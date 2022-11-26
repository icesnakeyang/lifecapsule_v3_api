package cc.cdtime.lifecapsule_v3_api.meta.cash.service;

import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashAccount;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashCategory;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashLedger;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashView;

import java.util.ArrayList;
import java.util.Map;

public interface ICashService {
    /**
     * 创建一单用户现金流水记录
     *
     * @param cashLedger
     */
    void createCashLedger(CashLedger cashLedger) throws Exception;

    /**
     * 创建用户的现金汇总账户
     *
     * @param cashAccount
     */
    void createCashAccount(CashAccount cashAccount) throws Exception;

    /**
     * 读取一个用户的现金总账
     *
     * @param qIn userId
     * @return
     */
    CashView getCashAccount(Map qIn) throws Exception;

    /**
     * 修改用户现金总账
     *
     * @param qIn amountIn
     *            amountOut
     *            balance
     *            cashAccountId
     *            userId
     */
    void updateCashAccount(Map qIn) throws Exception;

    /**
     * 创建一个账户类别
     *
     * @param cashCategory
     */
    void createCashCategory(CashCategory cashCategory) throws Exception;

    /**
     * 查询现金账户分类列表
     *
     * @param qIn userId
     * @return
     */
    ArrayList<CashView> listCashCategory(Map qIn) throws Exception;

    /**
     * 修改现金账户分类
     *
     * @param qIn cashCategoryName
     *            remark
     *            cashCategoryId
     */
    void updateCashCategory(Map qIn) throws Exception;

    /**
     * 物理删除一个现金账户分类
     *
     * @param cashCategoryId
     */
    void deleteCashCategory(String cashCategoryId) throws Exception;

    /**
     * 查询一个现金账户分类信息
     *
     * @param qIn default
     *            userId
     *            cashCategoryId
     *            categoryName
     * @return
     */
    CashView getCashCategory(Map qIn) throws Exception;

    /**
     * 统计用户的账户收支总额
     *
     * @param qIn userId
     * @return
     */
    Map sumAccountBalance(Map qIn) throws Exception;

    /**
     * 查询用户的现金流水账列表
     *
     * @param qIn userId
     *            cashCategoryId
     *            offset
     *            size
     * @return
     */
    ArrayList<CashView> listCashLedger(Map qIn) throws Exception;

    /**
     * 统计用户的现金流水账总数
     *
     * @param qIn userId
     *            cashCategoryId
     * @return
     */
    Integer totalCashLedger(Map qIn) throws Exception;

    CashView getCashLedger(String cashLedgerId) throws Exception;

    /**
     * 修改流水账记录
     *
     * @param qIn amountIn
     *            amountOut
     *            cashCategoryId
     *            remark
     *            cashLedgerId
     */
    void updateCashLedger(Map qIn) throws Exception;
}
