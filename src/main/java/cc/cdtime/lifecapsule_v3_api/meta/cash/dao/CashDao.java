package cc.cdtime.lifecapsule_v3_api.meta.cash.dao;

import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashAccount;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashCategory;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashLedger;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface CashDao {
    /**
     * 创建一单用户现金流水记录
     *
     * @param cashLedger
     */
    void createCashLedger(CashLedger cashLedger);

    /**
     * 创建用户的现金汇总账户
     *
     * @param cashAccount
     */
    void createCashAccount(CashAccount cashAccount);

    /**
     * 读取一个用户的现金总账
     *
     * @param qIn userId
     * @return
     */
    CashView getCashAccount(Map qIn);

    /**
     * 修改用户现金总账
     *
     * @param qIn amountIn
     *            amountOut
     *            balance
     *            cashAccountId
     *            userId
     */
    void updateCashAccount(Map qIn);

    /**
     * 创建一个账户类别
     *
     * @param cashCategory
     */
    void createCashCategory(CashCategory cashCategory);

    /**
     * 查询现金账户分类列表
     *
     * @param qIn userId
     * @return
     */
    ArrayList<CashView> listCashCategory(Map qIn);

    /**
     * 修改现金账户分类
     *
     * @param qIn cashCategoryName
     *            cashCategoryId
     */
    void updateCashCategory(Map qIn);

    /**
     * 物理删除一个现金账户分类
     *
     * @param cashCategoryId
     */
    void deleteCashCategory(String cashCategoryId);

    /**
     * 查询一个现金账户分类信息
     *
     * @param qIn default
     *            userId
     *            cashCategoryId
     *            categoryName
     * @return
     */
    CashView getCashCategory(Map qIn);

    /**
     * 统计用户的账户收支总额
     *
     * @param qIn userId
     * @return
     */
    Map sumAccountBalance(Map qIn);

    /**
     * 查询用户的现金流水账列表
     *
     * @param qIn userId
     *            cashCategoryId
     *            offset
     *            size
     * @return
     */
    ArrayList<CashView> listCashLedger(Map qIn);

    /**
     * 统计用户的现金流水账总数
     *
     * @param qIn userId
     *            cashCategoryId
     * @return
     */
    Integer totalCashLedger(Map qIn);

}
