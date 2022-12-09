package cc.cdtime.lifecapsule_v3_api.framework.constant;

public enum ESTags {
    SUCCESS,
    FAIL,
    USER_LOGIN,
    USER_REGISTER,
    DEFAULT,
    NORMAL,
    ACTIVE,
    TIMER_TYPE_PRIMARY,
    TIMER_TYPE_DATETIME,
    INSTANT_MESSAGE,
    NO_TRIGGER,
    ANONYMOUS,
    ADMIN_ROOT,
    CREATIVE1,
    CREATIVE2,
    CREATIVE3,
    /**
     * 10秒行动任务
     */
    ACTION_10_SEC,
    /**
     * 重要且紧急
     */
    IMPORTANT_AND_URGENT,
    /**
     * 重要但不紧急
     */
    IMPORTANT_NOT_URGENT,
    /**
     * 紧急但不重要
     */
    URGENT_NOT_IMPORTANT,
    /**
     * 不重要不紧急
     */
    NOTHING,
    CREATIVE_NOTE,
    COMPLETE,
    PROGRESS,
    /**
     * 四象限任务
     */
    TASK_QUAD,
    /**
     * 使命任务
     */
    TASK_MIND,
    /**
     * 待办任务
     */
    TASK_TODO,
    /**
     * 网页端用户
     */
    WEB_CLIENT,
    /**
     * 移动端用户
     */
    MOBILE_CLIENT,
    /**
     * 游客，没有进行email验证
     */
    USER_GUEST,
    /**
     * 已经email验证
     */
    USER_NORMAL,
    /**
     * 已经发送过了
     */
    SEND_COMPLETE,
    /**
     * 笔记
     */
    MY_NOTE_INFO,
    /**
     * 发送的笔记
     */
    NOTE_SEND_LOG,
    /**
     * 论坛里的笔记
     */
    FORUM_NOTE,
    /**
     * 回复发送给我的笔记
     */
    REPLY_SEND_LOG,
    ADMIN_REMOVE,
    /**
     * 查看话题
     */
    READ_TOPIC,
    USER_LIST_HISTORY,
    USER_READ_NOTE,
    USER_LIST_MYNOTE,
    USER_LIKE_MOTTO,
    STOP,
    MAIL_TYPE_VALIDATE,
    MAIL_TYPE_NOTE_SEND,
    USER_LIST_RECEIVE_NOTE,
    USER_SAVE_NOTE,
    USER_GET_NOTE,
    CASH_CREATE_LEDGER,
    USER_LIST_CASH_LEDGER,
    USER_GET_CASH_LEDGER,
    UPDATE_CASH_LEDGER,
    USER_CREATE_TASK_TODO,
    USER_UPDATE_TASK_TODO,
    USER_CREATE_TRIGGER_INSTANT,
    USER_LIST_TRIGGER,
    LOGIN_BY_LOGIN_NAME,
    USER_SET_LOGIN_NAME_PASSWORD,
    USER_LIST_ANTI_DELAY_NOTE,
    HAPPY_YESTERDAY,
    LONG_GOAL,
    TODAY_GOAL,
    MY_THOUGHT,
    ANTI_DELAY_NOTE,
    USER_CREATE_ANTI_DELAY_NOTE,
    USER_UPDATE_ANTI_DELAY_NOTE,
    USER_GET_ANTI_DELAY_NOTE,
    USER_DELETE_ANTI_DELAY_NOTE,
    CASH_LEDGER_STATISTIC
}
