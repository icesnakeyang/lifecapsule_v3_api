package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectRequest extends Request {
    private String projectName;
    private String userId;
    private String projectId;
}
