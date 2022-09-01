package cc.cdtime.lifecapsule_v3_api.middle.trigger;

import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.TriggerView;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.service.IAdminTriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class AdminTriggerMiddle implements IAdminTriggerMiddle {
    private final IAdminTriggerService iAdminTriggerService;

    public AdminTriggerMiddle(IAdminTriggerService iAdminTriggerService) {
        this.iAdminTriggerService = iAdminTriggerService;
    }

    @Override
    public ArrayList<TriggerView> adminListTrigger(Map qIn) throws Exception {
        ArrayList<TriggerView> triggerViews = iAdminTriggerService.adminListTrigger(qIn);
        return triggerViews;
    }

    @Override
    public void updateNoteTrigger(Map qIn) throws Exception {
        iAdminTriggerService.updateNoteTrigger(qIn);
    }
}
