package cc.cdtime.lifecapsule_v3_api.meta.trigger.entity;

import lombok.Data;
import org.apache.ibatis.type.NStringTypeHandler;

import java.util.Date;

@Data
public class TriggerView {
    private Integer ids;
    private String triggerId;
    private String noteId;
    private String remark;
    private Date createTime;
    private Date triggerTime;
    private String userId;
    private Integer actTimes;
    private String triggerType;
    private String status;
    private String recipientId;
    private String toEmail;
    private String title;
    private String noteContent;
    private String userEncodeKey;
    private String fromName;
    private String toUserId;
    private String refPid;
    private String toEmailStatus;
    private String toUserStatus;
    private String toName;
}
