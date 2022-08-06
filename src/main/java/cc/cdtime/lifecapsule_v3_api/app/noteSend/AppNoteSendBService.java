package cc.cdtime.lifecapsule_v3_api.app.noteSend;

import cc.cdtime.lifecapsule_v3_api.business.noteSend.INoteSendBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppNoteSendBService implements IAppNoteSendBService {
    private final INoteSendBService iNoteSendBService;

    public AppNoteSendBService(INoteSendBService iNoteSendBService) {
        this.iNoteSendBService = iNoteSendBService;
    }

    @Override
    public void sendNote(Map in) throws Exception {
        iNoteSendBService.sendNote(in);
    }

    @Override
    public Map searchUser(Map in) throws Exception {
        Map out = iNoteSendBService.searchUser(in);
        return out;
    }

    @Override
    public Map loadMyNoteSendStatistic(Map in) throws Exception {
        Map out = iNoteSendBService.loadMyNoteSendStatistic(in);
        return out;
    }

    @Override
    public Map listMyNoteReceiveLog(Map in) throws Exception {
        Map out = iNoteSendBService.listNoteReceiveLog(in);
        return out;
    }

    @Override
    public Map getMyReceiveNote(Map in) throws Exception {
        Map out = iNoteSendBService.getNoteSendLog(in);
        return out;
    }
}
