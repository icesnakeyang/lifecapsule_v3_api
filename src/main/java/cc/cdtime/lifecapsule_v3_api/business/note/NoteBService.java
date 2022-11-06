package cc.cdtime.lifecapsule_v3_api.business.note;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.category.entity.Category;
import cc.cdtime.lifecapsule_v3_api.meta.category.entity.CategoryView;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.recipient.entity.RecipientView;
import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagBase;
import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagNote;
import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagView;
import cc.cdtime.lifecapsule_v3_api.meta.trigger.entity.TriggerView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.category.ICategoryMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.recipient.IRecipientMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.security.ISecurityMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.tag.ITagMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.task.ITaskMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.trigger.ITriggerMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.HTML;
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
    private final ITagMiddle iTagMiddle;

    public NoteBService(IUserMiddle iUserMiddle,
                        INoteMiddle iNoteMiddle,
                        ISecurityMiddle iSecurityMiddle,
                        ICategoryMiddle iCategoryMiddle,
                        ITagMiddle iTagMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iSecurityMiddle = iSecurityMiddle;
        this.iCategoryMiddle = iCategoryMiddle;
        this.iTagMiddle = iTagMiddle;
    }

    @Override
    public Map listNote(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");
        String categoryId = (String) in.get("categoryId");
        String keyword = (String) in.get("keyword");
        ArrayList tagList = (ArrayList) in.get("tagList");
        String searchKey = (String) in.get("searchKey");

        Map qIn = new HashMap();
        qIn.put("token", token);
        /**
         * 读取当前登录用户，如果未登录，则返回错误
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
        if (tagList != null && tagList.size() > 0) {
            qIn.put("tagList", tagList);
        }
        qIn.put("keyword", searchKey);
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
         * 获取用户临时上传的加密笔记AES秘钥的AES秘钥
         */
        String strAESKey = null;
        if (keyToken != null) {
            strAESKey = iSecurityMiddle.takeNoteAES(keyToken, encryptKey);
        }

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteDetail(noteId, false, userView.getUserId());

        /**
         * 读取userEncodeKey
         */
        if (noteView.getEncrypt() != null && noteView.getEncrypt() == 1) {
            if (strAESKey == null) {
                //查询秘钥错误
                throw new Exception("10037");
            }
            String data = noteView.getUserEncodeKey();
            //用AES秘钥加密笔记内容的AES秘钥
            String outCode = GogoTools.encryptAESKey(data, strAESKey);
            noteView.setUserEncodeKey(outCode);
        } else {
            noteView.setUserEncodeKey(null);
        }

        /**
         * 读取笔记标签
         */
        qIn = new HashMap();
        qIn.put("noteId", noteView.getNoteId());
        ArrayList<TagView> tagViews = iTagMiddle.listNoteTag(qIn);

        Map out = new HashMap();
        out.put("note", noteView);
        out.put("noteTagList", tagViews);

        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map saveNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = (String) in.get("noteId");
        String title = (String) in.get("title");
        String content = (String) in.get("content");
        Integer encrypt = (Integer) in.get("encrypt");
        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");
        String categoryId = (String) in.get("categoryId");
        ArrayList tagList = (ArrayList) in.get("tagList");

        /**
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String strAESKey = null;
        if (encrypt != null && encrypt == 1) {
            String privateKey = iSecurityMiddle.getRSAKey(keyToken);
            strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);
            iSecurityMiddle.deleteRSAKey(keyToken);
        }

        /**
         * 首先读取用户信息
         */
        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUserLogin(qIn, false);
        in.put("userId", userView.getUserId());
        if (noteId == null) {
            /**
             * 新增
             */
            if (strAESKey != null) {
                in.put("strAESKey", strAESKey);
            }
            if (categoryId == null) {
                /**
                 * 没有指定分类，默认分类
                 */
                CategoryView categoryView = iCategoryMiddle.getDefaultCategory(userView.getUserId());
                in.put("categoryId", categoryView.getCategoryId());
            }
            noteId = createNote(in);

        } else {
            /**
             * 检查note是否当前用户的
             */
            NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());
            /**
             * 修改
             */
            in.put("strAESKey", strAESKey);
            updateNote(in);
        }

        /**
         * 保存tagList
         */
        if (tagList != null && tagList.size() > 0) {
            /**
             * 删除掉原来所有tag，再添加tag
             */
            qIn = new HashMap();
            qIn.put("noteId", noteId);
            iTagMiddle.deleteTagNote(qIn);
            for (int i = 0; i < tagList.size(); i++) {
                Map tagMap = (Map) tagList.get(i);
                if (tagMap != null) {
                    String tagName = (String) tagMap.get("tagName");
                    if (tagName != null) {
                        saveTag(tagName, noteId, userView.getUserId());
                    }
                }
            }
        }


        Map out = new HashMap();
        out.put("noteId", noteId);
        return out;
    }

    @Override
    public Map totalNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String categoryId = (String) in.get("categoryId");
        String keyword = (String) in.get("keyword");

        Map qIn = new HashMap();
        qIn.put("token", token);
        /**
         * 读取当前登录用户，如果未登录，则返回错误
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        iNoteMiddle.deleteNote(noteId);
    }

    @Override
    public Map getNoteTiny(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        Map note = new HashMap();
        note.put("noteId", noteView.getNoteId());
        note.put("categoryId", noteView.getCategoryId());
        note.put("categoryName", noteView.getCategoryName());
        note.put("createTime", noteView.getCreateTime());
        note.put("title", noteView.getTitle());
        Map out = new HashMap();
        out.put("note", note);

        return out;
    }

    @Override
    public void replyNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String title = in.get("title").toString();
        String content = in.get("content").toString();
        String pid = (String) in.get("pid");
        String sengLogId = (String) in.get("sendLogId");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        if (pid != null) {
            /**
             * 回复自己的笔记
             * 直接创建一个笔记，指定pid
             */
        } else {
            /**
             * 回复别人的笔记
             * 创建笔记，类型为回复，指定sendLogId
             */

        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveMyNoteTags(Map in) throws Exception {
        String token = in.get("token").toString();
        ArrayList tagList = (ArrayList) in.get("tagList");
        String noteId = in.get("noteId").toString();

        if (tagList == null) {
            return;
        }
        if (tagList.size() == 0) {
            /**
             * 把该笔记的所有tag删除
             */
            Map qIn = new HashMap();
            qIn.put("noteId", noteId);
            iTagMiddle.deleteTagNote(qIn);
            return;
        }

        if (noteId == null) {
            return;
        }

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        /**
         * 删除掉原来所有tag，再添加tag
         */
        qIn = new HashMap();
        qIn.put("noteId", noteId);
        iTagMiddle.deleteTagNote(qIn);
        for (int i = 0; i < tagList.size(); i++) {
            Map tagMap = (Map) tagList.get(i);
            String tagName = (String) tagMap.get("tagName");
            if (tagName != null) {
                saveTag(tagName, noteId, userView.getUserId());
            }
        }
    }

    private String createNote(Map in) throws Exception {
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
             * 没有categoryId，就用默认的
             */
            Map qIn = new HashMap();
            qIn.put("userId", userId);
            qIn.put("categoryName", ESTags.DEFAULT.toString());
            ArrayList<CategoryView> categoryViews = iCategoryMiddle.listCategory(qIn);
            if (categoryViews.size() > 0) {
                noteInfo.setCategoryId(categoryViews.get(0).getCategoryId());
            } else {
                /**
                 * 该用户还没有default分类，添加一个
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
            //上传了categoryId
            Map qIn2 = new HashMap();
            qIn2.put("categoryId", categoryId);
            CategoryView categoryView = iCategoryMiddle.getCategory(qIn2, false, userId);
            /**
             * 如果用户上传了一个错误的categoryId要怎么处理呢？
             */
            noteInfo.setCategoryId(categoryView.getCategoryId());
        }

        noteInfo.setEncrypt(encrypt);

        if (encrypt != null && encrypt == 1) {
            /**
             * 把秘钥保存到userencodekey表
             */
            noteInfo.setUserEncodeKey(strAESKey);
        }
        noteInfo.setCreateTime(new Date());
        noteInfo.setStatus(ESTags.ACTIVE.toString());
        noteInfo.setUserId(userId);
        noteInfo.setTitle(title);
        iNoteMiddle.createNoteInfo(noteInfo);
        return noteInfo.getNoteId();
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
         * 读取笔记信息，检查笔记是否存在，是否是用户本人的笔记
         */
        NoteView noteView = iNoteMiddle.getNoteDetail(noteId, false, userId);

        /**
         * 检查有没有要修改的属性
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
             * 没有categoryId，就用默认的
             */
            Map qIn = new HashMap();
            qIn.put("userId", userId);
            qIn.put("categoryName", ESTags.DEFAULT.toString());
            ArrayList<CategoryView> categoryViews = iCategoryMiddle.listCategory(qIn);
            if (categoryViews.size() > 0) {
                categoryId = categoryViews.get(0).getCategoryId();
            } else {
                /**
                 * 该用户还没有default分类，添加一个
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
            //上传了categoryId
            if (noteView.getCategoryId() == null) {
                //原来没有，直接保存
                qInEdit.put("categoryId", categoryId);
                cc++;
            } else {
                //原来有，比较是否一致，不一致就修改
                if (!noteView.getCategoryId().equals(categoryId)) {
                    qInEdit.put("categoryId", categoryId);
                    cc++;
                } else {
                    //一致，不修改
                }
            }
        }

        /**
         * 比较当前笔记是否加密
         * encrypt==1 加密
         * encrypt==0 不加密
         */
        if (encrypt != null) {
            qInEdit.put("encrypt", encrypt);
            if (encrypt == 1) {
                qInEdit.put("encodeKey", strAESKey);
            }
            cc++;
        }

        if (noteView.getContent() != null) {
            /**
             * 旧笔记里已经有详情内容了
             * 比较是否一致，如果一样，就不处理，不一样，才修改
             */
            if (!noteView.getContent().equals(content)) {
                qInEdit.put("content", content);
                cc++;
            }
        } else {
            /**
             * 还没有笔记内容
             */
            qInEdit.put("content", content);
        }
        qInEdit.put("noteId", noteId);
        iNoteMiddle.updateNoteInfo(qInEdit);
    }

    private void saveTag(String tagName, String noteId, String userId) throws Exception {
        /**
         * 新增加的tag
         * 1、检查tagName是否存在，不存在就创建tagBase
         * 2、获得tagId，创建到tagNote
         */
        TagView tagView = iTagMiddle.getTagBase(tagName, true);
        if (tagView == null) {
            TagBase tagBase = new TagBase();
            tagBase.setTagName(tagName);
            tagBase.setTagId(GogoTools.UUID32());
            iTagMiddle.createTagBase(tagBase);
            TagNote tagNote = new TagNote();
            tagNote.setNoteId(noteId);
            tagNote.setCreateTime(new Date());
            tagNote.setTagId(tagBase.getTagId());
            tagNote.setUserId(userId);
            iTagMiddle.createTagNote(tagNote);
        } else {
            TagNote tagNote = new TagNote();
            tagNote.setNoteId(noteId);
            tagNote.setCreateTime(new Date());
            tagNote.setTagId(tagView.getTagId());
            tagNote.setUserId(userId);
            iTagMiddle.createTagNote(tagNote);
            /**
             * 增加tagHot热度
             */
            Map qIn = new HashMap();
            qIn.put("tagHot", tagView.getTagHot() + 1);
            qIn.put("tagId", tagView.getTagId());
            iTagMiddle.updateTagBase(qIn);
        }
    }
}
