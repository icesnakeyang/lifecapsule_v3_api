package cc.cdtime.lifecapsule_v3_api.business.noteTrigger;

import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class NoteTriggerBService implements INoteTriggerBService {
    private final IUserMiddle iUserMiddle;
    private final INoteMiddle iNoteMiddle;

    public NoteTriggerBService(IUserMiddle iUserMiddle,
                               INoteMiddle iNoteMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iNoteMiddle = iNoteMiddle;
    }

    @Override
    public Map listMyNoteTrigger(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        ArrayList<NoteView> noteViews = iNoteMiddle.listNoteTrigger(userView.getUserId());
        for (int i = 0; i < noteViews.size(); i++) {

        }
        Map out = new HashMap();
        out.put("noteList", noteViews);

        return out;
    }
}
