package cc.cdtime.lifecapsule_v3_api.business.recipient;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.category.entity.Category;
import cc.cdtime.lifecapsule_v3_api.meta.contact.entity.Contact;
import cc.cdtime.lifecapsule_v3_api.meta.contact.entity.ContactView;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmail;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmailView;
import cc.cdtime.lifecapsule_v3_api.meta.email.service.IUserEmailService;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.recipient.entity.NoteRecipient;
import cc.cdtime.lifecapsule_v3_api.meta.recipient.entity.RecipientView;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.TriggerView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserBase;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserLogin;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserLoginLog;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.category.ICategoryMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.contact.IContactMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.recipient.IRecipientMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.timer.ITimerMiddle;
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
public class RecipientBService implements IRecipientBService {
    private final IRecipientMiddle iRecipientMiddle;
    private final IUserMiddle iUserMiddle;
    private final ITriggerMiddle iTriggerMiddle;
    private final INoteMiddle iNoteMiddle;
    private final IContactMiddle iContactMiddle;
    private final ICategoryMiddle iCategoryMiddle;
    private final ITimerMiddle iTimerMiddle;

    public RecipientBService(IRecipientMiddle iRecipientMiddle,
                             IUserMiddle iUserMiddle,
                             ITriggerMiddle iTriggerMiddle,
                             INoteMiddle iNoteMiddle,
                             IContactMiddle iContactMiddle,
                             ICategoryMiddle iCategoryMiddle,
                             ITimerMiddle iTimerMiddle) {
        this.iRecipientMiddle = iRecipientMiddle;
        this.iUserMiddle = iUserMiddle;
        this.iTriggerMiddle = iTriggerMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iContactMiddle = iContactMiddle;
        this.iCategoryMiddle = iCategoryMiddle;
        this.iTimerMiddle = iTimerMiddle;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createNoteRecipient(Map in) throws Exception {
        String token = in.get("token").toString();
        String email = in.get("email").toString();
        String noteId = in.get("noteId").toString();

        /**
         * 1、读取当前用户
         * 2、读取笔记
         * 3、读取email
         * 4、创建recipient表
         * 5、把该email加入到contact表
         */

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        /**
         * 查询该email是否已经是该笔记的接收人了
         */
        qIn = new HashMap();
        qIn.put("noteId", noteId);
        qIn.put("email", email);
        ArrayList<RecipientView> recipientViews = iRecipientMiddle.listNoteRecipient(qIn);
        if (recipientViews.size() > 0) {
            //该email已经被设置成接收人了
            throw new Exception("10050");
        }

        /**
         * 查询该email是否已经是联系人了
         */
        qIn = new HashMap();
        qIn.put("email", email);
        ContactView contactView = iContactMiddle.getContact(qIn, true, userView.getUserId());
        if (contactView == null) {
            /**
             * 创建联系人
             */
            Contact contact = new Contact();
            contact.setContactId(GogoTools.UUID32());
            contact.setUserId(userView.getUserId());
            contact.setEmail(email);
            iContactMiddle.createContact(contact);
        }

        /**
         * 创建接收人
         */
        NoteRecipient noteRecipient = new NoteRecipient();
        noteRecipient.setRecipientId(GogoTools.UUID32());
        noteRecipient.setNoteId(noteId);
        noteRecipient.setEmail(email);
        noteRecipient.setStatus(ESTags.ACTIVE.toString());
        noteRecipient.setTitle(noteView.getTitle());
        noteRecipient.setUserId(userView.getUserId());
        iRecipientMiddle.createNoteRecipient(noteRecipient);
    }

    @Override
    public void deleteNoteRecipient(Map in) throws Exception {
        String token = in.get("token").toString();
        String recipientId = in.get("recipientId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("recipientId", recipientId);
        iRecipientMiddle.deleteNoteRecipient(qIn);
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
             * 新增
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
             * 修改
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

    @Override
    public void addContactToRecipient(Map in) throws Exception {
        String token = in.get("token").toString();
        String contactId = in.get("contactId").toString();
        String noteId = in.get("noteId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);

        /**
         * 读取用户信息
         */
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * 读取联系人信息
         */
        qIn = new HashMap();
        qIn.put("contactId", contactId);
        ContactView contactView = iContactMiddle.getContact(qIn, false, userView.getUserId());

        /**
         * 读取笔记信息
         */
        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        /**
         * 关于接收人重复的问题
         * 重复就重复吧，用户自己判断，用户可以自行删除
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

    @Override
    public void addEmailToRecipient(Map in) throws Exception {
        String token = in.get("token").toString();
        String email = in.get("email").toString();
        String noteId = in.get("noteId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);

        /**
         * 读取用户信息
         */
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * 读取email
         */
        qIn = new HashMap();
        qIn.put("email", email);
        ContactView contactView = iContactMiddle.getContact(qIn, false, userView.getUserId());

        /**
         * 读取笔记信息
         */
        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        /**
         * 关于接收人重复的问题
         * 重复就重复吧，用户自己判断，用户可以自行删除
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

    private Map registerUser(Map in) throws Exception {
        String frontEnd = in.get("frontEnd").toString();
        /**
         * 直接生成一个临时账号
         */

        String userId = GogoTools.UUID32();
        String token = GogoTools.UUID32();

        /**
         * 创建userBase表
         */
        UserBase userBase = new UserBase();
        userBase.setUserId(userId);
        userBase.setCreateTime(new Date());
        //生成一个随机的用户昵称
        userBase.setNickname(GogoTools.generateString(8));
        iUserMiddle.createUserBase(userBase);

        /**
         * 创建默认笔记分类
         */
        Category category = new Category();
        category.setCategoryId(GogoTools.UUID32());
        category.setUserId(userId);
        category.setCategoryName(ESTags.DEFAULT.toString());
        category.setNoteType(ESTags.NORMAL.toString());
        iCategoryMiddle.createCategory(category);

        /**
         * 创建用户登录信息
         */
        UserLogin userLogin = new UserLogin();
        userLogin.setUserId(userId);
        userLogin.setToken(token);
        userLogin.setTokenTime(new Date());
        iUserMiddle.createUserLogin(userLogin);

        /**
         * 创建一个主计时器
         */
        Map map = iTimerMiddle.createUserTimer(userId);


        /**
         * 创建用户登录日志
         */
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUserId(userId);
        userLoginLog.setLoginTime(new Date());
        userLoginLog.setFrontEnd(frontEnd);
        iUserMiddle.createUserLoginLog(userLoginLog);

        /**
         * 返回临时用户信息
         */
        Map out = new HashMap();
        out.put("token", token);
        out.put("nickname", userBase.getNickname());
        out.put("defaultCategoryId", category.getCategoryId());
        out.put("defaultCategoryName", category.getCategoryName());
        out.put("timerPrimary", map.get("timerTime"));
        out.put("userId", userId);

        /**
         * 用户状态为USER_GUEST
         */
        out.put("userStatus", ESTags.USER_GUEST);
        return out;
    }
}

