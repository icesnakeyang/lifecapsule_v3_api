package cc.cdtime.lifecapsule_v3_api.meta.task.service;

import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskCreative;

import java.util.ArrayList;
import java.util.Map;

public interface ITaskCreativeService {
    void deleteTaskCreative(Map qIn) throws Exception;

    ArrayList<TaskCreative> listTaskCreative(Map qIn2);
}
