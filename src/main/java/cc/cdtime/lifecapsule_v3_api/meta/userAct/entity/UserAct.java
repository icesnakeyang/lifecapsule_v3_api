package cc.cdtime.lifecapsule_v3_api.meta.userAct.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户行为日志
 */
@Data
public class UserAct {
    private Integer ids;
    /**
     * 发生时间
     */
    private Date createTime;
    /**
     * 用户行为类型
     */
    private String actType;

    private String result;

    /**
     * 需要记录的信息
     */
    private Object memo;
    private String userId;
}
