package cc.cdtime.lifecapsule_v3_api.business.note;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.category.entity.Category;
import cc.cdtime.lifecapsule_v3_api.meta.category.entity.CategoryView;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
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
public class NoteBService implements INoteBService {
    private final IUserMiddle iUserMiddle;
    private final INoteMiddle iNoteMiddle;
    private final ISecurityMiddle iSecurityMiddle;
    private final ICategoryMiddle iCategoryMiddle;
    private final ICreativeNoteMiddle iCreativeNoteMiddle;
    private final ITaskMiddle iTaskMiddle;

    public NoteBService(IUserMiddle iUserMiddle,
                        INoteMiddle iNoteMiddle,
                        ISecurityMiddle iSecurityMiddle,
                        ICategoryMiddle iCategoryMiddle,
                        ICreativeNoteMiddle iCreativeNoteMiddle,
                        ITaskMiddle iTaskMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iSecurityMiddle = iSecurityMiddle;
        this.iCategoryMiddle = iCategoryMiddle;
        this.iCreativeNoteMiddle = iCreativeNoteMiddle;
        this.iTaskMiddle = iTaskMiddle;
    }

    @Override
    public Map listNote(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");
        String categoryId = (String) in.get("categoryId");
        String keyword = (String) in.get("keyword");

        Map qIn = new HashMap();
        qIn.put("token", token);
        /**
         * ????????????????????????????????????????????????????????????
         */
        UserView userView = iUserMiddle.getUserLogin(qIn, false);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        if (categoryId != null) {
            qIn.put("categoryId", categoryId);
        }
        if (keyword != null && !keyword.equals("")) {
            qIn.put("keyword", keyword);
        }
        ArrayList<NoteView> noteViews = iNoteMiddle.listNote(qIn);
        Integer total = iNoteMiddle.totalNote(qIn);

        Map out = new HashMap();
        out.put("noteList", noteViews);
        out.put("totalNote", total);

        return out;
    }

    @Override
    public Map getMyNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();
        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");

        /**
         * ???????????????????????????????????????AES?????????AES??????
         */
        String strAESKey = null;
        if (keyToken != null) {
            strAESKey = iSecurityMiddle.takeNoteAES(keyToken, encryptKey);
        }

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteDetail(noteId, false, userView.getUserId());

        if (noteView.getEncrypt() != null && noteView.getEncrypt() == 1) {
            if(strAESKey==null){
                //??????????????????
                throw new Exception("10037");
            }
            //???AES???????????????????????????AES??????
            String data = noteView.getUserEncodeKey();
            String outCode = GogoTools.encryptAESKey(data, strAESKey);
            noteView.setUserEncodeKey(outCode);
        } else {
            noteView.setUserEncodeKey(null);
        }

        Map out = new HashMap();
        out.put("note", noteView);

        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = (String) in.get("noteId");
        String title = (String) in.get("title");
        String content = (String) in.get("content");
        Integer encrypt = (Integer) in.get("encrypt");
        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");
        String categoryId = (String) in.get("categoryId");

