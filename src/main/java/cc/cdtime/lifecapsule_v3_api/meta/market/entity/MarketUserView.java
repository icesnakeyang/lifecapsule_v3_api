package cc.cdtime.lifecapsule_v3_api.meta.market.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MarketUserView {
    private Integer ids;
    private String marketUserId;
    private String loginName;
    private String loginPassword;
    private String createTime;
    private String roleType;
    private String token;
    private Date tokenTime;
}
