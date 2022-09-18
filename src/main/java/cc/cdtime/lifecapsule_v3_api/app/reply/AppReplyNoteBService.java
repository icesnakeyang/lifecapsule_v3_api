package cc.cdtime.lifecapsule_v3_api.app.reply;

import cc.cdtime.lifecapsule_v3_api.business.reply.IReplyNoteBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppReplyNoteBService implements IAppReplyNoteBService {
    private final IReplyNoteBService iReplyNoteBService;

    public AppReplyNoteBService(IReplyNoteBService iReplyNoteBService) {
        this.iReplyNoteBService = iReplyNoteBService;
    }

    @Override
    public void createReplyNote(Map in) throws Exception {
        iReplyNoteBService.createReplyNote(in);
    }
}
