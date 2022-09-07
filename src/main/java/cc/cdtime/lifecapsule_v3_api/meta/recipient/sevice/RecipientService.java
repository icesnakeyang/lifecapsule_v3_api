package cc.cdtime.lifecapsule_v3_api.meta.recipient.sevice;

import cc.cdtime.lifecapsule_v3_api.meta.recipient.dao.RecipientDao;
import cc.cdtime.lifecapsule_v3_api.meta.recipient.entity.NoteRecipient;
import cc.cdtime.lifecapsule_v3_api.meta.recipient.entity.RecipientView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class RecipientService implements IRecipientService {
    private final RecipientDao recipientDao;

    public RecipientService(RecipientDao recipientDao) {
        this.recipientDao = recipientDao;
    }

    @Override
    public void createNoteRecipient(NoteRecipient noteRecipient) throws Exception {
        recipientDao.createNoteRecipient(noteRecipient);
    }

    @Override
    public ArrayList<RecipientView> listNoteRecipient(Map qIn) throws Exception {
        ArrayList<RecipientView> recipientViews = recipientDao.listNoteRecipient(qIn);
        return recipientViews;
    }

    @Override
    public RecipientView getRecipient(String recipientId) throws Exception {
        RecipientView recipientView = recipientDao.getRecipient(recipientId);
        return recipientView;
    }

    @Override
    public RecipientView getRecipientTiny(String recipientId) throws Exception {
        RecipientView recipientView = recipientDao.getRecipientTiny(recipientId);
        return recipientView;
    }

    @Override
    public void deleteNoteRecipient(Map qIn) throws Exception {
        String noteId=(String)qIn.get("noteId");
        String recipientId=(String)qIn.get("recipientId");
        if(noteId==null && recipientId==null){
            //删除接收人失败，必须指定一个删除条件
            throw new Exception("10052");
        }
        recipientDao.deleteNoteRecipient(qIn);
    }

    @Override
    public void updateNoteRecipient(Map qIn) throws Exception {
        recipientDao.updateNoteRecipient(qIn);
    }
}
