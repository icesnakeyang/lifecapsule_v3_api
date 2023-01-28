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
    public Integer totalTrigger(Map qIn) throws Exception {
        Integer total = triggerDao.totalTrigger(qIn);
        return total;
    }

    @Override
    public void updateNoteTrigger(Map qIn) throws Exception {
        triggerDao.updateNoteTrigger(qIn);
    }

    @Override
    public void updateNoteTrigger2(TriggerView triggerView) throws Exception {
        triggerDao.updateNoteTrigger2(triggerView);
    }

    @Override
    public void deleteTrigger(String triggerId) throws Exception {
        triggerDao.deleteTrigger(triggerId);
    }
}
