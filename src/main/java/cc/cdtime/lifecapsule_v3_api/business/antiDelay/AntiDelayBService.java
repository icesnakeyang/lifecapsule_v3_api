package cc.cdtime.lifecapsule_v3_api.business.antiDelay;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.antiDelay.entity.AntiDelayNote;
import cc.cdtime.lifecapsule_v3_api.meta.antiDelay.entity.AntiDelayView;
import cc.cdtime.lifecapsule_v3_api.meta.content.entity.Content;
import cc.cdtime.lifecapsule_v3_api.meta.creativeNote.entity.CreativeNote;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskTodo;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.TaskView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKeyView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.antiDelay.IAntiDelayMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.content.IContentMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.security.ISecurityMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.task.ITaskTodoMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserEncodeKeyMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AntiDelayBService implements IAntiDelayBService {
    private final IUserMiddle iUserMiddle;
    private final IAntiDelayMiddle iAntiDelayMiddle;
    private final ISecurityMiddle iSecurityMiddle;
    private final INoteMiddle iNoteMiddle;
    private final ITaskTodoMiddle iTaskTodoMiddle;
    private final IUserEncodeKeyMiddle iUserEncodeKeyMiddle;
    private final IContentMiddle iContentMiddle;

    public AntiDelayBService(IUserMiddle iUserMiddle,
                             IAntiDelayMiddle iAntiDelayMiddle,
                             ISecurityMiddle iSecurityMiddle,
                             INoteMiddle iNoteMiddle,
                             ITaskTodoMiddle iTaskTodoMiddle,
                             IUserEncodeKeyMiddle iUserEncodeKeyMiddle,
                             IContentMiddle iContentMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iAntiDelayMiddle = iAntiDelayMiddle;
        this.iSecurityMiddle = iSecurityMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iTaskTodoMiddle = iTaskTodoMiddle;
        this.iUserEncodeKeyMiddle = iUserEncodeKeyMiddle;
        this.iContentMiddle = iContentMiddle;
    }

    @Override
    public Map listMyAntiDelayNote(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        qIn.put("userId", userView.getUserId());
        qIn.put("noteType", ESTags.ANTI_DELAY_NOTE);
        ArrayList<NoteView> noteViews = iNoteMiddle.listNote(qIn);
        Integer totalNote = iNoteMiddle.totalNote(qIn);

        Map out = new HashMap();
        out.put("noteList", noteViews);
        out.put("totalNote", totalNote);
        return out;
    }


    /**
     * 创建一个防拖延笔记
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createAntiDelayNote(Map in) throws Exception {
        String token = (String) in.get("token");
        String encryptKey = in.get("encryptKey").toString();
        String keyToken = in.get("keyToken").toString();
        ArrayList<Map> tasksMap = (ArrayList<Map>) in.get("tasks");
        String happyYesterday = (String) in.get("happyYesterday");
        String longGoal = (String) in.get("longGoal");
        String todayGoal = (String) in.get("todayGoal");
        String title = (String) in.get("title");
        String myThought = (String) in.get("myThought");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String strAESKey = null;
        String privateKey = iSecurityMiddle.getRSAKey(keyToken);
        strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);
        iSecurityMiddle.deleteRSAKey(keyToken);
        /**
         * 创建防拖延笔记时，加密strAESKey是对整个笔记加密的，也就是说，happyYester，myThought等子项内容都是用同一个strAESKey加密的
         * 所以，userEncodeKey只保存一次就行了
         */

        /**
         * 新增笔记
         * 把方拖延的单项合并进笔记
         */
        NoteInfo note = new NoteInfo();
        note.setNoteId(GogoTools.UUID32());
        note.setUserId(userView.getUserId());
        note.setCreateTime(new Date());
        note.setTitle(title);
        note.setStatus(ESTags.ACTIVE.toString());
        String fullContent = happyYesterday + "\n" + myThought + "\n" + longGoal + "\n" + "todayGoal";
        note.setContent(fullContent);
        note.setUserEncodeKey(strAESKey);
        note.setNoteType(ESTags.ANTI_DELAY_NOTE.toString());
        //保存用户的AES私钥
        iNoteMiddle.createNoteInfo(note);

        AntiDelayNote antiDelayNote = new AntiDelayNote();
        antiDelayNote.setNoteId(note.getNoteId());

        //昨天高兴的事
        antiDelayNote.setAntiDelayType(ESTags.HAPPY_YESTERDAY.toString());
        antiDelayNote.setAntiDelayId(GogoTools.UUID32());
        antiDelayNote.setContent(happyYesterday);
        iAntiDelayMiddle.createAntiDelayNote(antiDelayNote);
        //我的感受
        antiDelayNote.setAntiDelayType(ESTags.MY_THOUGHT.toString());
        antiDelayNote.setAntiDelayId(GogoTools.UUID32());
        antiDelayNote.setContent(myThought);
        iAntiDelayMiddle.createAntiDelayNote(antiDelayNote);
        //长期目标
        antiDelayNote.setAntiDelayType(ESTags.LONG_GOAL.toString());
        antiDelayNote.setAntiDelayId(GogoTools.UUID32());
        antiDelayNote.setContent(longGoal);
        iAntiDelayMiddle.createAntiDelayNote(antiDelayNote);
        //今天要做的事情
        antiDelayNote.setAntiDelayType(ESTags.TODAY_GOAL.toString());
        antiDelayNote.setAntiDelayId(GogoTools.UUID32());
        antiDelayNote.setContent(todayGoal);
        iAntiDelayMiddle.createAntiDelayNote(antiDelayNote);

        /**
         * 新增就直接新增就行了
         */
        //遍历前端提交的任务，如果taskId==null，就新增
        if (tasksMap != null) {
            if (tasksMap.size() > 0) {
                createNew10SecTasks(tasksMap, userView.getUserId(), note.getNoteId());
            }
        }
    }

    /**
     * 修改防拖延笔记
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAntiDelayNote(Map in) throws Exception {
        String token = (String) in.get("token");
        String encryptKey = in.get("encryptKey").toString();
        String keyToken = in.get("keyToken").toString();
        ArrayList<Map> tasksMap = (ArrayList<Map>) in.get("tasks");
        String happyYesterday = (String) in.get("happyYesterday");
        String longGoal = (String) in.get("longGoal");
        String todayGoal = (String) in.get("todayGoal");
        String title = (String) in.get("title");
        String myThought = (String) in.get("myThought");
        String noteId = in.get("noteId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String strAESKey = null;
        String privateKey = iSecurityMiddle.getRSAKey(keyToken);
        strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);
        iSecurityMiddle.deleteRSAKey(keyToken);

        /**
         * 读取原笔记
         */
        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());
        qIn = new HashMap();
        qIn.put("noteId", noteId);
        ArrayList<AntiDelayView> antiDelayViews = iAntiDelayMiddle.listAntiDelayNote(qIn);
        if (antiDelayViews.size() == 0) {
            /**
             * 没有查询到防拖延笔记的内容
             */
            throw new Exception("10077");
        }
        /**
         * 修改noteInfo
         * 首先修改noteInfo的AES秘钥
         * 然后修改3个noteDetail
         * 最后修改10秒任务
         */

        qIn = new HashMap();
        qIn.put("noteId", noteId);
        qIn.put("title", title);
        qIn.put("encodeKey", strAESKey);
        iNoteMiddle.updateNoteInfo(qIn);

        for (int i = 0; i < antiDelayViews.size(); i++) {
            AntiDelayView antiDelayView = antiDelayViews.get(i);
            AntiDelayNote antiDelayNote = new AntiDelayNote();
            antiDelayNote.setAntiDelayId(antiDelayView.getAntiDelayId());
            if (antiDelayView.getAntiDelayType().equals(ESTags.HAPPY_YESTERDAY.toString())) {
                antiDelayNote.setContent(happyYesterday);
            } else {
                if (antiDelayView.getAntiDelayType().equals(ESTags.MY_THOUGHT.toString())) {
                    antiDelayNote.setContent(myThought);
                } else {
                    if (antiDelayView.getAntiDelayType().equals(ESTags.LONG_GOAL.toString())) {
                        antiDelayNote.setContent(longGoal);
                    } else {
                        if (antiDelayView.getAntiDelayType().equals(ESTags.TODAY_GOAL.toString())) {
                            antiDelayNote.setContent(todayGoal);
                        }
                    }
                }
            }
            iAntiDelayMiddle.updateAntiDelayNote(antiDelayNote);
        }

        /**
         * 修改todo任务
         * 直接删除noteId下的所有任务
         * 然后再新增任务
         */
        qIn = new HashMap();
        qIn.put("noteId", noteId);
        iTaskTodoMiddle.deleteTaskTodo(qIn);
        if (tasksMap != null) {
            if (tasksMap.size() > 0) {
                createNew10SecTasks(tasksMap, userView.getUserId(), noteId);
            }
        }
    }

    @Override
    public Map getMyAntiDelayNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();

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

        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        qIn.put("noteId", noteId);
        ArrayList<AntiDelayView> antiDelayViews = iAntiDelayMiddle.listAntiDelayNote(qIn);
        Map out = new HashMap();

        if (strAESKey == null) {
            //查询秘钥错误
            throw new Exception("10037");
        }
        UserEncodeKeyView userEncodeKeyView = iUserEncodeKeyMiddle.getUserEncodeKey(noteId);
        if (userEncodeKeyView == null) {
            throw new Exception("10037");
        }
        String data = userEncodeKeyView.getEncodeKey();
        //用AES秘钥加密笔记内容的AES秘钥
        String outCode = GogoTools.encryptAESKey(data, strAESKey);
        out.put("title", noteView.getTitle());
        out.put("noteId", noteId);
        out.put("userEncodeKey", outCode);

        /**
         * 读取子项
         */
        if (antiDelayViews.size() > 0) {
            for (int i = 0; i < antiDelayViews.size(); i++) {
                AntiDelayView antiDelayView = iAntiDelayMiddle.getAntiDelayNote(antiDelayViews.get(i).getAntiDelayId());
                if (antiDelayView.getAntiDelayType().equals(ESTags.HAPPY_YESTERDAY.toString())) {
                    Map happyYesterdayMap = new HashMap();
                    happyYesterdayMap.put("content", antiDelayView.getContent());
                    out.put("HAPPY_YESTERDAY", happyYesterdayMap);
                } else {
                    if (antiDelayView.getAntiDelayType().equals(ESTags.MY_THOUGHT.toString())) {
                        Map myThoughtMap = new HashMap();
                        myThoughtMap.put("content", antiDelayView.getContent());
                        out.put("MY_THOUGHT", myThoughtMap);
                    } else {
                        if (antiDelayView.getAntiDelayType().equals(ESTags.LONG_GOAL.toString())) {
                            Map longGoalMap = new HashMap();
                            longGoalMap.put("content", antiDelayView.getContent());
                            out.put("LONG_GOAL", longGoalMap);
                        } else {
                            if (antiDelayView.getAntiDelayType().equals(ESTags.TODAY_GOAL.toString())) {
                                Map todayGoalMap = new HashMap();
                                todayGoalMap.put("content", antiDelayView.getContent());
                                out.put("TODAY_GOAL", todayGoalMap);
                            }
                        }
                    }
                }
            }
        }

        /**
         * 读取todo任务列表
         */
        qIn = new HashMap();
        qIn.put("noteId", noteId);
        ArrayList<TaskView> taskViews = iTaskTodoMiddle.listTaskTodo(qIn);
        out.put("todoList", taskViews);

        return out;

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAntiDelayNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        iAntiDelayMiddle.deleteAntiDelayNote(noteView.getNoteId());
        iNoteMiddle.deleteNote(noteId);
    }

    @Override
    public Map loadLastMyAntiDelayNote(Map in) throws Exception {
        String token = in.get("token").toString();

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

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        qIn.put("noteType", ESTags.ANTI_DELAY_NOTE);
        qIn.put("size", 1);
        qIn.put("offset", 0);
        ArrayList<NoteView> noteViews = iNoteMiddle.listNote(qIn);

        Map out = new HashMap();
        if (noteViews.size() == 1) {

            String noteId = noteViews.get(0).getNoteId();
            qIn = new HashMap();
            qIn.put("noteId", noteId);
            ArrayList<AntiDelayView> antiDelayViews = iAntiDelayMiddle.listAntiDelayNote(qIn);
            ArrayList list = new ArrayList();
            for (int i = 0; i < antiDelayViews.size(); i++) {
                String type = antiDelayViews.get(i).getAntiDelayType();
                Content content = iContentMiddle.getContent(antiDelayViews.get(i).getAntiDelayId());
                String c = content.getContent();
                Map map = new HashMap();
                map.put("type", type);
                map.put("content", c);
                list.add(map);
            }
            out.put("subList", list);
            out.put("noteId", noteId);
            UserEncodeKeyView userEncodeKeyView = iUserEncodeKeyMiddle.getUserEncodeKey(noteId);
            if (userEncodeKeyView != null) {
                String data = userEncodeKeyView.getEncodeKey();
                //用AES秘钥加密笔记内容的AES秘钥
                String outCode = GogoTools.encryptAESKey(data, strAESKey);
                out.put("userEncodeKey", outCode);
            }
        }
        return out;
    }

    private void createNew10SecTasks(ArrayList<Map> tasksMap, String userId, String noteId) throws Exception {
        for (int j = 0; j < tasksMap.size(); j++) {
            String taskId = (String) tasksMap.get(j).get("taskId");
            if (taskId == null) {
                taskId=GogoTools.UUID32();
            }
            Map taskMap = tasksMap.get(j);
            String title = (String) taskMap.get("taskTitle");
            Boolean complete = (Boolean) taskMap.get("complete");
            if (complete == null) {
                complete = false;
            }
            if (title == null || title.equals("")) {
                //任务标题不能为空
                throw new Exception("10033");
            }
            TaskTodo taskTodo = new TaskTodo();
            taskTodo.setUserId(userId);
            taskTodo.setTaskTitle(title);
            taskTodo.setComplete(complete);
            taskTodo.setPriority(1);
            taskTodo.setCreateTime(new Date());
            taskTodo.setNoteId(noteId);
            taskTodo.setTaskId(taskId);
            taskTodo.setTaskType(ESTags.ACTION_10_SEC.toString());
            /**
             * 优先级默认为1，如果以后要增加优先级，就调大这个值，值越高，优先级越高
             */
            iTaskTodoMiddle.createTaskTodo(taskTodo);
        }
    }
}
