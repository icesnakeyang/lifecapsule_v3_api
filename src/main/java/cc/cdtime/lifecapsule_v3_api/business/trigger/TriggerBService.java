package cc.cdtime.lifecapsule_v3_api.business.trigger;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.contact.entity.Contact;
import cc.cdtime.lifecapsule_v3_api.meta.contact.entity.ContactView;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmail;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmailView;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.recipient.entity.RecipientView;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.NoteTrigger;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.TriggerView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.contact.IContactMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.recipient.IRecipientMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.security.ISecurityMiddle;
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
    private final IContactMiddle iContactMiddle;
    private final ISecurityMiddle iSecurityMiddle;

    public TriggerBService(IUserMiddle iUserMiddle,
                           ITriggerMiddle iTriggerMiddle,
                           INoteMiddle iNoteMiddle,
                           IRecipientMiddle iRecipientMiddle,
                           IContactMiddle iContactMiddle,
                           ISecurityMiddle iSecurityMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iTriggerMiddle = iTriggerMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iRecipientMiddle = iRecipientMiddle;
        this.iContactMiddle = iContactMiddle;
        this.iSecurityMiddle = iSecurityMiddle;
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
            throw new Exception("10048");
        }

        if (triggerId != null) {
//            TriggerView triggerView = iTriggerMiddle.getTrigger(triggerId, false, userView.getUserId());
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

//    /**
//     * 用户设置指定一篇笔记的指定一个接收人的发送触发条件
//     * 说明：本方法用于用户对特定一篇笔记指定的特定一位接收人单独设置的发送触发条件，每次只能设置一个接收人的发送条件，不适合批量操作情况
//     *
//     * @param in
//     * @throws Exception
//     */
//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public Map saveNoteRecipientTrigger(Map in) throws Exception {
//        String token = in.get("token").toString();
//        String remark = (String) in.get("remark");
//        String triggerType = in.get("triggerType").toString();
//        Date triggerTime = (Date) in.get("triggerTime");
//        String triggerId = (String) in.get("triggerId");
//        String fromName = (String) in.get("fromName");
//        String recipientId = in.get("recipientId").toString();
//
//        /**
//         * 读取当前用户信息
//         */
//        Map qIn = new HashMap();
//        qIn.put("token", token);
//        //检查用户是否存在，是否已经登录
//        //todo 检查登录是否有效
//        UserView userView = iUserMiddle.getUser(qIn, false, true);
//
//        /**
//         * 检查触发器是否有效
//         */
//        int cc = 0;
//        if (triggerType.equals(ESTags.TIMER_TYPE_PRIMARY.toString())) {
//            /**
//             * 主倒计时
//             */
//            triggerTime = null;
//            cc++;
//        } else {
//            if (triggerType.equals(ESTags.TIMER_TYPE_DATETIME.toString())) {
//                /**
//                 * 用户指定时间
//                 */
//                if (triggerTime == null) {
//                    //必须设定触发条件的触发时间
//                    throw new Exception("10020");
//                }
//                cc++;
//            }
//        }
//        if (cc == 0) {
//            //无效的触发条件
//            throw new Exception("10048");
//        }
//
//        /**
//         * 读取接收人
//         */
//        RecipientView recipientView = iRecipientMiddle.getRecipient(recipientId, false, userView.getUserId());
//
//        /**
//         * 读取接收人的触发器
//         */
//        qIn = new HashMap();
//        qIn.put("recipientId", recipientId);
//        TriggerView triggerView = iTriggerMiddle.getTrigger(qIn, true, userView.getUserId());
//
//
//        /**
//         * 如果有triggerId，就是修改，没有就增加
//         */
//        Map triggerMap = new HashMap();
//        if (triggerId != null) {
//            if (!triggerView.getTriggerId().equals(triggerId)) {
//                //触发器错误
//                throw new Exception("10032");
//            }
//            /**
//             * 保存保存触发器
//             */
//            qIn = new HashMap();
//            qIn.put("triggerTime", triggerTime);
//            qIn.put("triggerType", triggerType);
//            qIn.put("triggerId", triggerId);
//            iTriggerMiddle.updateNoteTrigger(qIn);
//            triggerMap.put("triggerId", triggerId);
//            triggerMap.put("triggerType", triggerType);
//            triggerMap.put("triggerTime", triggerTime);
//        } else {
//            /**
//             * 新增触发器
//             */
//            if (triggerView != null) {
//                //触发器已经存在，不能新增
//                throw new Exception("10033");
//            }
//            NoteTrigger trigger = new NoteTrigger();
//            trigger.setTriggerId(GogoTools.UUID32());
//            trigger.setCreateTime(new Date());
//            trigger.setNoteId(recipientView.getNoteId());
//            trigger.setUserId(userView.getUserId());
//            trigger.setTriggerType(triggerType);
//            trigger.setTriggerTime(triggerTime);
//            trigger.setRecipientId(recipientView.getRecipientId());
//            trigger.setStatus(ESTags.ACTIVE.toString());
//            iTriggerMiddle.createTrigger(trigger);
//            triggerMap.put("triggerId", trigger.getTriggerId());
//            triggerMap.put("triggerType", trigger.getTriggerType());
//            triggerMap.put("triggerTime", trigger.getTriggerTime());
//        }
//
//        Map out = new HashMap();
//        out.put("trigger", triggerMap);
//
//        return out;
//    }

//    @Override
//    public Map getNoteTrigger(Map in) throws Exception {
//        String token = in.get("token").toString();
//        String triggerId = (String) in.get("triggerId");
//
//        Map qIn = new HashMap();
//        qIn.put("token", token);
//        UserView userView = iUserMiddle.getUser(qIn, false, true);
//
//        Map out = new HashMap();
//
//        qIn = new HashMap();
//        qIn.put("triggerId", triggerId);
//        TriggerView triggerView = iTriggerMiddle.getTrigger(qIn, true, userView.getUserId());
//        out.put("trigger", triggerView);
//        return out;
//    }

    @Override
    public Map listNoteTrigger(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");
        String triggerType = (String) in.get("triggerType");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        ArrayList<TriggerView> triggerViews = iTriggerMiddle.listTrigger(qIn);
        Integer total = iTriggerMiddle.totalTrigger(qIn);

        Map out = new HashMap();
        out.put("triggerList", triggerViews);
        out.put("totalTrigger", total);

        return out;
    }

    @Override
    public Map getTriggerDetail(Map in) throws Exception {
        String token = in.get("token").toString();
        String triggerId = in.get("triggerId").toString();

        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");

        /**
         * 获取用户临时上传的加密笔记AES秘钥的AES秘钥
         */
        String strAESKey = null;
        if (keyToken != null) {
            strAESKey = iSecurityMiddle.takeNoteAES(keyToken, encryptKey);
        }

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        TriggerView triggerView = iTriggerMiddle.getTrigger(triggerId, false, userView.getUserId());

        /**
         * 读取userEncodeKey
         */
        if (triggerView.getUserEncodeKey() != null) {
            if (strAESKey == null) {
                //查询秘钥错误
                throw new Exception("10037");
            }
            String data = triggerView.getUserEncodeKey();
            //用AES秘钥加密笔记内容的AES秘钥
            String outCode = GogoTools.encryptAESKey(data, strAESKey);
            triggerView.setUserEncodeKey(outCode);
        }

        Map out = new HashMap();
        out.put("trigger", triggerView);

        return out;
    }

    @Override
    public void deleteNoteTrigger(Map in) throws Exception {
        String token = in.get("token").toString();
        String triggerId = in.get("triggerId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        TriggerView triggerView = iTriggerMiddle.getTrigger(triggerId, false, userView.getUserId());

        iTriggerMiddle.deleteTrigger(triggerId);
    }

    @Override
    public void createTriggerInstant(Map in) throws Exception {
        String token = in.get("token").toString();
        String toEmail = in.get("toEmail").toString();
        String title = (String) in.get("title");
        String noteId = in.get("noteId").toString();
        String noteContent = in.get("noteContent").toString();
        String fromName = (String) in.get("fromName");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteDetail(noteId, false, userView.getUserId());

        qIn = new HashMap();
        qIn.put("email", toEmail);
        UserEmailView userEmailView = iUserMiddle.getUserEmail(qIn, true, null);
        if (userEmailView == null) {

        }

        qIn = new HashMap();
        qIn.put("email", toEmail);
        ContactView contactView = iContactMiddle.getContact(qIn, true, null);
        if (contactView == null) {
            /**
             * 创建联系人
             */
            Contact contact = new Contact();
            contact.setContactId(GogoTools.UUID32());
            contact.setEmail(toEmail);
            contact.setUserId(userView.getUserId());
            iContactMiddle.createContact(contact);
        }


        /**
         * 新建触发器
         * 1、首先创建一个NoteTrigger
         * 2、生成triggerId
         * 3、创建一个content，indexId=triggerId
         * 4、创建一个UserEncodeKey，indexId=triggerId
         */
        NoteTrigger noteTrigger = new NoteTrigger();
        noteTrigger.setTriggerId(GogoTools.UUID32());
        noteTrigger.setTriggerType(ESTags.INSTANT_MESSAGE.toString());
        noteTrigger.setNoteContent(noteContent);
        noteTrigger.setCreateTime(new Date());
        noteTrigger.setUserId(userView.getUserId());
        noteTrigger.setStatus(ESTags.ACTIVE.toString());
        noteTrigger.setTitle(title);
        noteTrigger.setToEmail(toEmail);
        noteTrigger.setTriggerTime(new Date());
        noteTrigger.setNoteId(noteView.getNoteId());
        noteTrigger.setFromName(fromName);
        iTriggerMiddle.createTrigger(noteTrigger);
    }

    /**
     * 用户保存一个笔记触发器，根据设置的时间来发送
     *
     * @param in
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createNoteTriggerByDatetime(Map in) throws Exception {
        String token = in.get("token").toString();
        String toEmail = in.get("toEmail").toString();
        String title = (String) in.get("title");
        Date sendTime = (Date) in.get("sendTime");
        String noteId = in.get("noteId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteDetail(noteId, false, userView.getUserId());

        qIn = new HashMap();
        qIn.put("email", toEmail);
        UserEmailView userEmailView = iUserMiddle.getUserEmail(qIn, true, null);
        if (userEmailView == null) {

        }

        qIn = new HashMap();
        qIn.put("email", toEmail);
        ContactView contactView = iContactMiddle.getContact(qIn, true, null);
        if (contactView == null) {
            /**
             * 创建联系人
             */
            Contact contact = new Contact();
            contact.setContactId(GogoTools.UUID32());
            contact.setEmail(toEmail);
            contact.setUserId(userView.getUserId());
            iContactMiddle.createContact(contact);
        }


        /**
         * 新建触发器
         * 1、首先创建一个NoteTrigger
         * 2、生成triggerId
         * 3、创建一个content，indexId=triggerId
         * 4、创建一个UserEncodeKey，indexId=triggerId
         */
        NoteTrigger noteTrigger = new NoteTrigger();
        noteTrigger.setTriggerId(GogoTools.UUID32());
        noteTrigger.setTriggerType(ESTags.TIMER_TYPE_DATETIME.toString());
        noteTrigger.setNoteContent(noteView.getContent());
        noteTrigger.setUserEncodeKey(noteView.getUserEncodeKey());
        noteTrigger.setCreateTime(new Date());
        noteTrigger.setUserId(userView.getUserId());
        noteTrigger.setStatus(ESTags.ACTIVE.toString());
        noteTrigger.setTitle(title);
        noteTrigger.setToEmail(toEmail);
        noteTrigger.setTriggerTime(sendTime);
        noteTrigger.setNoteId(noteView.getNoteId());
        iTriggerMiddle.createTrigger(noteTrigger);
    }

    @Override
    public void createNoteTriggerPrimary(Map in) throws Exception {
        String token = in.get("token").toString();
        String toEmail = in.get("toEmail").toString();
        String title = (String) in.get("title");
        String noteId = in.get("noteId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteDetail(noteId, false, userView.getUserId());

        qIn = new HashMap();
        qIn.put("email", toEmail);
        ContactView contactView = iContactMiddle.getContact(qIn, true, null);
        if (contactView == null) {
            /**
             * 创建联系人
             */
            Contact contact = new Contact();
            contact.setContactId(GogoTools.UUID32());
            contact.setEmail(toEmail);
            contact.setUserId(userView.getUserId());
            iContactMiddle.createContact(contact);
        }

        /**
         * 查询触发器是否已经存在
         */
        qIn = new HashMap();
        qIn.put("triggerType", ESTags.TIMER_TYPE_PRIMARY.toString());
        qIn.put("userId", userView.getUserId());
        qIn.put("status", ESTags.ACTIVE.toString());
        qIn.put("noteId", noteId);
        ArrayList<TriggerView> triggerViews = iTriggerMiddle.listTrigger(qIn);
        if(triggerViews.size()>0){
            //该笔记已经设订了主倒计时结束时发送
            throw new Exception("10057");
        }

        /**
         * 新建触发器
         * 1、首先创建一个NoteTrigger
         * 2、生成triggerId
         * 3、创建一个content，indexId=triggerId
         * 4、创建一个UserEncodeKey，indexId=triggerId
         */
        NoteTrigger noteTrigger = new NoteTrigger();
        noteTrigger.setTriggerId(GogoTools.UUID32());
        noteTrigger.setTriggerType(ESTags.TIMER_TYPE_PRIMARY.toString());
        noteTrigger.setNoteContent(noteView.getContent());
        noteTrigger.setUserEncodeKey(noteView.getUserEncodeKey());
        noteTrigger.setCreateTime(new Date());
        noteTrigger.setUserId(userView.getUserId());
        noteTrigger.setStatus(ESTags.ACTIVE.toString());
        noteTrigger.setTitle(title);
        noteTrigger.setToEmail(toEmail);
        noteTrigger.setNoteId(noteView.getNoteId());
        iTriggerMiddle.createTrigger(noteTrigger);
    }
}
