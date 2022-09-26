package cc.cdtime.lifecapsule_v3_api.app.note.business;

import cc.cdtime.lifecapsule_v3_api.business.note.INoteBService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppNoteBservice implements IAppNoteBService {
    private final INoteBService iNoteBService;

    public AppNoteBservice(INoteBService iNoteBService) {
        this.iNoteBService = iNoteBService;
    }

    @Override
    public Map listMyNote(Map in) throws Exception {
        Map out = iNoteBService.listNote(in);
        return out;
    }

    @Override
    public Map getMyNote(Map in) throws Exception {
        Map out = iNoteBService.getMyNote(in);
        return out;
    }

    @Override
    public Map saveMyNote(Map in) throws Exception {
        Map out = iNoteBService.saveNote(in);
        return out;
    }

    @Override
    public void deleteMyNote(Map in) throws Exception {
        iNoteBService.deleteNote(in);
    }

    @Override
    public Map listNoteTrigger(Map in) throws Exception {
        return null;
    }

    @Override
    public void createNoteRecipient(Map in) throws Exception {

    }

    @Override
    public Map totalMyNote(Map in) throws Exception {
        Map out = iNoteBService.totalNote(in);
        return out;
    }

    @Override
    public Map getMyNoteTiny(Map in) throws Exception {
        Map out = iNoteBService.getNoteTiny(in);
        return out;
    }
}
