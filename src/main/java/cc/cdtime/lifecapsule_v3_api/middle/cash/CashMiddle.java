package cc.cdtime.lifecapsule_v3_api.middle.cash;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashAccount;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashCategory;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashLedger;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashView;
import cc.cdtime.lifecapsule_v3_api.meta.cash.service.ICashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class CashMiddle implements ICashMiddle {
    private final ICashService iCashService;

    public CashMiddle(ICashService iCashService) {
        this.iCashService = iCashService;
    }

    @Override
    public void createCashLedger(CashLedger cashLedger) throws Exception {
        iCashService.createCashLedger(cashLedger);
    }

    @Override
    public void createCashAccount(CashAccount cashAccount) throws Exception {
        iCashService.createCashAccount(cashAccount);
    }

    @Override
    public CashView getCashAccount(Map qIn, Boolean returnNull, String userId) throws Exception {
        CashView cashView = iCashService.getCashAccount(qIn);
        if (cashView == null) {
            if (returnNull) {
                return null;
            }
            //该用户没有现金账户
            throw new Exception("10067");
        }
        if (userId != null) {
            if (!cashView.getUserId().equals(userId)) {
                //不是自己的账户
                throw new Exception("10074");
            }
        }
        return cashView;
    }

    @Override
    public void updateCashAccount(Map qIn) throws Exception {
        iCashService.updateCashAccount(qIn);
    }

    @Override
    public void createCashCategory(CashCategory cashCategory) throws Exception {
        iCashService.createCashCategory(cashCategory);
    }

    @Override
    public ArrayList<CashView> listCashCategory(Map qIn) throws Exception {
        ArrayList<CashView> cashViews = iCashService.listCashCategory(qIn);
        return cashViews;
    }

    @Override
    public void updateCashCategory(Map qIn) throws Exception {
        iCashService.updateCashCategory(qIn);
    }

    @Override
    public void deleteCashCategory(String cashCategoryId) throws Exception {
        iCashService.deleteCashCategory(cashCategoryId);
    }

    @Override
    public CashView getCashCategory(Map qIn, Boolean returnNull, String userId) throws Exception {
        CashView cashView = iCashService.getCashCategory(qIn);
        if (cashView == null) {
            if (returnNull) {
                return null;
            }
            //没有查询到该现金账户分类
            throw new Exception("10069");
        }
        if (userId != null) {
            if (!cashView.getUserId().equals(userId)) {
                //不是自己创建的分类
                throw new Exception("10071");
            }
        }
        return cashView;
    }

    @Override
    public Map sumAccountBalance(Map qIn) throws Exception {
        Map out = iCashService.sumAccountBalance(qIn);
        return out;
    }

    @Override
    public ArrayList<CashView> listCashLedger(Map qIn) throws Exception {
        ArrayList<CashView> cashViews = iCashService.listCashLedger(qIn);
        return cashViews;
    }

    @Override
    public Integer totalCashLedger(Map qIn) throws Exception {
        Integer total = iCashService.totalCashLedger(qIn);
        return total;
    }

    @Override
    public CashView getCashLedger(String cashLedgerId, Boolean returnNull, String userId) throws Exception {
        CashView cashView = iCashService.getCashLedger((cashLedgerId));
        if (cashView == null) {
            if (returnNull) {
                return null;
            }
            /**
             * 没有查询到该笔现金流水
             */
            throw new Exception("10072");
        }
        if (userId != null) {
            if (!cashView.getUserId().equals(userId)) {
                //不是自己的现金流水账
                throw new Exception("10073");
            }
        }
        return cashView;
    }

    @Override
    public void updateCashLedger(Map qIn) throws Exception {
        iCashService.updateCashLedger(qIn);
    }

    @Override
    public CashView initCashAccount(String userId) throws Exception {
        CashAccount cashAccount = new CashAccount();
        cashAccount.setCashAccountId(GogoTools.UUID32());
        cashAccount.setBalance(0.0);
        cashAccount.setAmountOut(0.0);
        cashAccount.setAmountIn(0.0);
        cashAccount.setUserId(userId);
        createCashAccount(cashAccount);

        //创建一个默认现金账户分类
        CashCategory cashCategory = new CashCategory();
        cashCategory.setCashCategoryName(ESTags.DEFAULT.toString());
        cashCategory.setCashCategoryId(GogoTools.UUID32());
        cashCategory.setUserId(userId);
        cashCategory.setStatus(ESTags.DEFAULT.toString());
        createCashCategory(cashCategory);

        CashView cashView = new CashView();
        cashView.setCashAccountId(cashAccount.getCashAccountId());
        cashView.setCashCategoryId(cashCategory.getCashCategoryId());
        cashView.setCashCategoryName(cashCategory.getCashCategoryName());
        cashView.setAmountIn(cashAccount.getAmountIn());
        cashView.setAmountOut(cashAccount.getAmountOut());
        cashView.setBalance(cashAccount.getBalance());

        return cashView;
    }
}
