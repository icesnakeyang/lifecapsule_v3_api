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
    public void deleteNoteRecipient(String recipientId) throws Exception {
        recipientDao.deleteNoteRecipient(recipientId);
    }

    @Override
    public void updateNoteRecipient(Map qIn) throws Exception {
        recipientDao.updateNoteRecipient(qIn);
    }
}
