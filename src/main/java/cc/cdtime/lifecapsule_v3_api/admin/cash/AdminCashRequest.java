package cc.cdtime.lifecapsule_v3_api.admin.cash;

import cc.cdtime.lifecapsule_v3_api.framework.vo.Request;
import lombok.Data;

@Data
public class AdminCashRequest extends Request {
    private String userId;
}
