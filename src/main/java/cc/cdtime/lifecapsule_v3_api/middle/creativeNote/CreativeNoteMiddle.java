package cc.cdtime.lifecapsule_v3_api.middle.creativeNote;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.content.entity.Content;
import cc.cdtime.lifecapsule_v3_api.meta.content.service.IContentService;
import cc.cdtime.lifecapsule_v3_api.meta.creativeNote.entity.CreativeNote;
import cc.cdtime.lifecapsule_v3_api.meta.creativeNote.service.ICreativeNoteService;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKey;
import cc.cdtime.lifecapsule_v3_api.meta.user.service.IUserEncodeKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CreativeNoteMiddle implements ICreativeNoteMiddle {
    private final ICreativeNoteService iCreativeNoteService;
    private final IContentService iContentService;
    private final IUserEncodeKeyService iUserEncodeKeyService;

    public CreativeNoteMiddle(ICreativeNoteService iCreativeNoteService,
                              IContentService iContentService,
                              IUserEncodeKeyService iUserEncodeKeyService) {
        this.iCreativeNoteService = iCreativeNoteService;
        this.iContentService = iContentService;
        this.iUserEncodeKeyService = iUserEncodeKeyService;
    }

    @Override
    public void createCreativeNote(CreativeNote creativeNote) throws Exception {
        iCreativeNoteService.createCreativeNote(creativeNote);

        Content content = new Content();
        content.setContent(creativeNote.getContent());
        content.setIndexId(creativeNote.getCreativeNoteId());
        iContentService.createContent(content);

        UserEncodeKey userEncodeKey = new UserEncodeKey();
        userEncodeKey.setEncodeKey(creativeNote.getUserEncodeKey());
        userEncodeKey.setEncodeKeyId(GogoTools.UUID32());
        userEncodeKey.setCreateTime(new Date());
        userEncodeKey.setIndexId(creativeNote.getCreativeNoteId());
        iUserEncodeKeyService.createUserEncodeKey(userEncodeKey);
    }

    @Override
    public ArrayList<CreativeNote> listCreativeNote(Map qIn) throws Exception {
        ArrayList<CreativeNote> creativeNotes = iCreativeNoteService.listCreativeNote(qIn);
        return creativeNotes;
    }

    @Override
    public void updateCreativeNoteDetail(Map qIn) throws Exception {
        //iCreativeNoteService.updateCreativeNoteDetail(qIn);
        String content = (String) qIn.get("content");
        String indexId = qIn.get("creativeNoteId").toString();
        String userEncodeKey = qIn.get("userEncodeKey").toString();
        if (content == null) {
            content = "";
        }
        Map qIn1 = new HashMap();
        qIn1.put("indexId", indexId);
        qIn1.put("content", content);
        iContentService.updateContent(qIn1);
        qIn1.put("encodeKey", userEncodeKey);
        iUserEncodeKeyService.updateUserEncodeKey(qIn1);

    }

    @Override
    public void deleteCreativeNote(String noteId) throws Exception {
        Map qIn = new HashMap();
        qIn.put("noteId", noteId);
        ArrayList<CreativeNote> creativeNotes = listCreativeNote(qIn);
        for (int i = 0; i < creativeNotes.size(); i++) {
            iContentService.deleteContent(creativeNotes.get(i).getCreativeNoteId());
            iUserEncodeKeyService.deleteUserEncodeKey(creativeNotes.get(i).getCreativeNoteId());
        }
        iCreativeNoteService.deleteCreativeNote(noteId);
    }
}
