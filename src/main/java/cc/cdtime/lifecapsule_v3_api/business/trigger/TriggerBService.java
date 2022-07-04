package cc.cdtime.lifecapsule_v3_api.business.trigger;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.recipient.entity.RecipientView;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.NoteTrigger;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.TriggerView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.recipient.IRecipientMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.trigger.ITriggerMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TriggerBService implements ITriggerBService {
    private final IUserMiddle iUserMiddle;
    private final ITriggerMiddle iTriggerMiddle;
    private final INoteMiddle iNoteMiddle;
    private final IRecipientMiddle iRecipientMiddle;

    public TriggerBService(IUserMiddle iUserMiddle,
                           ITriggerMiddle iTriggerMiddle,
                           INoteMiddle iNoteMiddle,
                           IRecipientMiddle iRecipientMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iTriggerMiddle = iTriggerMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iRecipientMiddle = iRecipientMiddle;
    }


    @Override
    public Map listNoteTrigger(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        qIn = new HashMap();
        qIn.put("noteId", noteView.getNoteId());
        ArrayList<TriggerView> triggerViews = iTriggerMiddle.listTrigger(qIn);

        Map out = new HashMap();
        out.put("triggerList", triggerViews);

        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map saveNoteTrigger(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = (String) in.get("noteId");
        String remark = (String) in.get("remark");
        String triggerType = in.get("triggerType").toString();
        Date triggerTime = (Date) in.get("triggerTime");
        String triggerId = (String) in.get("triggerId");
        String fromName = (String) in.get("fromName");
        String recipientId = (String) in.get("recipientId");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        int cc = 0;
        if (triggerType.equals(ESTags.TIMER_TYPE_PRIMARY.toString())) {
            /**
             * 主倒计时
             */
            triggerTime = null;
            cc++;
        } else {
            if (triggerType.equals(ESTags.TIMER_TYPE_DATETIME.toString())) {
                /**
                 * 用户指定时间
                 */
                if (triggerTime == null) {
                    //必须设定触发条件的触发时间
                    throw new Exception("10020");
                }
                cc++;
            } else {
                if (triggerType.equals(ESTags.NO_TRIGGER.toString())) {
                    /**
                     * 不设置触发条件
                     */
                    cc++;
                }
            }
        }
        if (cc == 0) {
            //无效的触发条件
            throw new Exception("10047");
        }

        if (triggerId != null) {
            TriggerView triggerView = iTriggerMiddle.getTrigger(triggerId, false, userView.getUserId());
            /**
             * 保存保存触发器
             */
            qIn = new HashMap();
            qIn.put("triggerTime", triggerTime);
            qIn.put("remark", remark);
            qIn.put("triggerType", triggerType);
            qIn.put("triggerId", triggerId);
            qIn.put("fromName", fromName);
            iTriggerMiddle.updateNoteTrigger(qIn);
        } else {
            /**
             * 新增触发器
             */
            RecipientView recipientView = iRecipientMiddle.getRecipient(recipientId, false, userView.getUserId());
            NoteView noteView = iNoteMiddle.getNoteTiny(recipientView.getNoteId(), false, userView.getUserId());
            NoteTrigger trigger = new NoteTrigger();
            trigger.setTriggerId(GogoTools.UUID32());
            trigger.setCreateTime(new Date());
            trigger.setNoteId(noteView.getNoteId());
            trigger.setUserId(userView.getUserId());
            trigger.setTriggerType(triggerType);
            trigger.setRemark(remark);
            trigger.setTriggerTime(triggerTime);
            trigger.setRecipientId(recipientView.getRecipientId());
            trigger.setStatus(ESTags.ACTIVE.toString());

            iTriggerMiddle.createTrigger(trigger);
            triggerId = trigger.getTriggerId();
        }

        Map out = new HashMap();
        out.put("triggerId", triggerId);

        return out;
    }

    @Override
    public Map getNoteTrigger(Map in) throws Exception {
        String token = in.get("token").toString();
        String triggerId = (String) in.get("triggerId");
        String noteId = (String) in.get("noteId");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Map out = new HashMap();

        if (triggerId != null) {
            TriggerView triggerView = iTriggerMiddle.getTrigger(triggerId, false, userView.getUserId());
            out.put("triggerView", triggerView);
        } else {
            if (noteId != null) {
                qIn = new HashMap();
                qIn.put("noteId", noteId);
                ArrayList<TriggerView> triggerViews = iTriggerMiddle.listTrigger(qIn);
                if (triggerViews.size() > 0) {
                    out.put("triggerView", triggerViews.get(0));
                }
            }
        }
        return out;
    }
}
