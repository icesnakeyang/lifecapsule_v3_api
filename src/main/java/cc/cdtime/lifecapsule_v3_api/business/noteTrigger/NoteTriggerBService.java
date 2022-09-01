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
    public Map listNoteTrigger(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn=new HashMap();
        Integer offset=(pageIndex-1)*pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        ArrayList<NoteView> noteViews = iNoteMiddle.listNoteTrigger(userView.getUserId());
        for (int i = 0; i < noteViews.size(); i++) {

        }
        Map out = new HashMap();
        out.put("noteList", noteViews);

        return out;
    }
}
