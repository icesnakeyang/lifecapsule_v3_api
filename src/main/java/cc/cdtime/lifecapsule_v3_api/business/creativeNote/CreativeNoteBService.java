package cc.cdtime.lifecapsule_v3_api.business.creativeNote;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.category.entity.Category;
import cc.cdtime.lifecapsule_v3_api.meta.category.entity.CategoryView;
import cc.cdtime.lifecapsule_v3_api.meta.creativeNote.entity.CreativeNote;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.Task;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKeyView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.category.ICategoryMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.creativeNote.ICreativeNoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.security.ISecurityMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.task.ITaskMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserEncodeKeyMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CreativeNoteBService implements ICreativeNoteBService {
    private final ICreativeNoteMiddle iCreativeNoteMiddle;
    private final IUserMiddle iUserMiddle;
    private final ISecurityMiddle iSecurityMiddle;
    private final INoteMiddle iNoteMiddle;
    private final ITaskMiddle iTaskMiddle;
    private final ICategoryMiddle iCategoryMiddle;
    private final IUserEncodeKeyMiddle iUserEncodeKeyMiddle;

    public CreativeNoteBService(ICreativeNoteMiddle iCreativeNoteMiddle,
                                IUserMiddle iUserMiddle,
                                ISecurityMiddle iSecurityMiddle,
                                INoteMiddle iNoteMiddle,
                                ITaskMiddle iTaskMiddle,
                                ICategoryMiddle iCategoryMiddle,
                                IUserEncodeKeyMiddle iUserEncodeKeyMiddle) {
        this.iCreativeNoteMiddle = iCreativeNoteMiddle;
        this.iUserMiddle = iUserMiddle;
        this.iSecurityMiddle = iSecurityMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iTaskMiddle = iTaskMiddle;
        this.iCategoryMiddle = iCategoryMiddle;
        this.iUserEncodeKeyMiddle = iUserEncodeKeyMiddle;
    }

    @Override
    public Map listCreativeNote(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        qIn.put("noteType", ESTags.CREATIVE_NOTE);
        ArrayList<CategoryView> categories = iCategoryMiddle.listCategory(qIn);

        Map out = new HashMap();

        if (categories.size() > 0) {
            qIn = new HashMap();
            qIn.put("userId", userView.getUserId());
            qIn.put("categoryId", categories.get(0).getCategoryId());
            Integer offset = (pageIndex - 1) * pageSize;
            qIn.put("offset", offset);
            qIn.put("size", pageSize);
            ArrayList<NoteView> noteViews = iNoteMiddle.listNote(qIn);
            out.put("creativeNoteList", noteViews);
            Integer total = iNoteMiddle.totalNote(qIn);
            out.put("totalCreativeNote", total);
        }
        return out;
    }

    @Override
    public Map getCreativeNote(Map in) throws Exception {
        String token = (String) in.get("token");
        String noteId = in.get("noteId").toString();
        String encryptKey = in.get("encryptKey").toString();
        String keyToken = (String) in.get("keyToken");

        /**
         * 获取用户临时上传的加密笔记AES秘钥的AES秘钥
         */
        String strAESKey = iSecurityMiddle.takeNoteAES(keyToken, encryptKey);

        /**
         * 1、检查token，查询登录用户
         * 2、根据noteId，查询note详情
         * 3、比较note的userId是否是登录用户，如果不是，返回错误
         * 4、如果note是当前用户创建的，返回note
         */

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        //查询note简介
        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        Map out = new HashMap();
        out.put("note", noteView);

        /**
         * 读取创新防拖延笔记
         */
        qIn = new HashMap();
        qIn.put("noteId", noteId);
        ArrayList<CreativeNote> creativeNotes = iCreativeNoteMiddle.listCreativeNote(qIn);
        out.put("creativeNoteList", creativeNotes);

        //用AES秘钥加密笔记内容的AES秘钥
        String data = noteView.getUserEncodeKey();
        if (data == null) {
            qIn = new HashMap();
            qIn.put("indexId", creativeNotes.get(0).getCreativeNoteId());
            UserEncodeKeyView userEncodeKeyView = iUserEncodeKeyMiddle.getUserEncodeKey(qIn);
            data = userEncodeKeyView.getEncodeKey();
        }
        String outCode = GogoTools.encryptAESKey(data, strAESKey);
        noteView.setUserEncodeKey(outCode);

        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map saveCreativeNote(Map in) throws Exception {
        String token = (String) in.get("token");
        String detail1 = (String) in.get("detail1");
        String detail2 = (String) in.get("detail2");
        String detail3 = (String) in.get("detail3");
        String noteId = (String) in.get("noteId");
        String encryptKey = in.get("encryptKey").toString();
        String keyToken = in.get("keyToken").toString();
        ArrayList<Map> tasksMap = (ArrayList<Map>) in.get("tasks");
        String noteTitle = (String) in.get("noteTitle");

        /**
         * 根据token取用户信息
         */
        if (token == null) {
            throw new Exception("10010");
        }
        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * 获取用户临时上传的加密笔记AES秘钥的AES秘钥
         */
        String strAESKey = null;
        if (keyToken != null) {
            strAESKey = iSecurityMiddle.takeNoteAES(keyToken, encryptKey);
        }

        /**
         * 保存笔记
         * 4个方拖延子笔记，仍然需要一个note父笔记
         */
        ArrayList creativeNotes = new ArrayList();
        Map out = new HashMap();
        if (noteId != null) {
            /**
             * 读取原笔记
             */
            NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());
            qIn = new HashMap();
            qIn.put("noteId", noteId);
            creativeNotes = iCreativeNoteMiddle.listCreativeNote(qIn);
            if (creativeNotes.size() == 0) {
                /**
                 * 笔记内容不存在
                 */
                throw new Exception("10004");
            }
            /**
             * 修改noteInfo
             * 首先修改noteInfo的AES秘钥
             * 然后修改3个noteDetail
             * 最后修改10秒任务
             */
            qIn = new HashMap();
            qIn.put("noteId", noteView.getNoteId());
            qIn.put("title", noteTitle);
            iNoteMiddle.updateNoteInfo(qIn);

            out.put("note", noteView);

            for (int i = 0; i < creativeNotes.size(); i++) {
                CreativeNote creativeNote = (CreativeNote) creativeNotes.get(i);
                if (creativeNote.getCreativeType().equals(ESTags.CREATIVE1.toString())) {
                    /**
                     * 昨天高兴的事
                     */
                    qIn = new HashMap();
                    qIn.put("content", detail1);
                    qIn.put("creativeNoteId", creativeNote.getCreativeNoteId());
                    qIn.put("userEncodeKey", strAESKey);
                    iCreativeNoteMiddle.updateCreativeNoteDetail(qIn);
                }
                if (creativeNote.getCreativeType().equals(ESTags.CREATIVE2.toString())) {
                    /**
                     * 我的感想
                     */
                    qIn = new HashMap();
                    qIn.put("content", detail2);
                    qIn.put("creativeNoteId", creativeNote.getCreativeNoteId());
                    qIn.put("userEncodeKey", strAESKey);
                    iCreativeNoteMiddle.updateCreativeNoteDetail(qIn);
                }
                if (creativeNote.getCreativeType().equals(ESTags.CREATIVE3.toString())) {
                    /**
                     * 今天要做的事情
                     */
                    qIn = new HashMap();
                    qIn.put("content", detail3);
                    qIn.put("creativeNoteId", creativeNote.getCreativeNoteId());
                    qIn.put("userEncodeKey", strAESKey);
                    iCreativeNoteMiddle.updateCreativeNoteDetail(qIn);
                }
            }
            /**
             * 修改
             * 首先根据noteId读出task列表，oldTasks
             * 遍历oldTasks×tasks，如果有taskId，修改，如果没有就删除
             * 遍历前端提交的tasks，如果taskId=null，新增
             */
            qIn.put("noteId", noteView.getNoteId());
            ArrayList<Task> tasksDB = iTaskMiddle.listTask(qIn);
            if (tasksDB.size() > 0) {
                //遍历数据库里旧的任务
                for (int iDB = 0; iDB < tasksDB.size(); iDB++) {
                    Task taskDB = tasksDB.get(iDB);
                    //遍历前端提交的任务
                    int ss = 0;
                    for (int iSubmit = 0; iSubmit < tasksMap.size(); iSubmit++) {
                        Map taskMap = tasksMap.get(iSubmit);
                        String taskId = (String) taskMap.get("taskId");
                        if (taskId != null) {
                            if (taskId.equals(taskDB.getTaskId())) {
                                //修改
                                String title = (String) taskMap.get("taskTitle");
                                qIn.put("taskTitle", taskMap.get("taskTitle"));
                                qIn.put("status", taskMap.get("status"));

                                String taskStatus = (String) taskMap.get("status");
                                if (taskStatus.equals(ESTags.COMPLETE.toString())) {
                                    //任务已完成
                                    if (taskDB.getStatus().equals(ESTags.COMPLETE.toString())) {
                                        //数据库是已完成状态，不需要修改
                                    } else {
                                        //数据库是未完成状态，修改为完成
                                        qIn.put("endTime", new Date());
                                        qIn.put("status", ESTags.COMPLETE);
                                        qIn.put("complete", true);
                                    }
                                } else {
                                    //任务非完成状态
                                    if (taskStatus.equals(ESTags.PROGRESS.toString())) {
                                        //任务进行中
                                        if (taskDB.getStatus().equals(ESTags.PROGRESS.toString())) {
                                            //数据库也是进行中，不需要修改
                                        } else {
                                            //数据库不是进行中，需要修改
                                            qIn.put("status", ESTags.PROGRESS);
                                            qIn.put("complete", false);
                                        }
                                    }
                                }
                                qIn.put("taskId", taskMap.get("taskId"));
                                iTaskMiddle.updateTask(qIn);

                                //匹配成功，ss+1
                                ss++;
                            }
                        }
                    }
                    if (ss == 0) {
                        //遍历完所有提交的任务，没有该task，则删除
                        qIn = new HashMap();
                        qIn.put("taskId", taskDB.getTaskId());
                        iTaskMiddle.deleteTask(qIn);
                    }
                }
            }
            //遍历前端提交的任务，如果taskId==null，就新增
            if (tasksMap != null && tasksMap.size() > 0) {
                createNew10SecTasks(tasksMap, userView.getUserId(), noteView.getNoteId());
            }
        } else {
            /**
             * 新增笔记
             */
            NoteInfo note = new NoteInfo();
            note.setNoteId(GogoTools.UUID32());
            note.setUserId(userView.getUserId());
            note.setCreateTime(new Date());
            note.setTitle(noteTitle);
            note.setStatus(ESTags.ACTIVE.toString());
            /**
             * 读取用户的行动笔记分类，如果没有，就创建一个
             */
            Map qIn2 = new HashMap();
            qIn2.put("noteType", ESTags.CREATIVE_NOTE);
            qIn2.put("userId", userView.getUserId());
            CategoryView categoryView = iCategoryMiddle.getCategory(qIn2, true, userView.getUserId());
            if (categoryView == null) {
                Category category = new Category();
                category.setNoteType(ESTags.CREATIVE_NOTE.toString());
                category.setCategoryName(ESTags.CREATIVE_NOTE.toString());
                category.setUserId(userView.getUserId());
                category.setCategoryId(GogoTools.UUID32());
                iCategoryMiddle.createCategory(category);
                note.setCategoryId(category.getCategoryId());
            } else {
                note.setCategoryId(categoryView.getCategoryId());
            }

            //保存用户的AES私钥
            iNoteMiddle.createNoteInfo(note);

            out.put("note", note);

            CreativeNote creativeNote = new CreativeNote();
            creativeNote.setNoteId(note.getNoteId());
            //昨天高兴的事
            creativeNote.setCreativeType(ESTags.CREATIVE1.toString());
            creativeNote.setCreativeNoteId(GogoTools.UUID32());
            creativeNote.setContent(detail1);
            creativeNote.setUserEncodeKey(strAESKey);
            iCreativeNoteMiddle.createCreativeNote(creativeNote);
            //我的感受
            creativeNote.setCreativeType(ESTags.CREATIVE2.toString());
            creativeNote.setCreativeNoteId(GogoTools.UUID32());
            creativeNote.setContent(detail2);
            creativeNote.setUserEncodeKey(strAESKey);
            iCreativeNoteMiddle.createCreativeNote(creativeNote);
            //今天要做的事情
            creativeNote.setCreativeType(ESTags.CREATIVE3.toString());
            creativeNote.setCreativeNoteId(GogoTools.UUID32());
            creativeNote.setContent(detail3);
            creativeNote.setUserEncodeKey(strAESKey);
            iCreativeNoteMiddle.createCreativeNote(creativeNote);

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
        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCreativeNote(Map in) throws Exception {
        String token = (String) in.get("token");
        String noteId = in.get("noteId").toString();

        /**
         * 1、检查token，查询登录用户
         * 2、根据noteId，查询note详情
         * 3、比较note的userId是否是登录用户，如果不是，返回错误
         * 4、如果note是当前用户创建的，返回note
         */

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        //查询note简介
        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        iNoteMiddle.deleteNote((noteId));

        iCreativeNoteMiddle.deleteCreativeNote(noteId);
    }

    private void createNew10SecTasks(ArrayList<Map> tasksMap, String userId, String noteId) throws Exception {
        for (int j = 0; j < tasksMap.size(); j++) {
            String taskId = (String) tasksMap.get(j).get("taskId");
            if (taskId != null) {
                continue;
            }
            Map taskMap = tasksMap.get(j);
            String taskTitle = (String) taskMap.get("taskTitle");
            if (taskTitle == null || taskTitle.equals("")) {
                //任务标题不能为空
                throw new Exception("10033");
            }
            Boolean complete = (Boolean) taskMap.get("complete");
            Task task = new Task();
            task.setCreateTime(new Date());
            task.setUserId(userId);
            task.setNoteId(noteId);
            task.setStatus(ESTags.PROGRESS.toString());
            task.setTaskId(GogoTools.UUID32());
            task.setTaskTitle(taskTitle);
            task.setTaskType(ESTags.ACTION_10_SEC.toString());
            /**
             * 优先级默认为1，如果以后要增加优先级，就调大这个值，值越高，优先级越高
             */
            task.setPriority(1);
            /**
             * 任务的象限
             * 默认为重要且紧急的任务
             */
            task.setImportant(ESTags.IMPORTANT_AND_URGENT.toString());
            iTaskMiddle.createTask(task);
        }
    }
}
