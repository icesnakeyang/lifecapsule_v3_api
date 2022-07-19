package cc.cdtime.lifecapsule_v3_api.web.contact;

import cc.cdtime.lifecapsule_v3_api.business.contact.IContactBService;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebContactBService implements IWebContactBService {
    private final IContactBService iContactBService;

    public WebContactBService(IContactBService iContactBService) {
        this.iContactBService = iContactBService;
    }

    @Override
    public void saveMyContact(Map in) throws Exception {
        iContactBService.saveContact(in);
    }

    @Override
    public Map listMyContact(Map in) throws Exception {
        Map out=iContactBService.listContact(in);
        return out;
    }

    @Override
    public Map getMyContact(Map in) throws Exception {
        Map out=iContactBService.getContact(in);
        return out;
    }

    @Override
    public void deleteMyContact(Map in) throws Exception {
        iContactBService.deleteContact(in);
    }
}
