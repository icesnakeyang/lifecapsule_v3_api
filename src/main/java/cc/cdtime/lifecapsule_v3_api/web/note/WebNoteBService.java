package cc.cdtime.lifecapsule_v3_api.web.note;

import cc.cdtime.lifecapsule_v3_api.business.note.INoteBService;
import cc.cdtime.lifecapsule_v3_api.business.noteSend.INoteSendBService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebNoteBService implements IWebNoteBService {
    private final INoteBService iNoteBService;
    private final INoteSendBService iNoteSendBService;

    public WebNoteBService(INoteBService iNoteBService,
                           INoteSendBService iNoteSendBService) {
        this.iNoteBService = iNoteBService;
        this.iNoteSendBService = iNoteSendBService;
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
    public Map loadMyNoteSendStatistic(Map in) throws Exception {
        Map out = iNoteSendBService.loadMyNoteSendStatistic(in);
        return out;
    }

    @Override
    public void saveMyNoteTags(Map in) throws Exception {
        iNoteBService.saveMyNoteTags(in);
    }
}
