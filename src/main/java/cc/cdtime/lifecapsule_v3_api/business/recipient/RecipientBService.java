package cc.cdtime.lifecapsule_v3_api.business.recipient;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.contact.entity.ContactView;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.recipient.entity.NoteRecipient;
import cc.cdtime.lifecapsule_v3_api.meta.recipient.entity.RecipientView;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.TriggerView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.contact.IContactMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.recipient.IRecipientMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.trigger.ITriggerMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipientBService implements IRecipientBService {
    private final IRecipientMiddle iRecipientMiddle;
    private final IUserMiddle iUserMiddle;
    private final ITriggerMiddle iTriggerMiddle;
    private final INoteMiddle iNoteMiddle;
    private final IContactMiddle iContactMiddle;

    public RecipientBService(IRecipientMiddle iRecipientMiddle,
                             IUserMiddle iUserMiddle,
                             ITriggerMiddle iTriggerMiddle,
                             INoteMiddle iNoteMiddle,
                             IContactMiddle iContactMiddle) {
        this.iRecipientMiddle = iRecipientMiddle;
        this.iUserMiddle = iUserMiddle;
        this.iTriggerMiddle = iTriggerMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iContactMiddle = iContactMiddle;
    }

    @Override
    public Map listRecipient(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = (String) in.get("noteId");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        qIn = new HashMap();
        if (noteId != null) {
            qIn.put("noteId", noteId);
        }
        ArrayList<RecipientView> recipientViews = iRecipientMiddle.listNoteRecipient(qIn);

        Map out = new HashMap();
        out.put("recipientList", recipientViews);

        return out;
    }

    @Override
    public void createNoteRecipient(Map in) throws Exception {
        String token = in.get("token").toString();
        String name = (String) in.get("name");
        String phone = (String) in.get("phone");
        String email = (String) in.get("email");
        String remark = (String) in.get("remark");
        String triggerId = in.get("triggerId").toString();
        String noteId = in.get("noteId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

//        TriggerView triggerView = iTriggerMiddle.getTrigger(triggerId, false, noteView.getNoteId(), userView.getUserId());

        NoteRecipient noteRecipient = new NoteRecipient();
        noteRecipient.setNoteId(noteId);
        noteRecipient.setRemark(remark);
        noteRecipient.setRecipientName(name);
        noteRecipient.setTriggerId(triggerId);
        noteRecipient.setEmail(email);
        noteRecipient.setPhone(phone);
        noteRecipient.setStatus(ESTags.ACTIVE.toString());
        iRecipientMiddle.createNoteRecipient(noteRecipient);
    }

    @Override
    public void deleteNoteRecipient(Map in) throws Exception {
        String token = in.get("token").toString();
        String recipientId = in.get("recipientId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        iRecipientMiddle.deleteNoteRecipient(recipientId);
    }

    @Override
    public void saveRecipient(Map in) throws Exception {
        String token = in.get("token").toString();
        String phone = (String) in.get("phone");
        String name = (String) in.get("name");
        String email = (String) in.get("email");
        String recipientId = (String) in.get("recipientId");
        String remark = (String) in.get("remark");
        String noteId = (String) in.get("noteId");
        String title = (String) in.get("title");
        String description = (String) in.get("description");
        String toName = (String) in.get("toName");
        String fromName = (String) in.get("fromName");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        if (recipientId == null) {
            /**
             * ??????
             */
            NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());
            NoteRecipient noteRecipient = new NoteRecipient();
            noteRecipient.setRemark(remark);
            noteRecipient.setNoteId(noteView.getNoteId());
            noteRecipient.setRecipientName(name);
            noteRecipient.setEmail(email);
            noteRecipient.setPhone(phone);
            noteRecipient.setStatus(ESTags.ACTIVE.toString());
            noteRecipient.setRecipientId(GogoTools.UUID32());
            noteRecipient.setUserId(userView.getUserId());
            iRecipientMiddle.createNoteRecipient(noteRecipient);
        } else {
            /**
             * ??????
             */
            RecipientView recipientView = iRecipientMiddle.getRecipient(recipientId, false, userView.getUserId());
            qIn = new HashMap();
            qIn.put("name", name);
            qIn.put("phone", phone);
            qIn.put("email", email);
            qIn.put("remark", remark);
            qIn.put("recipientId", recipientId);
            qIn.put("title", title);
            qIn.put("description", description);
            qIn.put("fromName", fromName);
            qIn.put("toName", toName);
            iRecipientMiddle.updateNoteRecipient(qIn);
        }
    }

    @Override
    public Map getRecipient(Map in) throws Exception {
        String token = in.get("token").toString();
        String recipientId = (String) in.get("recipientId");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        RecipientView recipientView = iRecipientMiddle.getRecipient(recipientId, false);

        Map out = new HashMap();
        out.put("recipient", recipientView);
        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRecipient(Map in) throws Exception {
        String token = in.get("token").toString();
        String recipientId = (String) in.get("recipientId");

        Map qIn = new HashMap();
        qIn.put("token", token);

        /**
         * ??????????????????
         */
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * ?????????????????????
         */
        RecipientView recipientView = iRecipientMiddle.getRecipientTiny(recipientId, false, userView.getUserId());

        /**
         * ??????recipient???triggerId???????????????????????????trigger?????????????????????????????????recipient??????
         */
        if (recipientView.getTriggerId() != null) {
            qIn = new HashMap();
            qIn.put("triggerId", recipientView.getTriggerId());
            TriggerView triggerView = iTriggerMiddle.getTrigger(qIn, false, userView.getUserId());
        } else {
            /**
             * ??????triggerId????????????????????????note????????????recipient?????????????????????trigger?????????????????????????????????????????????????????????trigger
             */
            //????????????????????????????????????????????????????????????
            NoteView noteView = iNoteMiddle.getNoteTiny(recipientView.getNoteId(), false, userView.getUserId());
            //??????trigger
            qIn = new HashMap();
            qIn.put("recipientId", recipientId);
            TriggerView triggerView = iTriggerMiddle.getTrigger(qIn, true, userView.getUserId());
            if (triggerView!=null && triggerView.getTriggerId() != null) {
                iTriggerMiddle.deleteTrigger(triggerView.getTriggerId());
            }
        }
        /**
         * ???????????????
         */
        iRecipientMiddle.deleteNoteRecipient(recipientId);
    }

    @Override
    public void addContactToRecipient(Map in) throws Exception {
        String token = in.get("token").toString();
        String contactId = in.get("contactId").toString();
        String noteId = in.get("noteId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);

        /**
         * ??????????????????
         */
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * ?????????????????????
         */
        qIn = new HashMap();
        qIn.put("contactId", contactId);
        ContactView contactView = iContactMiddle.getContact(qIn, false, userView.getUserId());

        /**
         * ??????????????????
         */
        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        /**
         * ??????????????????????????????
         * ??????????????????????????????????????????????????????????????????
         */

        NoteRecipient noteRecipient = new NoteRecipient();
        noteRecipient.setNoteId(noteId);
        noteRecipient.setRecipientName(contactView.getContactName());
        noteRecipient.setPhone(contactView.getPhone());
        noteRecipient.setEmail(contactView.getEmail());
        noteRecipient.setRemark(contactView.getRemark());
        noteRecipient.setStatus(ESTags.ACTIVE.toString());
        noteRecipient.setRecipientId(GogoTools.UUID32());
        noteRecipient.setUserId(userView.getUserId());
        noteRecipient.setTitle(noteView.getTitle());
        noteRecipient.setToName(contactView.getContactName());
        iRecipientMiddle.createNoteRecipient(noteRecipient);
    }
}

