package cc.cdtime.lifecapsule_v3_api.middle.trigger;

import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.NoteTrigger;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.TriggerView;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.service.ITriggerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class TriggerMiddle implements ITriggerMiddle {
    private final ITriggerService iTriggerService;

    public TriggerMiddle(ITriggerService iTriggerService) {
        this.iTriggerService = iTriggerService;
    }

    @Override
    public void createTrigger(NoteTrigger trigger) throws Exception {
        iTriggerService.createTrigger(trigger);
    }

    @Override
    public TriggerView getTrigger(String triggerId, Boolean returnNull, String noteId, String userId) throws Exception {
        TriggerView triggerView = iTriggerService.getTrigger(triggerId);
        if (triggerView == null) {
            if (returnNull) {
                return null;
            }
            /**
             * 没有查询到对应的触发器
             */
            throw new Exception("10017");
        }
        if (noteId != null) {
            if (!triggerView.getNoteId().equals(noteId)) {
                //当前编辑的触发器不属于当前编辑的笔记
                throw new Exception("10018");
            }
        }
        if(userId!=null){
            if(!triggerView.getUserId().equals(userId)){
                //不能查询不属于自己的笔记的触发器
                throw new Exception("10015");
            }
        }
        return triggerView;
    }

    @Override
    public TriggerView getTrigger(String triggerId, Boolean returnNull, String userId) throws Exception {
        TriggerView triggerView = iTriggerService.getTrigger(triggerId);
        if (triggerView == null) {
            if (returnNull) {
                return null;
            }
            /**
             * 没有查询到对应的触发器
             */
            throw new Exception("10017");
        }
        if(userId!=null){
            if(!triggerView.getUserId().equals(userId)){
                //不能查询不属于自己的笔记的触发器
                throw new Exception("10015");
            }
        }
        return triggerView;
    }

    @Override
    public ArrayList<TriggerView> listTrigger(Map qIn) throws Exception {
        ArrayList<TriggerView> triggerViews = iTriggerService.listTrigger(qIn);
        return triggerViews;
    }

    @Override
    public void updateNoteTrigger(Map qIn) throws Exception {
        iTriggerService.updateNoteTrigger(qIn);
    }
}
