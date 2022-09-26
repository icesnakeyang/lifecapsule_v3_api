package cc.cdtime.lifecapsule_v3_api.app.inbox;

import cc.cdtime.lifecapsule_v3_api.business.inbox.receiveNote.IReceiveNoteBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppInboxBService implements IAppInboxBService {
    private final IReceiveNoteBService iReceiveNoteBService;

    public AppInboxBService(IReceiveNoteBService iReceiveNoteBService) {
        this.iReceiveNoteBService = iReceiveNoteBService;
    }

    @Override
    public Map listMyReceiveNote(Map in) throws Exception {
        Map out = iReceiveNoteBService.listReceiveNote(in);
        return out;
    }

    @Override
    public Map getMyReceiveNote(Map in) throws Exception {
        Map out = iReceiveNoteBService.getMyReceiveNote(in);
        return out;
    }
}
