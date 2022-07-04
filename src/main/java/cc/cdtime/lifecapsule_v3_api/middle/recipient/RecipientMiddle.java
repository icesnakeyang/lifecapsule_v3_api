package cc.cdtime.lifecapsule_v3_api.middle.recipient;

import cc.cdtime.lifecapsule_v3_api.meta.recipient.entity.NoteRecipient;
import cc.cdtime.lifecapsule_v3_api.meta.recipient.entity.RecipientView;
import cc.cdtime.lifecapsule_v3_api.meta.recipient.sevice.IRecipientService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class RecipientMiddle implements IRecipientMiddle {
    private final IRecipientService iRecipientService;

    public RecipientMiddle(IRecipientService iRecipientService) {
        this.iRecipientService = iRecipientService;
    }

    @Override
    public void createNoteRecipient(NoteRecipient noteRecipient) throws Exception {
        iRecipientService.createNoteRecipient(noteRecipient);
    }

    @Override
    public ArrayList<RecipientView> listNoteRecipient(Map qIn) throws Exception {
        ArrayList<RecipientView> recipientViews = iRecipientService.listNoteRecipient(qIn);
        return recipientViews;
    }

    @Override
    public RecipientView getRecipient(String recipientId, Boolean returnNull) throws Exception {
        RecipientView recipientView = iRecipientService.getRecipient(recipientId);
        if (recipientView == null) {
            if (returnNull) {
                return null;
            }
            //没有查询该接收人
            throw new Exception("10016");
        }
        return recipientView;
    }

    @Override
    public RecipientView getRecipient(String recipientId, Boolean returnNull, String userId) throws Exception {
        RecipientView recipientView = iRecipientService.getRecipient(recipientId);
        if (recipientView == null) {
            if (returnNull) {
                return null;
            }
            throw new Exception("10016");
        }
        if (userId != null) {
            if (!recipientView.getUserId().equals(userId)) {
                //要修改的接收人不属于当前用户
                throw new Exception("10021");
            }
        }
        return recipientView;
    }

    @Override
    public void deleteNoteRecipient(String recipientId) throws Exception {
        iRecipientService.deleteNoteRecipient(recipientId);
    }

    @Override
    public void updateNoteRecipient(Map qIn) throws Exception {
        iRecipientService.updateNoteRecipient(qIn);
    }
}
