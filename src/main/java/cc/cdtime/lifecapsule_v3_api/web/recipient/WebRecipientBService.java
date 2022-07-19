package cc.cdtime.lifecapsule_v3_api.web.recipient;

import cc.cdtime.lifecapsule_v3_api.business.recipient.IRecipientBService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebRecipientBService implements IWebRecipientBService {
    private final IRecipientBService iRecipientBService;

    public WebRecipientBService(IRecipientBService iRecipientBService) {
        this.iRecipientBService = iRecipientBService;
    }

    @Override
    public void addContactToRecipient(Map in) throws Exception {
        iRecipientBService.addContactToRecipient(in);
    }

    @Override
    public Map listMyNoteRecipient(Map in) throws Exception {
        Map out = iRecipientBService.listRecipient(in);
        return out;
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
