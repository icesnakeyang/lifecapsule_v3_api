package cc.cdtime.lifecapsule_v3_api.app.lastWords;

import cc.cdtime.lifecapsule_v3_api.business.lastWords.ILastWordsBService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppLastWordsBService implements IAppLastWordsBService {
    private final ILastWordsBService iLastWordsBService;

    public AppLastWordsBService(ILastWordsBService iLastWordsBService) {
        this.iLastWordsBService = iLastWordsBService;
    }

    @Override
    public Map listLastWords(Map in) throws Exception {
        Map out = iLastWordsBService.listLastWords(in);
        return out;
    }

    @Override
    public void saveLastWords(Map in) throws Exception {
        iLastWordsBService.saveLastWords(in);
    }

    @Override
    public Map getMyLastWords(Map in) throws Exception {
        Map out = iLastWordsBService.getLastWords(in);
        return out;
    }

    @Override
    public void deleteMyLastWords(Map in) throws Exception {
        iLastWordsBService.deleteMyLastWords(in);
    }
}
