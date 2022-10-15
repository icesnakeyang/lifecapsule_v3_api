package cc.cdtime.lifecapsule_v3_api.web.tag;

import cc.cdtime.lifecapsule_v3_api.business.tag.ITagBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebTagBService implements IWebTagBService {
    private final ITagBService iTagBService;

    public WebTagBService(ITagBService iTagBService) {
        this.iTagBService = iTagBService;
    }

    @Override
    public Map listUserNoteTag(Map in) throws Exception {
        Map out = iTagBService.listUserNoteTag(in);
        return out;
    }

    @Override
    public Map listHotNoteTags(Map in) throws Exception {
        Map out = iTagBService.listHotNoteTags(in);
        return out;
    }
}
