package cc.cdtime.lifecapsule_v3_api.app.cash;

import cc.cdtime.lifecapsule_v3_api.framework.common.ICommonService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.vo.CashRequest;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/app/cash")
public class AppCashController {
    private final IAppCashBService iAppCashBService;
    private final ICommonService iCommonService;

    public AppCashController(IAppCashBService iAppCashBService,
                             ICommonService iCommonService) {
        this.iAppCashBService = iAppCashBService;
        this.iCommonService = iCommonService;
    }

    /**
     * app用户创建一个现金流水记录
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/createLedger")
    public Response createLedger(@RequestBody CashRequest request,
                                 HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("amountIn", request.getAmountIn());
            in.put("amountOut", request.getAmountOut());
            in.put("ledgerType", request.getLedgerType());
            in.put("remark", request.getRemark());

            logMap.put("", ESTags.CASH_CREATE_LEDGER);
            logMap.put("token", token);
            memoMap.put("ledgerType", request.getLedgerType());
            memoMap.put("amountIn", request.getAmountIn());
            memoMap.put("amountOut", request.getAmountOut());

            iAppCashBService.createLedger(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App createLedger error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App createLedger user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * app用户查询自己的默认现金账户分类
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @GetMapping("/getMyDefaultCashCategory")
    public Response getMyDefaultCashCategory(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            Map out = iAppCashBService.getMyDefaultCashCategory(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getMyDefaultCashCategory error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * app用户查询自己所有的现金账户分类列表
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @GetMapping("/listMyCashCategory")
    public Response listMyCashCategory(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            Map out = iAppCashBService.listMyCashCategory(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listMyCashCategory error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * app用户创建一个新的现金账户分类
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/createMyCashCategory")
    public Response createMyCashCategory(@RequestBody CashRequest request,
                                         HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("categoryName", request.getCategoryName());
            iAppCashBService.createMyCashCategory(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App createMyCashCategory error:" + ex.getMessage());
            }
        }
        return response;
    }
}
