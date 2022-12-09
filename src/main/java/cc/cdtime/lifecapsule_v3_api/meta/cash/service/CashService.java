package cc.cdtime.lifecapsule_v3_api.meta.cash.service;

import cc.cdtime.lifecapsule_v3_api.meta.cash.dao.CashDao;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashAccount;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashCategory;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashLedger;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class CashService implements ICashService {
    private final CashDao cashDao;

    public CashService(CashDao cashDao) {
        this.cashDao = cashDao;
    }

    @Override
    public void createCashLedger(CashLedger cashLedger) throws Exception {
        cashDao.createCashLedger(cashLedger);
    }

    @Override
    public void createCashAccount(CashAccount cashAccount) throws Exception {
        cashDao.createCashAccount(cashAccount);
    }

    @Override
    public CashView getCashAccount(Map qIn) throws Exception {
        CashView cashView = cashDao.getCashAccount(qIn);
        return cashView;
    }

    @Override
    public void updateCashAccount(Map qIn) throws Exception {
        String userId = (String) qIn.get("userId");
        String cashAccountId = (String) qIn.get("cashAccountId");

        if (userId == null && cashAccountId == null) {
            //修改用户账户信息失败
            throw new Exception("10068");
        }
        cashDao.updateCashAccount(qIn);
    }

    @Override
    public void createCashCategory(CashCategory cashCategory) throws Exception {
        cashDao.createCashCategory(cashCategory);
    }

    @Override
    public ArrayList<CashView> listCashCategory(Map qIn) throws Exception {
        ArrayList<CashView> cashViews = cashDao.listCashCategory(qIn);
        return cashViews;
    }

    @Override
    public void updateCashCategory(Map qIn) throws Exception {
        cashDao.updateCashCategory(qIn);
    }

    @Override
    public void deleteCashCategory(String cashCategoryId) throws Exception {
        cashDao.deleteCashCategory(cashCategoryId);
    }

    @Override
    public CashView getCashCategory(Map qIn) throws Exception {
        CashView cashView = cashDao.getCashCategory(qIn);
        return cashView;
    }

    @Override
    public Map sumAccountBalance(Map qIn) throws Exception {
        Map out = cashDao.sumAccountBalance(qIn);
        return out;
    }

    @Override
    public ArrayList<CashView> listCashLedger(Map qIn) throws Exception {
        ArrayList<CashView> cashViews = cashDao.listCashLedger(qIn);
        return cashViews;
    }

    @Override
    public Integer totalCashLedger(Map qIn) throws Exception {
        Integer total = cashDao.totalCashLedger(qIn);
        return total;
    }

    @Override
    public CashView getCashLedger(String cashLedgerId) throws Exception {
        CashView cashView = cashDao.getCashLedger(cashLedgerId);
        return cashView;
    }

    @Override
    public void updateCashLedger(Map qIn) throws Exception {
        cashDao.updateCashLedger(qIn);
    }

    @Override
    public ArrayList<Map> statisticByMonth(Map qIn) throws Exception {
        ArrayList<Map> list = cashDao.statisticByMonth(qIn);
        return list;
    }
}
