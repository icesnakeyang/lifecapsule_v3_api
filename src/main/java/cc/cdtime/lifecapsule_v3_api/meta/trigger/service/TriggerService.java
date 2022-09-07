package cc.cdtime.lifecapsule_v3_api.meta.trigger.service;

import cc.cdtime.lifecapsule_v3_api.meta.trigger.dao.TriggerDao;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.NoteTrigger;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.TriggerView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class TriggerService implements ITriggerService {
    private final TriggerDao triggerDao;

    public TriggerService(TriggerDao triggerDao) {
        this.triggerDao = triggerDao;
    }

    @Override
    public void createTrigger(NoteTrigger trigger) throws Exception {
        triggerDao.createTrigger(trigger);
        triggerDao.createContentDetail(trigger);
    }

    @Override
    public TriggerView getTrigger(Map qIn) throws Exception {
        TriggerView triggerView = triggerDao.getTrigger(qIn);
        return triggerView;
    }

    @Override
    public ArrayList<TriggerView> listTrigger(Map qIn) throws Exception {
        ArrayList<TriggerView> triggerViews = triggerDao.listTrigger(qIn);
        return triggerViews;
    }

    @Override
    public void updateNoteTrigger(Map qIn) throws Exception {
        triggerDao.updateNoteTrigger(qIn);
    }

    @Override
    public void deleteTrigger(Map qIn) throws Exception {
        String triggerId = (String) qIn.get("triggerId");
        String noteId = (String) qIn.get("noteId");
        String recipientId = (String) qIn.get("recipientId");
        if (triggerId == null) {
            if (noteId == null) {
                if (recipientId == null) {
                    //删除触发器失败，必须指定一个删除条件
                    throw new Exception("10051");
                }
            }
        }
        triggerDao.deleteTrigger(qIn);
    }
}
