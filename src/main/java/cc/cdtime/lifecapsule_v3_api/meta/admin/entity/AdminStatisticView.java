package cc.cdtime.lifecapsule_v3_api.meta.admin.entity;

import lombok.Data;

@Data
public class AdminStatisticView {
    private Integer total;
    private String userId;
    private String nickname;
    private String email;
}
