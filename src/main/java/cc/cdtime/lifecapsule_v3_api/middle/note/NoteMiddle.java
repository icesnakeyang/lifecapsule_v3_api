package cc.cdtime.lifecapsule_v3_api.middle.note;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.content.entity.Content;
import cc.cdtime.lifecapsule_v3_api.meta.content.service.IContentService;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.note.service.INoteService;
import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagView;
import cc.cdtime.lifecapsule_v3_api.meta.tag.service.ITagService;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKey;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKeyView;
import cc.cdtime.lifecapsule_v3_api.meta.user.service.IUserEncodeKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class NoteMiddle implements INoteMiddle {
    private final INoteService iNoteService;
    private final IUserEncodeKeyService iUserEncodeKeyService;
    private final IContentService iContentService;
    private final ITagService iTagService;

    public NoteMiddle(INoteService iNoteService,
                      IUserEncodeKeyService iUserEncodeKeyService,
                      IContentService iContentService,
                      ITagService iTagService) {
        this.iNoteService = iNoteService;
        this.iUserEncodeKeyService = iUserEncodeKeyService;
        this.iContentService = iContentService;
        this.iTagService = iTagService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createNoteInfo(NoteInfo noteInfo) throws Exception {
        /**
         * 创建笔记表 note_info
         */
        iNoteService.createNoteInfo(noteInfo);

        if (noteInfo.getContent() != null) {
            /**
             * 创建内容表content_detail
             */
            Content content = new Content();
            content.setContent(noteInfo.getContent());
            content.setIndexId(noteInfo.getNoteId());
            iContentService.createContent(content);

            if (noteInfo.getUserEncodeKey() != null) {
                /**
                 * 创建用户秘钥表user_encode_key
                 */
                UserEncodeKey userEncodeKey = new UserEncodeKey();
                userEncodeKey.setUserId(noteInfo.getUserId());
                userEncodeKey.setEncodeKeyId(GogoTools.UUID32());
                userEncodeKey.setCreateTime(new Date());
                userEncodeKey.setEncodeKey(noteInfo.getUserEncodeKey());
                userEncodeKey.setIndexId(noteInfo.getNoteId());
                iUserEncodeKeyService.createUserEncodeKey(userEncodeKey);
            }
        }
    }

    @Override
    public NoteView getNoteDetail(String noteId, Boolean returnNull, String userId) throws Exception {
        NoteView noteView = getNoteTiny(noteId, returnNull, userId);

        /**
         * 读取详情
         */
        Map qIn = new HashMap();
        Content content = iContentService.getContent(noteView.getNoteId());
        if (content != null) {
            noteView.setContent(content.getContent());
        }

        /**
         * 读取userEncodeKey
         */
        qIn = new HashMap();
        qIn.put("indexId", noteView.getNoteId());
        UserEncodeKeyView userEncodeKeyView = iUserEncodeKeyService.getUserEncodeKey(qIn);
        if (userEncodeKeyView != null) {
            if (userEncodeKeyView.getEncodeKey() != null) {
                noteView.setUserEncodeKey(userEncodeKeyView.getEncodeKey());
            }
        } else {

        }
        return noteView;
    }

    @Override
    public NoteView getNoteTiny(String noteId, Boolean returnNull, String userId) throws Exception {
        NoteView noteView = iNoteService.getNoteInfo(noteId);

        if (noteView == null) {
            if (returnNull) {
                return null;
            }
            //没有查询到该笔记
            throw new Exception("10007");
        }
        if (userId != null) {
            if (!userId.equals(noteView.getUserId())) {
                //该笔记不属于当前用户
                throw new Exception("10008");
            }
        }
        return noteView;
    }

    @Override
    public ArrayList<NoteView> listNote(Map qIn) throws Exception {
        ArrayList<NoteView> noteViews = iNoteService.listNoteInfo(qIn);
        return noteViews;
    }

    @Override
    public Integer totalNote(Map qIn) throws Exception {
        Integer total = iNoteService.totalNoteInfo(qIn);
        return total;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateNoteInfo(Map qIn) throws Exception {
        iNoteService.updateNoteInfo(qIn);
        String indexId = qIn.get("noteId").toString();

        String content = (String) qIn.get("content");
        if (content != null) {
            Map contentMap = new HashMap();
            contentMap.put("indexId", indexId);
            contentMap.put("content", content);
            iContentService.updateContent(contentMap);
        }

        String encodeKey = (String) qIn.get("encodeKey");
        if (encodeKey != null) {
            Map keyMap = new HashMap();
            keyMap.put("encodeKey", encodeKey);
            keyMap.put("indexId", indexId);
            iUserEncodeKeyService.updateUserEncodeKey(keyMap);
        }
    }

    @Override
    public void deleteNote(String noteId) throws Exception {
        iNoteService.deleteNoteInfo(noteId);
        iContentService.deleteContent(noteId);
        iUserEncodeKeyService.deleteUserEncodeKey(noteId);
    }

    @Override
    public ArrayList<NoteView> listNoteTrigger(String userId) throws Exception {
        ArrayList<NoteView> noteViews = iNoteService.listNoteTrigger(userId);
        return noteViews;
    }

    @Override
    public ArrayList<NoteView> listHistoryNote(Map qIn) throws Exception {
        ArrayList<NoteView> noteViews = iNoteService.listHistoryNote(qIn);
        return noteViews;
    }

    @Override
    public ArrayList<NoteView> listHistoryRandom(Map qIn) throws Exception {
        ArrayList<NoteView> noteViews = iNoteService.listHistoryRandom(qIn);
        return noteViews;
    }
}
