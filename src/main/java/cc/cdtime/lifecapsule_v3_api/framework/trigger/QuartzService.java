package cc.cdtime.lifecapsule_v3_api.framework.trigger;

import cc.cdtime.lifecapsule_v3_api.framework.common.email.IEmailToolService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmailView;
import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLog;
import cc.cdtime.lifecapsule_v3_api.meta.timer.entity.TimerView;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.TriggerView;
import cc.cdtime.lifecapsule_v3_api.middle.noteSend.INoteSendMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.timer.ITimerMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.trigger.IAdminTriggerMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import lombok.extern.slf4j.Slf4j;
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
    private final ITimerMiddle iTimerMiddle;
    private final INoteSendMiddle iNoteSendMiddle;
    private final IEmailToolService iEmailToolService;

    public QuartzService(IAdminTriggerMiddle iAdminTriggerMiddle,
                         IUserMiddle iUserMiddle,
                         ITimerMiddle iTimerMiddle,
                         INoteSendMiddle iNoteSendMiddle,
                         IEmailToolService iEmailToolService) {
        this.iAdminTriggerMiddle = iAdminTriggerMiddle;
        this.iUserMiddle = iUserMiddle;
        this.iTimerMiddle = iTimerMiddle;
        this.iNoteSendMiddle = iNoteSendMiddle;
        this.iEmailToolService = iEmailToolService;
    }

    /**
     * 每小时检查一次所有的倒计时发送的笔记
     *
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 */5 * * * ?")
    public void primaryTimerJob() throws Exception {
        log.info("primary timer trigger");

        /**
         * 读取所有的倒计时发送笔记
         */
        Map qIn = new HashMap();
        qIn.put("triggerType", ESTags.TIMER_TYPE_PRIMARY);
        qIn.put("status", ESTags.ACTIVE);
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
                /**
                 * 读取trigger用户的倒计时时间
                 */
                qIn = new HashMap();
                qIn.put("userId", triggerView.getUserId());
                qIn.put("type", ESTags.TIMER_TYPE_PRIMARY.toString());
                TimerView timerView = iTimerMiddle.getUserTimer(qIn, true);
                if (timerView != null) {
                    /**
                     * 检查是否触发
                     */
                    if (GogoTools.compare_date(new Date(), timerView.getTimerTime())) {
                        /**
                         * 已触发，发送
                         */
                        sendNote(triggerView);
                    }
                }
            } catch (Exception ex) {
                log.error("Send primary trigger error:" + ex.getMessage());
            }
        }
    }

    /**
     * 发送用户指定时间的笔记
     *
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 */5 * * * ?")
    public void specificTimeJob() throws Exception {
        log.info("specific time trigger");
        Map qIn = new HashMap();
        qIn.put("triggerType", ESTags.TIMER_TYPE_DATETIME);
        qIn.put("status", ESTags.ACTIVE.toString());
        ArrayList<TriggerView> triggerViews = iAdminTriggerMiddle.adminListTrigger(qIn);
        for (int i = 0; i < triggerViews.size(); i++) {
            TriggerView triggerView = triggerViews.get(i);
            /**
             * 1、通过toEmail，或者toUserId获取接收人用户id
             * 2、创建note_send_log表
             * 3、修改trigger表的状态为已发送
             */
            try {
                if (GogoTools.compare_date(new Date(), triggerView.getTriggerTime())) {
                    sendNote(triggerView);
                }
            } catch (Exception ex) {
                log.error("send specific time trigger log error:" + ex.getMessage());
            }
        }
    }

    /**
     * 发送即时消息
     *
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 */5 * * * ?")
    public void instantMessageJob() throws Exception {
        log.info("Send instant message");
        Map qIn = new HashMap();
        qIn.put("triggerType", ESTags.INSTANT_MESSAGE);
        qIn.put("status", ESTags.ACTIVE.toString());
        ArrayList<TriggerView> triggerViews = iAdminTriggerMiddle.adminListTrigger(qIn);
        for (int i = 0; i < triggerViews.size(); i++) {
            TriggerView triggerView = triggerViews.get(i);
            try {
                sendNote(triggerView);
            } catch (Exception ex) {
                log.error("send instant message error:" + ex.getMessage());
            }
        }
    }

    private void sendNote(TriggerView triggerView) throws Exception {
        String toUserId = null;
        if (triggerView.getToUserId() != null) {
            toUserId = triggerView.getToUserId();
        } else {
            /**
             * toUserId为空
             */
            if (triggerView.getToEmail() != null) {

                Map qIn = new HashMap();
                qIn.put("email", triggerView.getToEmail());
                UserEmailView userEmailView = iUserMiddle.getUserEmail(qIn, true, null);
                if (userEmailView != null) {
                    toUserId = userEmailView.getUserId();
                }
                if (triggerView.getToEmailStatus() == null) {
                    /**
                     * 还没发送email，先发送
                     * 调用email接口，发送email
                     */
                    try {
                        sendEmail(qIn, triggerView);
                    } catch (Exception ex) {
                        log.error("Send mail to " + triggerView.getToEmail() + " failed!");
                    }
                } else {
                    /**
                     * 已发送
                     */
                }
                /**
                 *
                 */
            }
        }
        if (toUserId != null) {
            if (triggerView.getToUserStatus() == null) {
                /**
                 * 还没有发送站内消息，创建noteSendLog
                 */
                try {
                    sendInnerMessage(triggerView, toUserId);
                } catch (Exception ex) {
                    log.error("Send message to user " + toUserId + " failed!");
                }
            }
        }

        if (triggerView.getToEmailStatus() != null && triggerView.getToUserStatus() != null) {
            if (triggerView.getToUserStatus().equals(ESTags.SEND_COMPLETE.toString())) {
                if (triggerView.getToEmailStatus().equals(ESTags.SEND_COMPLETE.toString())) {
                    /**
                     * 把trigger设置为已发送状态
                     */
                    Map qIn = new HashMap();
                    int actTimes = 1;
                    if (triggerView.getActTimes() != null) {
                        actTimes += triggerView.getActTimes();
                    }
                    qIn.put("actTimes", actTimes);
                    qIn.put("status", ESTags.SEND_COMPLETE.toString());
                    qIn.put("triggerId", triggerView.getTriggerId());
                    iAdminTriggerMiddle.updateNoteTrigger(qIn);
                }
            }
        }
    }

    private void sendEmail(Map qIn, TriggerView triggerView) throws Exception {
        qIn.put("subject", triggerView.getTitle());
        qIn.put("userName", triggerView.getFromName());
        qIn.put("toName", triggerView.getToName());
        qIn.put("urlLink", "urllink");
        qIn.put("decode", "decode");
        qIn.put("mailType", ESTags.MAIL_TYPE_NOTE_SEND);
        qIn.put("triggerId", triggerView.getTriggerId());
        iEmailToolService.sendMail(qIn);
        /**
         * 修改note_trigger.to_email_status=SEND_COMPLETE
         */
        qIn = new HashMap();
        qIn.put("toEmailStatus", ESTags.SEND_COMPLETE.toString());
        qIn.put("triggerId", triggerView.getTriggerId());
        iAdminTriggerMiddle.updateNoteTrigger(qIn);
        log.info("Send message to user " + triggerView.getToEmail() + " success");
    }

    private void sendInnerMessage(TriggerView triggerView, String toUserId) throws Exception {
        NoteSendLog noteSendLog = new NoteSendLog();
        noteSendLog.setSendLogId(GogoTools.UUID32());
        noteSendLog.setSendTime(new Date());
        noteSendLog.setReceiveUserId(toUserId);
        noteSendLog.setSendUserId(triggerView.getUserId());
        noteSendLog.setTriggerType(triggerView.getTriggerType());
        noteSendLog.setTriggerId(triggerView.getTriggerId());
        noteSendLog.setToEmail(triggerView.getToEmail());
        noteSendLog.setTitle(triggerView.getTitle());
        noteSendLog.setFromName(triggerView.getFromName());
        noteSendLog.setRefPid(triggerView.getRefPid());
        noteSendLog.setToName(triggerView.getToName());
        iNoteSendMiddle.createNoteSendLog(noteSendLog);

        /**
         * 修改note_trigger.to_email_status=SEND_COMPLETE
         */
        Map qIn = new HashMap();
        qIn.put("toUserStatus", ESTags.SEND_COMPLETE.toString());
        qIn.put("triggerId", triggerView.getTriggerId());
        iAdminTriggerMiddle.updateNoteTrigger(qIn);
        log.info("Send message to user " + toUserId + " success");
    }
}
