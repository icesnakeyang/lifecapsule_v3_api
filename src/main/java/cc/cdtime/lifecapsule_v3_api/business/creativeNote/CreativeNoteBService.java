package cc.cdtime.lifecapsule_v3_api.business.creativeNote;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.category.entity.Category;
import cc.cdtime.lifecapsule_v3_api.meta.category.entity.CategoryView;
import cc.cdtime.lifecapsule_v3_api.meta.creativeNote.entity.CreativeNote;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.task.entity.Task;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.category.ICategoryMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.creativeNote.ICreativeNoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.security.ISecurityMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.task.ITaskMiddle;
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

    public CreativeNoteBService(ICreativeNoteMiddle iCreativeNoteMiddle,
                                IUserMiddle iUserMiddle,
                                ISecurityMiddle iSecurityMiddle,
                                INoteMiddle iNoteMiddle,
                                ITaskMiddle iTaskMiddle,
                                ICategoryMiddle iCategoryMiddle) {
        this.iCreativeNoteMiddle = iCreativeNoteMiddle;
        this.iUserMiddle = iUserMiddle;
        this.iSecurityMiddle = iSecurityMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iTaskMiddle = iTaskMiddle;
        this.iCategoryMiddle = iCategoryMiddle;
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
         * ???????????????????????????????????????AES?????????AES??????
         */
        String strAESKey = iSecurityMiddle.takeNoteAES(keyToken, encryptKey);

        /**
         * 1?????????token?????????????????????
         * 2?????????noteId?????????note??????
         * 3?????????note???userId???????????????????????????????????????????????????
         * 4?????????note?????????????????????????????????note
         */

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        //??????note??????
        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        Map out = new HashMap();
        out.put("note", noteView);

        /**
         * ???????????????????????????
         */
        qIn = new HashMap();
        qIn.put("noteId", noteId);
        ArrayList<CreativeNote> creativeNotes = iCreativeNoteMiddle.listCreativeNote(qIn);
        out.put("creativeNoteList", creativeNotes);

        //???AES???????????????????????????AES??????
        String data = noteView.getUserEncodeKey();
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
         * ??????token???????????????
         */
        if (token == null) {
            throw new Exception("10010");
        }
        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * ???????????????????????????????????????AES?????????AES??????
         */
        String strAESKey = null;
        if (keyToken != null) {
            strAESKey = iSecurityMiddle.takeNoteAES(keyToken, encryptKey);
        }

        /**
         * ????????????
         * 4??????????????????????????????????????????note?????????
         */
        ArrayList creativeNotes = new ArrayList();
        Map out = new HashMap();
        if (noteId != null) {
            /**
             * ???????????????
             */
            NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());
            qIn = new HashMap();
            qIn.put("noteId", noteId);
            creativeNotes = iCreativeNoteMiddle.listCreativeNote(qIn);
            if (creativeNotes.size() == 0) {
                /**
                 * ?????????????????????
                 */
                throw new Exception("10004");
            }
            /**
             * ??????noteInfo
             * ????????????noteInfo???AES??????
             * ????????????3???noteDetail
             * ????????????10?????????
             */
            qIn = new HashMap();
            qIn.put("noteId", noteView.getNoteId());
            qIn.put("userEncodeKey", strAESKey);
            qIn.put("title", noteTitle);
            iNoteMiddle.updateNoteInfo(qIn);

            out.put("note", noteView);

            for (int i = 0; i < creativeNotes.size(); i++) {
                CreativeNote creativeNote = (CreativeNote) creativeNotes.get(i);
                if (creativeNote.getCreativeType().equals(ESTags.CREATIVE1.toString())) {
                    /**
                     * ??????????????????
                     */
                    qIn = new HashMap();
                    qIn.put("content", detail1);
                    qIn.put("creativeNoteId", creativeNote.getCreativeNoteId());
                    iCreativeNoteMiddle.updateCreativeNoteDetail(qIn);
                }
                if (creativeNote.getCreativeType().equals(ESTags.CREATIVE2.toString())) {
                    /**
                     * ????????????
                     */
                    qIn = new HashMap();
                    qIn.put("content", detail2);
                    qIn.put("creativeNoteId", creativeNote.getCreativeNoteId());
                    iCreativeNoteMiddle.updateCreativeNoteDetail(qIn);
                }
                if (creativeNote.getCreativeType().equals(ESTags.CREATIVE3.toString())) {
                    /**
                     * ?????????????????????
                     */
                    qIn = new HashMap();
                    qIn.put("content", detail3);
                    qIn.put("creativeNoteId", creativeNote.getCreativeNoteId());
                    iCreativeNoteMiddle.updateCreativeNoteDetail(qIn);
                }
            }
            /**
             * ??????
             * ????????????noteId??????task?????????oldTasks
             * ??????oldTasks??tasks????????????taskId?????????????????????????????????
             * ?????????????????????tasks?????????taskId=null?????????
             */
            qIn.put("noteId", noteView.getNoteId());
            ArrayList<Task> tasksDB = iTaskMiddle.listTask(qIn);
            if (tasksDB.size() > 0) {
                //??????????????????????????????
                for (int iDB = 0; iDB < tasksDB.size(); iDB++) {
                    Task taskDB = tasksDB.get(iDB);
                    //???????????????????????????
                    int ss = 0;
                    for (int iSubmit = 0; iSubmit < tasksMap.size(); iSubmit++) {
                        Map taskMap = tasksMap.get(iSubmit);
                        String taskId = (String) taskMap.get("taskId");
                        if (taskId != null) {
                            if (taskId.equals(taskDB.getTaskId())) {
                                //??????
                                String title = (String) taskMap.get("taskTitle");
                                qIn.put("taskTitle", taskMap.get("taskTitle"));
                                qIn.put("status", taskMap.get("status"));

                                String taskStatus = (String) taskMap.get("status");
                                if (taskStatus.equals(ESTags.COMPLETE.toString())) {
                                    //???????????????
                                    if (taskDB.getStatus().equals(ESTags.COMPLETE.toString())) {
                                        //?????????????????????????????????????????????
                                    } else {
                                        //?????????????????????????????????????????????
                                        qIn.put("endTime", new Date());
                                        qIn.put("status", ESTags.COMPLETE);
                                        qIn.put("complete", true);
                                    }
                                } else {
                                    //?????????????????????
                                    if (taskStatus.equals(ESTags.PROGRESS.toString())) {
                                        //???????????????
                                        if (taskDB.getStatus().equals(ESTags.PROGRESS.toString())) {
                                            //??????????????????????????????????????????
                                        } else {
                                            //???????????????????????????????????????
                                            qIn.put("status", ESTags.PROGRESS);
                                            qIn.put("complete", false);
                                        }
                                    }
                                }
                                qIn.put("taskId", taskMap.get("taskId"));
                                iTaskMiddle.updateTask(qIn);

                                //???????????????ss+1
                                ss++;
                            }
                        }
                    }
                    if (ss == 0) {
                        //??????????????????????????????????????????task????????????
                        qIn = new HashMap();
                        qIn.put("taskId", taskDB.getTaskId());
                        iTaskMiddle.deleteTask(qIn);
                    }
                }
            }
            //????????????????????????????????????taskId==null????????????
            if (tasksMap != null && tasksMap.size() > 0) {
                createNew10SecTasks(tasksMap, userView.getUserId(), noteView.getNoteId());
            }
        } else {
            /**
             * ????????????
             */
            NoteInfo note = new NoteInfo();
            note.setNoteId(GogoTools.UUID32());
            note.setUserId(userView.getUserId());
            note.setCreateTime(new Date());
            note.setTitle(noteTitle);
            note.setStatus(ESTags.ACTIVE.toString());
            /**
             * ??????????????????????????????????????????????????????????????????
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

            //???????????????AES??????
            note.setUserEncodeKey(strAESKey);
            iNoteMiddle.createNoteInfo(note);

            out.put("note", note);

            CreativeNote creativeNote = new CreativeNote();
            creativeNote.setNoteId(note.getNoteId());
            //??????????????????
            creativeNote.setCreativeType(ESTags.CREATIVE1.toString());
            creativeNote.setCreativeNoteId(GogoTools.UUID32());
            creativeNote.setContent(detail1);
            iCreativeNoteMiddle.createCreativeNote(creativeNote);
            //????????????
            creativeNote.setCreativeType(ESTags.CREATIVE2.toString());
            creativeNote.setCreativeNoteId(GogoTools.UUID32());
            creativeNote.setContent(detail2);
            iCreativeNoteMiddle.createCreativeNote(creativeNote);
            //?????????????????????
            creativeNote.setCreativeType(ESTags.CREATIVE3.toString());
            creativeNote.setCreativeNoteId(GogoTools.UUID32());
            creativeNote.setContent(detail3);
            iCreativeNoteMiddle.createCreativeNote(creativeNote);

            /**
             * ??????????????????????????????
             */
            //????????????????????????????????????taskId==null????????????
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
         * 1?????????token?????????????????????
         * 2?????????noteId?????????note??????
         * 3?????????note???userId???????????????????????????????????????????????????
         * 4?????????note?????????????????????????????????note
         */

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        //??????note??????
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
                //????????????????????????
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
             * ??????????????????1????????????????????????????????????????????????????????????????????????????????????
             */
            task.setPriority(1);
            /**
             * ???????????????
             * ?????????????????????????????????
             */
            task.setImportant(ESTags.IMPORTANT_AND_URGENT.toString());
            iTaskMiddle.createTask(task);
        }
    }
}
