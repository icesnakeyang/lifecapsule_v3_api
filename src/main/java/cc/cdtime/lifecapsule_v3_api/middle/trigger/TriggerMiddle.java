package cc.cdtime.lifecapsule_v3_api.middle.trigger;

import cc.cdtime.lifecapsule_v3_api.meta.content.entity.Content;
import cc.cdtime.lifecapsule_v3_api.meta.content.service.IContentService;
import cc.cdtime.lifecapsule_v3_api.meta.note.service.INoteService;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.NoteTrigger;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.TriggerView;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.service.ITriggerService;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKey;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKeyView;
import cc.cdtime.lifecapsule_v3_api.meta.user.service.IUserEncodeKeyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class TriggerMiddle implements ITriggerMiddle {
    private final ITriggerService iTriggerService;
    private final INoteService iNoteService;
    private final IUserEncodeKeyService iUserEncodeKeyService;
    private final IContentService iContentService;

    public TriggerMiddle(ITriggerService iTriggerService,
                         INoteService iNoteService,
                         IUserEncodeKeyService iUserEncodeKeyService,
                         IContentService iContentService) {
        this.iTriggerService = iTriggerService;
        this.iNoteService = iNoteService;
        this.iUserEncodeKeyService = iUserEncodeKeyService;
        this.iContentService = iContentService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createTrigger(NoteTrigger trigger) throws Exception {
        iTriggerService.createTrigger(trigger);
        Content content2 = new Content();
        content2.setContent(trigger.getNoteContent());
        content2.setIndexId(trigger.getTriggerId());
        iContentService.createContent(content2);
        if (trigger.getUserEncodeKey() != null) {
            UserEncodeKey userEncodeKey = new UserEncodeKey();
            userEncodeKey.setEncodeKey(trigger.getUserEncodeKey());
            userEncodeKey.setIndexId(trigger.getTriggerId());
            iUserEncodeKeyService.createUserEncodeKey(userEncodeKey);
        }
    }

    @Override
    public TriggerView getTrigger(Map qIn, Boolean returnNull, String userId) throws Exception {
        TriggerView triggerView = iTriggerService.getTrigger(qIn);
        if (triggerView == null) {
            if (returnNull) {
                return null;
            }
            /**
             * 没有查询到对应的触发器
             */
            throw new Exception("10017");
        }
        if (userId != null) {
            if (!triggerView.getUserId().equals(userId)) {
                //不能查询不属于自己的笔记的触发器
                throw new Exception("10015");
            }
        }
        TriggerView triggerView1 = loadContent(triggerView.getTriggerId());
        if (triggerView1.getNoteContent() != null) {
            triggerView.setNoteContent(triggerView1.getNoteContent());
            if (triggerView1.getUserEncodeKey() != null) {
                triggerView.setUserEncodeKey(triggerView1.getUserEncodeKey());
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
    public Integer totalTrigger(Map qIn) throws Exception {
        Integer total = iTriggerService.totalTrigger(qIn);
        return total;
    }

    @Override
    public void updateNoteTrigger(Map qIn) throws Exception {
        iTriggerService.updateNoteTrigger(qIn);
    }

    @Override
    public void updateNoteTrigger2(TriggerView triggerView) throws Exception {
        iTriggerService.updateNoteTrigger2(triggerView);
        Content content2 = new Content();
        content2.setContent(triggerView.getNoteContent());
        content2.setIndexId(triggerView.getTriggerId());
        Map qIn=new HashMap();
        qIn.put("indexId", triggerView.getTriggerId());
        qIn.put("content", triggerView.getNoteContent());
        iContentService.updateContent(qIn);
        if (triggerView.getUserEncodeKey() != null) {
            UserEncodeKey userEncodeKey = new UserEncodeKey();
            userEncodeKey.setEncodeKey(triggerView.getUserEncodeKey());
            userEncodeKey.setIndexId(triggerView.getTriggerId());
            qIn=new HashMap();
            qIn.put("encodeKey", triggerView.getUserEncodeKey());
            qIn.put("indexId", triggerView.getTriggerId());
            iUserEncodeKeyService.updateUserEncodeKey(qIn);
        }
    }

    @Override
    public void deleteTrigger(String triggerId) throws Exception {
        iTriggerService.deleteTrigger(triggerId);
        iContentService.deleteContent(triggerId);
        iUserEncodeKeyService.deleteUserEncodeKey(triggerId);
    }

    private TriggerView loadContent(String triggerId) throws Exception {
        TriggerView triggerView = new TriggerView();
        Content content = iContentService.getContent(triggerId);
        if (content != null) {
            triggerView.setNoteContent(content.getContent());
            Map qIn = new HashMap();
            qIn.put("indexId", triggerId);
            UserEncodeKeyView userEncodeKeyView = iUserEncodeKeyService.getUserEncodeKey(triggerId);
            if (userEncodeKeyView != null) {
                triggerView.setUserEncodeKey(userEncodeKeyView.getEncodeKey());
            }
        }
        return triggerView;
    }
}
