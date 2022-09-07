package cc.cdtime.lifecapsule_v3_api.middle.trigger;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.content.entity.Content;
import cc.cdtime.lifecapsule_v3_api.meta.content.service.IContentService;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.note.service.INoteService;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.NoteTrigger;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.TriggerView;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.service.ITriggerService;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKey;
import cc.cdtime.lifecapsule_v3_api.meta.user.service.IUserEncodeKeyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
        NoteView noteView = iNoteService.getNoteInfo(trigger.getNoteId());
        Content content = iContentService.getContent(noteView.getNoteId());
        Content content2 = new Content();
        content2.setContent(content.getContent());
        content2.setIndexId(trigger.getTriggerId());
        iContentService.createContent(content2);
        UserEncodeKey userEncodeKey = new UserEncodeKey();
        userEncodeKey.setEncodeKey(noteView.getUserEncodeKey());
        userEncodeKey.setEncodeKeyId(GogoTools.UUID32());
        userEncodeKey.setUserId(trigger.getUserId());
        userEncodeKey.setCreateTime(new Date());
        userEncodeKey.setIndexId(trigger.getTriggerId());
        iUserEncodeKeyService.createUserEncodeKey(userEncodeKey);
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

    @Override
    public void deleteTrigger(Map qIn) throws Exception {
        iTriggerService.deleteTrigger(qIn);
    }
}
