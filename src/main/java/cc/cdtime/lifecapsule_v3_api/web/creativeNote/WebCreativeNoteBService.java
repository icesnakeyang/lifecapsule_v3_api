package cc.cdtime.lifecapsule_v3_api.web.creativeNote;

import cc.cdtime.lifecapsule_v3_api.business.creativeNote.ICreativeNoteBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebCreativeNoteBService implements IWebCreativeNoteBService {
    private final ICreativeNoteBService iCreativeNoteBService;

    public WebCreativeNoteBService(ICreativeNoteBService iCreativeNoteBService) {
        this.iCreativeNoteBService = iCreativeNoteBService;
    }

    @Override
    public Map listMyCreativeNote(Map in) throws Exception {
        Map out = iCreativeNoteBService.listCreativeNote(in);
        return out;
    }

    @Override
    public Map getMyCreativeNote(Map in) throws Exception {
        Map out = iCreativeNoteBService.getCreativeNote(in);
        return out;
    }

    @Override
    public Map saveCreativeNote(Map in) throws Exception {
        Map out = iCreativeNoteBService.saveCreativeNote(in);
        return out;
    }

    @Override
    public void deleteCreativeNote(Map in) throws Exception {
        iCreativeNoteBService.deleteCreativeNote(in);
    }
}
