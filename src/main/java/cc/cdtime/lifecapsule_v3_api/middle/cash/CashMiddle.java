package cc.cdtime.lifecapsule_v3_api.middle.cash;

import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashAccount;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashCategory;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashLedger;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashView;
import cc.cdtime.lifecapsule_v3_api.meta.cash.service.ICashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

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
    public CashView getCashAccount(Map qIn, Boolean returnNull) throws Exception {
        CashView cashView = iCashService.getCashAccount(qIn);
        if (cashView == null) {
            if (returnNull) {
                return null;
            }
            //该用户没有现金账户
            throw new Exception("10067");
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
    public CashView getCashCategory(Map qIn, Boolean returnNull) throws Exception {
        CashView cashView = iCashService.getCashCategory(qIn);
        if(cashView==null){
            if(returnNull){
                return null;
            }
            //没有查询到该现金账户分类
            throw new Exception("10069");
        }
        return cashView;
    }
}
