package cc.cdtime.lifecapsule_v3_api.meta.cash.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CashView {
    private Integer ids;
    private String cashAccountId;
    private String userId;
    private Double amountIn;
    private Double amountOut;
    private Double balance;
    private String cashLedgerId;
    private Date createTime;
    private String cashCategoryId;
    private String remark;
    private String cashCategoryName;
}
