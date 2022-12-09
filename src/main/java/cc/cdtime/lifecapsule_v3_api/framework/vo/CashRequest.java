package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CashRequest extends Request {
    private Double amountIn;
    private Double amountOut;
    private String ledgerType;
    private String remark;
    private String CashCategoryName;
    private String cashCategoryId;
    private String cashLedgerId;
    private Date startTime;
    private Date endTime;
    private Date transactionTime;
}