        /**
         * ??????keyToken????????????
         * ?????????????????????????????????????????????AES??????
         */
        String strAESKey = null;
        if (encrypt != null && encrypt == 1) {
            String privateKey = iSecurityMiddle.getRSAKey(keyToken);
            strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);
            iSecurityMiddle.deleteRSAKey(keyToken);
        }

        /**
         * ????????????????????????
         */
        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUserLogin(qIn, false);
        in.put("userId", userView.getUserId());
        if (noteId == null) {
            /**
             * ??????
             */
            if (strAESKey != null) {
                in.put("strAESKey", strAESKey);
            }
            if (categoryId == null) {
                /**
                 * ?????????????????????????????????
                 */
                CategoryView categoryView = iCategoryMiddle.getDefaultCategory(userView.getUserId());
                in.put("categoryId", categoryView.getCategoryId());
            }
            createNote(in);
        } else {
            /**
             * ??????
             */
            in.put("strAESKey", strAESKey);
            updateNote(in);
        }
    }

    @Override
    public Map totalNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String categoryId = (String) in.get("categoryId");
        String keyword = (String) in.get("keyword");

        Map qIn = new HashMap();
        qIn.put("token", token);
        /**
         * ????????????????????????????????????????????????????????????
         */
        UserView userView = iUserMiddle.getUserLogin(qIn, false);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        if (categoryId != null) {
            qIn.put("categoryId", categoryId);
        }
        if (keyword != null && !keyword.equals("")) {
            qIn.put("keyword", keyword);
        }
        Integer total = iNoteMiddle.totalNote(qIn);

        Map out = new HashMap();
        out.put("totalNote", total);

        return out;
    }

    @Override
    public void deleteNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        iNoteMiddle.deleteNote(noteId);

        /**
         * ?????????????????????????????????????????????????????????creativeNote???task
         */
        iCreativeNoteMiddle.deleteCreativeNote(noteView.getNoteId());
        qIn = new HashMap();
        qIn.put("noteId", noteId);
        iTaskMiddle.deleteTask(qIn);
    }

    private void createNote(Map in) throws Exception {
        String content = in.get("content").toString();
        String categoryId = in.get("categoryId").toString();
        String userId = in.get("userId").toString();
        Integer encrypt = (Integer) in.get("encrypt");
        String strAESKey = (String) in.get("strAESKey");
        String title = (String) in.get("title");

        NoteInfo noteInfo = new NoteInfo();
        noteInfo.setNoteId(GogoTools.UUID32());
        noteInfo.setContent(content);
        if (categoryId == null) {
            /**
             * ??????categoryId??????????????????
             */
            Map qIn = new HashMap();
            qIn.put("userId", userId);
            qIn.put("categoryName", ESTags.DEFAULT.toString());
            ArrayList<CategoryView> categoryViews = iCategoryMiddle.listCategory(qIn);
            if (categoryViews.size() > 0) {
                noteInfo.setCategoryId(categoryViews.get(0).getCategoryId());
            } else {
                /**
                 * ??????????????????default?????????????????????
                 */
                Category category = new Category();
                category.setCategoryId(GogoTools.UUID32());
                category.setCategoryName(ESTags.DEFAULT.toString());
                category.setUserId(userId);
                category.setNoteType(ESTags.NORMAL.toString());
                iCategoryMiddle.createCategory(category);
                noteInfo.setCategoryId(category.getCategoryId());
            }
        } else {
            //?????????categoryId
            Map qIn2 = new HashMap();
            qIn2.put("categoryId", categoryId);
            CategoryView categoryView = iCategoryMiddle.getCategory(qIn2, false, userId);
            /**
             * ????????????????????????????????????categoryId?????????????????????
             */
            noteInfo.setCategoryId(categoryView.getCategoryId());
        }

        noteInfo.setEncrypt(encrypt);

        if (encrypt != null && encrypt == 1) {

            noteInfo.setUserEncodeKey(strAESKey);
        }
        noteInfo.setCreateTime(new Date());
        noteInfo.setStatus(ESTags.ACTIVE.toString());
        noteInfo.setUserId(userId);
        noteInfo.setTitle(title);
        iNoteMiddle.createNoteInfo(noteInfo);
    }

    private void updateNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();
        String title = (String) in.get("title");
        String content = (String) in.get("content");
        Integer encrypt = (Integer) in.get("encrypt");
        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");
        String categoryId = (String) in.get("categoryId");
        String userId = in.get("userId").toString();
        String strAESKey = (String) in.get("strAESKey");

        /**
         * ??????????????????????????????????????????????????????????????????????????????
         */
        NoteView noteView = iNoteMiddle.getNoteDetail(noteId, false, userId);

        /**
         * ?????????????????????????????????
         */
        Map qInEdit = new HashMap();
        int cc = 0;
        if (noteView.getTitle() != null) {
            if (title != null) {
                if (!noteView.getTitle().equals(title)) {
                    qInEdit.put("title", title);
                    cc++;
                }
            }
        } else {
            if (title != null) {
                qInEdit.put("title", title);
                cc++;
            }
        }

        if (categoryId == null) {
            /**
             * ??????categoryId??????????????????
             */
            Map qIn = new HashMap();
            qIn.put("userId", userId);
            qIn.put("categoryName", ESTags.DEFAULT.toString());
            ArrayList<CategoryView> categoryViews = iCategoryMiddle.listCategory(qIn);
            if (categoryViews.size() > 0) {
                categoryId = categoryViews.get(0).getCategoryId();
            } else {
                /**
                 * ??????????????????default?????????????????????
                 */
                Category category = new Category();
                category.setCategoryId(GogoTools.UUID32());
                category.setCategoryName(ESTags.DEFAULT.toString());
                category.setUserId(userId);
                category.setNoteType(ESTags.NORMAL.toString());
                iCategoryMiddle.createCategory(category);
                categoryId = category.getCategoryId();
            }
            qInEdit.put("categoryId", categoryId);
            cc++;
        } else {
            //?????????categoryId
            if (noteView.getCategoryId() == null) {
                //???????????????????????????
                qInEdit.put("categoryId", categoryId);
                cc++;
            } else {
                //???????????????????????????????????????????????????
                if (!noteView.getCategoryId().equals(categoryId)) {
                    qInEdit.put("categoryId", categoryId);
                    cc++;
                } else {
                    //??????????????????
                }
            }
        }

        /**
         * ??????????????????????????????
         * encrypt==1 ??????
         * encrypt==0 ?????????
         */
        if (encrypt != null) {
            qInEdit.put("encrypt", encrypt);
            if (encrypt == 1) {
                qInEdit.put("userEncodeKey", strAESKey);
            }
            cc++;
        }

        if (noteView.getContent() != null) {
            /**
             * ????????????????????????????????????
             * ????????????????????????????????????????????????????????????????????????
             */
            if (!noteView.getContent().equals(content)) {
                qInEdit.put("content", content);
                cc++;
            }
        }else{
            /**
             * ?????????????????????
             */
            qInEdit.put("content",content);
        }
        qInEdit.put("noteId", noteId);
        iNoteMiddle.updateNoteInfo(qInEdit);
    }
}
