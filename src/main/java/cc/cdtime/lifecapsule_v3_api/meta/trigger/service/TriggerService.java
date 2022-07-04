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
    public TriggerView getTrigger(String triggerId) throws Exception {
        TriggerView triggerView = triggerDao.getTrigger(triggerId);
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
}
