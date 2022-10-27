package cc.cdtime.lifecapsule_v3_api.admin.motto;

import cc.cdtime.lifecapsule_v3_api.framework.vo.Request;
import lombok.Data;

@Data
public class AdminMottoRequest extends Request {
    private String mottoId;
}
