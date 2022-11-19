package cc.cdtime.lifecapsule_v3_api.meta.cash.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户现金流水账
 */
@Data
public class CashLedger {
    private Integer ids;
    private String cashLedgerId;
    private String userId;
    private Date createTime;
    private Double amountIn;
    private Double amountOut;
    private String cashCategoryId;
    private String remark;
}
