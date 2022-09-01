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
    SEND_COMPLETE
}
