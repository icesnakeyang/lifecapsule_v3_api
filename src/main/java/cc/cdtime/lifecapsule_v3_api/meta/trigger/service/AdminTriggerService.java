package cc.cdtime.lifecapsule_v3_api.meta.trigger.service;

import cc.cdtime.lifecapsule_v3_api.meta.trigger.dao.AdminTriggerDao;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.TriggerView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class AdminTriggerService implements IAdminTriggerService {
    private final AdminTriggerDao adminTriggerDao;

    public AdminTriggerService(AdminTriggerDao adminTriggerDao) {
        this.adminTriggerDao = adminTriggerDao;
    }

    @Override
    public ArrayList<TriggerView> adminListTrigger(Map qIn) throws Exception {
        ArrayList<TriggerView> triggerViews = adminTriggerDao.adminListTrigger(qIn);
        return triggerViews;
    }

    @Override
    public void updateNoteTrigger(Map qIn) throws Exception {
        adminTriggerDao.updateNoteTrigger(qIn);
    }
}
