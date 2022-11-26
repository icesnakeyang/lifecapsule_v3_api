package cc.cdtime.lifecapsule_v3_api.meta.task.entity;

import lombok.Data;

import java.util.Date;

/**
 * RPG任务
 */
@Data
public class TaskRPG{
    /**
     * 父任务
     */
    private String pid;
    private Date endTime;

    private String userEncodeKey;
}
