package cc.cdtime.lifecapsule_v3_api.app.history;

import cc.cdtime.lifecapsule_v3_api.business.history.IHistoryBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppHistoryBService implements IAppHistoryBService {
    private final IHistoryBService iHistoryBService;

    public AppHistoryBService(IHistoryBService iHistoryBService) {
        this.iHistoryBService = iHistoryBService;
    }

    @Override
    public Map loadHistoryHome(Map in) throws Exception {
        Map out = iHistoryBService.loadHistoryHome(in);
        return out;
    }

    @Override
    public void replyMyNote(Map in) throws Exception {
        iHistoryBService.replyMyNote(in);
    }
}
