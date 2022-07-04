package cc.cdtime.lifecapsule_v3_api.meta.market.entity;

import lombok.Data;

import java.util.Date;

/**
 * 市场运营客服人员账号
 */
@Data
public class MarketUser {
    private Integer ids;
    private String marketUserId;
    private String loginName;
    private String loginPassword;
    private Date createTime;
    private String roleType;
    private String token;
    private Date tokenTime;
}
