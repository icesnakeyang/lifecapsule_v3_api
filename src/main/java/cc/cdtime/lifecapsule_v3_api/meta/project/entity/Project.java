package cc.cdtime.lifecapsule_v3_api.meta.project.entity;

import lombok.Data;

import java.util.Date;

/**
 * 项目
 */
@Data
public class Project {
    private Integer ids;
    private String projectId;
    private String projectName;
    private String userId;
    private String remark;
    private Date createTime;
}
