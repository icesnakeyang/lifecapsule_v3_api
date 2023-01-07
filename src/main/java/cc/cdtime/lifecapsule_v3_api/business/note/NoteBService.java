package cc.cdtime.lifecapsule_v3_api.business.note;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagBase;
import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagNote;
import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.security.ISecurityMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.tag.ITagMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
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
    private final ITagMiddle iTagMiddle;

    public NoteBService(IUserMiddle iUserMiddle,
                        INoteMiddle iNoteMiddle,
                        ISecurityMiddle iSecurityMiddle,
                        ITagMiddle iTagMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iSecurityMiddle = iSecurityMiddle;
        this.iTagMiddle = iTagMiddle;
    }

    @Override
    public Map listNote(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");
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
         * 如果有父笔记，就读取父笔记标题
         */
        if (noteView.getPid() != null) {
            NoteView pNote = iNoteMiddle.getNoteTiny(noteView.getPid(), true, userView.getUserId());
            if (pNote != null) {
                noteView.setPTitle(pNote.getTitle());
            }
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
        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");
        ArrayList tagList = (ArrayList) in.get("tagList");

        /**
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String strAESKey = null;
        String privateKey = iSecurityMiddle.getRSAKey(keyToken);
        strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);
        iSecurityMiddle.deleteRSAKey(keyToken);

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
        String keyword = (String) in.get("keyword");

        Map qIn = new HashMap();
        qIn.put("token", token);
        /**
         * 读取当前登录用户，如果未登录，则返回错误
         */
        UserView userView = iUserMiddle.getUserLogin(qIn, false);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
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
        String userId = in.get("userId").toString();
        String strAESKey = (String) in.get("strAESKey");
        String title = (String) in.get("title");

        NoteInfo noteInfo = new NoteInfo();
        noteInfo.setNoteId(GogoTools.UUID32());
        noteInfo.setContent(content);

        noteInfo.setEncrypt(1);
        noteInfo.setUserEncodeKey(strAESKey);
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
        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");
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

        qInEdit.put("encodeKey", strAESKey);

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
            if (tagView.getTagHot() == null) {
                qIn.put("tagHot", 1);
            } else {
                qIn.put("tagHot", tagView.getTagHot() + 1);
            }
            qIn.put("tagId", tagView.getTagId());
            iTagMiddle.updateTagBase(qIn);
        }
    }
}
