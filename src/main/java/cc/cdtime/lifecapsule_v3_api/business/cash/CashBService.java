package cc.cdtime.lifecapsule_v3_api.business.cash;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashAccount;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashCategory;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashLedger;
import cc.cdtime.lifecapsule_v3_api.meta.cash.entity.CashView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.cash.ICashMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CashBService implements ICashBService {
    private final IUserMiddle iUserMiddle;
    private final ICashMiddle iCashMiddle;

    public CashBService(IUserMiddle iUserMiddle,
                        ICashMiddle iCashMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iCashMiddle = iCashMiddle;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createLedger(Map in) throws Exception {
        String token = in.get("token").toString();
        Double amountIn = (Double) in.get("amountIn");
        Double amountOut = (Double) in.get("amountOut");
        String cashCategoryId = (String) in.get("cashCategoryId");
        String remark = (String) in.get("remark");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * 读取用户现金总账
         */
        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        CashView cashView = iCashMiddle.getCashAccount(qIn, true);

        if (cashCategoryId == null) {
            /**
             * 没有指定现金账户分类，设置为默认分类
             */
            //读取用户的默认分类
            qIn = new HashMap();
            qIn.put("default", true);
            CashView cashCategoryDefault = iCashMiddle.getCashCategory(qIn, true, null);
            if (cashCategoryDefault == null) {
                //还没有默认分类，创建一个
                CashCategory cashCategory = new CashCategory();
                cashCategory.setCashCategoryName(ESTags.DEFAULT.toString());
                cashCategory.setCashCategoryId(GogoTools.UUID32());
                cashCategory.setUserId(userView.getUserId());
                cashCategory.setStatus(ESTags.DEFAULT.toString());
                iCashMiddle.createCashCategory(cashCategory);
                cashCategoryId = cashCategory.getCashCategoryId();
            } else {
                //已经有默认分类了
                cashCategoryId = cashCategoryDefault.getCashCategoryId();
            }
        }

        /**
         * 创建现金流水账
         */
        CashLedger cashLedger = new CashLedger();
        cashLedger.setCashLedgerId(GogoTools.UUID32());
        cashLedger.setCreateTime(new Date());
        cashLedger.setAmountIn(amountIn);
        cashLedger.setAmountOut(amountOut);
        cashLedger.setRemark(remark);
        cashLedger.setCashCategoryId(cashCategoryId);
        cashLedger.setUserId(userView.getUserId());
        iCashMiddle.createCashLedger(cashLedger);

        /**
         * 刷新现金总账
         */
        if (cashView == null) {
            //没有account，先创建
            //当前用户还没有现金账户，创建一个
            CashAccount cashAccount = new CashAccount();
            cashAccount.setCashAccountId(GogoTools.UUID32());
            cashAccount.setUserId(userView.getUserId());
            cashAccount.setAmountIn(amountIn);
            cashAccount.setAmountOut(amountOut);
            cashAccount.setBalance(amountIn - amountOut);
            iCashMiddle.createCashAccount(cashAccount);
        } else {
            //有account刷新

            qIn = new HashMap();
            qIn.put("userId", userView.getUserId());
            Map accMap = iCashMiddle.sumAccountBalance(qIn);
            Double accIn = (Double) accMap.get("totalin");
            Double accOut = (Double) accMap.get("totalout");
            if (accIn == null) {
                accIn = 0.0;
            }
            if (accOut == null) {
                accOut = 0.0;
            }
            Double balance = accIn - accOut;
            qIn.put("balance", balance);
            qIn.put("amountIn", accIn);
            qIn.put("amountOut", accOut);
            iCashMiddle.updateCashAccount(qIn);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map getMyDefaultCashCategory(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        qIn.put("default", true);
        CashView cashView = iCashMiddle.getCashCategory(qIn, false,null);

        Map out = new HashMap();

        /**
         * if default cash category is null, then create it
         */
        if (cashView == null) {
            CashCategory cashCategory = new CashCategory();
            cashCategory.setCashCategoryId(GogoTools.UUID32());
            cashCategory.setCashCategoryName(ESTags.DEFAULT.toString());
            cashCategory.setUserId(userView.getUserId());
            cashCategory.setStatus(ESTags.DEFAULT.toString());
            iCashMiddle.createCashCategory(cashCategory);
            out.put("defaultCashCategoryId", cashCategory.getCashCategoryId());
            out.put("defaultCashCategoryName", cashCategory.getCashCategoryName());
        } else {
            out.put("defaultCashCategoryId", cashView.getCashCategoryId());
            out.put("defaultCashCategoryName", cashView.getCashCategoryName());
        }

        return out;
    }

    @Override
    public Map listMyCashCategory(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        ArrayList<CashView> cashViews = iCashMiddle.listCashCategory(qIn);

        Map out = new HashMap();
        out.put("cashCategoryList", cashViews);

        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createMyCashCategory(Map in) throws Exception {
        String token = in.get("token").toString();
        String cashCategoryName = in.get("cashCategoryName").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * 查询该category是否已经存在
         */
        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        qIn.put("cashCategoryName", cashCategoryName);
        CashView cashView = iCashMiddle.getCashCategory(qIn, true, null);
        if (cashView != null) {
            //category已经存在了
            throw new Exception("10070");
        }

        CashCategory cashCategory = new CashCategory();
        cashCategory.setCashCategoryId(GogoTools.UUID32());
        cashCategory.setCashCategoryName(cashCategoryName);
        cashCategory.setStatus(ESTags.ACTIVE.toString());
        cashCategory.setUserId(userView.getUserId());
        iCashMiddle.createCashCategory(cashCategory);
    }

    @Override
    public Map listMyCashLedger(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        qIn.put("userId", userView.getUserId());
        ArrayList<CashView> cashViews = iCashMiddle.listCashLedger(qIn);

        Map out = new HashMap();
        out.put("cashLedgerList", cashViews);

        return out;
    }

    @Override
    public Map getMyCashAccount(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        CashView cashView = iCashMiddle.getCashAccount(qIn, false);

        Map out = new HashMap();
        out.put("cashAccount", cashView);

        return out;
    }

    @Override
    public Map getMyCashCategory(Map in) throws Exception {
        String token = in.get("token").toString();
        String cashCategoryId = in.get("cashCategoryId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("cashCategoryId", cashCategoryId);
        CashView cashView = iCashMiddle.getCashCategory(qIn, false, userView.getUserId());
        Map out = new HashMap();
        out.put("cashCategory", cashView);

        return out;
    }

    @Override
    public void updateCashCategory(Map in) throws Exception {
        String token = in.get("token").toString();
        String cashCategoryId = in.get("cashCategoryId").toString();
        String cashCategoryName = in.get("cashCategoryName").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("cashCategoryId", cashCategoryId);
        CashView cashView = iCashMiddle.getCashCategory(qIn, false, userView.getUserId());

        if (!cashView.getCashCategoryName().equals(cashCategoryName)) {
            qIn.put("cashCategoryName", cashCategoryName);
            iCashMiddle.updateCashCategory(qIn);
        }
    }
}
