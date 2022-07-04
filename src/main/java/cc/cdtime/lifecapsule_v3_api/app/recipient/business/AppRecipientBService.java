package cc.cdtime.lifecapsule_v3_api.app.recipient.business;

import cc.cdtime.lifecapsule_v3_api.business.recipient.IRecipientBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppRecipientBService implements IAppRecipientBService {
    private final IRecipientBService iRecipientBService;

    public AppRecipientBService(IRecipientBService iRecipientBService) {
        this.iRecipientBService = iRecipientBService;
    }

    @Override
    public Map listRecipient(Map in) throws Exception {
        Map out = iRecipientBService.listRecipient(in);
        return out;
    }

    @Override
    public void saveRecipient(Map in) throws Exception {
        iRecipientBService.saveRecipient(in);
    }

    @Override
    public Map getRecipient(Map in) throws Exception {
        Map out = iRecipientBService.getRecipient(in);
        return out;
    }

    @Override
    public void deleteRecipient(Map in) throws Exception {
        iRecipientBService.deleteRecipient(in);
    }
}
