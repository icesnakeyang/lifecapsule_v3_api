package cc.cdtime.lifecapsule_v3_api.meta.email.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户验证email的日志记录
 */
@Data
public class EmailLog {
    private Integer ids;
    private String email;
    private String code;
    private Date createTime;
}
