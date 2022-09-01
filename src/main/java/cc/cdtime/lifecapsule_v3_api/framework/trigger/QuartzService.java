package cc.cdtime.lifecapsule_v3_api.framework.trigger;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmailView;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLog;
import cc.cdtime.lifecapsule_v3_api.meta.recipient.entity.RecipientView;
import cc.cdtime.lifecapsule_v3_api.meta.timer.entity.TimerView;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.TriggerView;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.noteSend.INoteSendMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.recipient.IRecipientMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.timer.ITimerMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.trigger.IAdminTriggerMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class QuartzService {
    private final IAdminTriggerMiddle iAdminTriggerMiddle;
    private final IUserMiddle iUserMiddle;
    private final IRecipientMiddle iRecipientMiddle;
    private final ITimerMiddle iTimerMiddle;
    private final INoteSendMiddle iNoteSendMiddle;
    private final INoteMiddle iNoteMiddle;

    public QuartzService(IAdminTriggerMiddle iAdminTriggerMiddle,
                         IUserMiddle iUserMiddle,
                         IRecipientMiddle iRecipientMiddle,
                         ITimerMiddle iTimerMiddle,
                         INoteSendMiddle iNoteSendMiddle,
                         INoteMiddle iNoteMiddle) {
        this.iAdminTriggerMiddle = iAdminTriggerMiddle;
        this.iUserMiddle = iUserMiddle;
        this.iRecipientMiddle = iRecipientMiddle;
        this.iTimerMiddle = iTimerMiddle;
        this.iNoteSendMiddle = iNoteSendMiddle;
        this.iNoteMiddle = iNoteMiddle;
    }

    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "*/10 * * * * ?")
    public void primaryTimerJob() throws Exception {

        log.info("primary timer trigger");
        Map qIn = new HashMap();
        qIn.put("triggerType", ESTags.TIMER_TYPE_PRIMARY);
        ArrayList<TriggerView> triggerViews = iAdminTriggerMiddle.adminListTrigger(qIn);
        for (int i = 0; i < triggerViews.size(); i++) {
            TriggerView triggerView = triggerViews.get(i);
            /**
             * userId是设置触发器的发送人
             * recipient是接收人，根据recipient表查询email，可以查询到接收人的userId
             * noteId就是要发送的笔记
             * recipient表也记录了发送人给接收人的部分信息
             *
             * 根据userId，查询user_timer表可获取发送人的主计时器时间timer_time
             * 计算timer_time是否已经触发，如果触发就发送笔记
             *
             * 站外发送：通过接口把笔记或者笔记链接发送给用户的email
             * 站内发送：增加一个note_trigger_log记录，把note content内容重新保存到content_detail表里，以避免用户修改。解密的key也需要复制一次
             * 把note笔记内容读出来，不用解密，在note_trigger_log表里
             */
            try {
                qIn = new HashMap();
                qIn.put("userId", triggerView.getUserId());
                qIn.put("type", ESTags.TIMER_TYPE_PRIMARY.toString());
                TimerView timerView = iTimerMiddle.getUserTimer(qIn, true);
                if (timerView != null) {
                    if (GogoTools.compare_date(new Date(), timerView.getTimerTime())) {
                        if (triggerView.getRecipientId() != null) {
                            RecipientView recipientView = iRecipientMiddle.getRecipientTiny(triggerView.getRecipientId(), true, null);
                            if (recipientView != null) {
                                if (recipientView.getEmail() != null) {
                                    qIn = new HashMap();
                                    qIn.put("email", recipientView.getEmail());
                                    UserEmailView userEmailView = iUserMiddle.getUserEmail(qIn, true, null);
                                    if (userEmailView != null) {
                                        NoteSendLog noteSendLog = new NoteSendLog();
                                        noteSendLog.setSendLogId(GogoTools.UUID32());
                                        noteSendLog.setSendTime(new Date());
                                        noteSendLog.setReceiveUserId(userEmailView.getUserId());
                                        noteSendLog.setSendUserId(triggerView.getUserId());
                                        noteSendLog.setRecipientId(recipientView.getRecipientId());
                                        noteSendLog.setTriggerType(ESTags.TIMER_TYPE_PRIMARY.toString());
                                        iNoteSendMiddle.createNoteSendLog(noteSendLog);
                                        log.info("create trigger log success" + userEmailView.getEmail());
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                log.error("send trigger log error:" + ex.getMessage());
            }
        }
    }

    /**
     * 发送用户指定时间的笔记
     *
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "*/10 * * * * ?")
    public void specificTimeJob() throws Exception {
        log.info("specific time trigger");
        log.info("primary timer trigger");
        Map qIn = new HashMap();
        qIn.put("triggerType", ESTags.TIMER_TYPE_DATETIME);
        qIn.put("status", ESTags.ACTIVE.toString());
        ArrayList<TriggerView> triggerViews = iAdminTriggerMiddle.adminListTrigger(qIn);
        for (int i = 0; i < triggerViews.size(); i++) {
            TriggerView triggerView = triggerViews.get(i);
            /**
             * userId是设置触发器的发送人
             * recipient是接收人，根据recipient表查询email，可以查询到接收人的userId
             * noteId就是要发送的笔记
             * recipient表也记录了发送人给接收人的部分信息
             * 计算timer_time是否已经触发，如果触发就发送笔记
             * 站外发送：通过接口把笔记或者笔记链接发送给用户的email
             * 站内发送：增加一个note_send_log记录，把note content内容重新保存到content_detail表里，以避免用户修改。解密的key也需要复制一次，保存在user_encode_key里
             * 把note笔记内容读出来，不用解密，在note_trigger_log表里
             */
            try {
                if (GogoTools.compare_date(new Date(), triggerView.getTriggerTime())) {
                    if (triggerView.getRecipientId() != null) {
                        RecipientView recipientView = iRecipientMiddle.getRecipientTiny(triggerView.getRecipientId(), true, null);
                        if (recipientView != null) {
                            if (recipientView.getEmail() != null) {
                                qIn = new HashMap();
                                qIn.put("email", recipientView.getEmail());
                                UserEmailView userEmailView = iUserMiddle.getUserEmail(qIn, true, null);
                                if (userEmailView != null) {
                                    /**
                                     * 发送笔记给接收用户
                                     */
                                    //读取笔记
                                    NoteView noteView = iNoteMiddle.getNoteDetail(triggerView.getNoteId(), true, triggerView.getUserId());
                                    if (noteView != null) {
                                        NoteSendLog noteSendLog = new NoteSendLog();
                                        noteSendLog.setSendLogId(GogoTools.UUID32());
                                        noteSendLog.setSendTime(new Date());
                                        noteSendLog.setReceiveUserId(userEmailView.getUserId());
                                        noteSendLog.setSendUserId(triggerView.getUserId());
                                        noteSendLog.setRecipientId(recipientView.getRecipientId());
                                        noteSendLog.setRecipientId(recipientView.getRecipientId());
                                        noteSendLog.setTriggerType(ESTags.TIMER_TYPE_DATETIME.toString());
                                        noteSendLog.setNoteContent(noteView.getContent());
                                        noteSendLog.setUserEncodeKey(noteView.getUserEncodeKey());
                                        iNoteSendMiddle.createNoteSendLog(noteSendLog);


                                        /**
                                         * 把trigger设置为已发送状态
                                         */
                                        qIn = new HashMap();
                                        int actTimes = 1;
                                        if (triggerView.getActTimes() != null) {
                                            actTimes += triggerView.getActTimes();
                                        }
                                        qIn.put("actTimes", actTimes);
                                        qIn.put("status", ESTags.SEND_COMPLETE.toString());
                                        qIn.put("triggerId", triggerView.getTriggerId());
                                        iAdminTriggerMiddle.updateNoteTrigger(qIn);

                                        log.info("create trigger log success--" + userEmailView.getEmail());
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                log.error("send trigger log error:" + ex.getMessage());
            }
        }
    }
}
