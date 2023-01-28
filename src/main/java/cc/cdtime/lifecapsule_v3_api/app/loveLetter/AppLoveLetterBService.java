package cc.cdtime.lifecapsule_v3_api.app.loveLetter;

import cc.cdtime.lifecapsule_v3_api.business.loveLetter.ILoveLetterBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppLoveLetterBService implements IAppLoveLetterBService {
    private final ILoveLetterBService iLoveLetterBService;

    public AppLoveLetterBService(ILoveLetterBService iLoveLetterBService) {
        this.iLoveLetterBService = iLoveLetterBService;
    }

    @Override
    public Map listLoveLetter(Map in) throws Exception {
        Map out = iLoveLetterBService.listLoveLetter(in);
        return out;
    }

    @Override
    public Map getLoveLetter(Map in) throws Exception {
        Map out = iLoveLetterBService.getLoveLetter(in);
        return out;
    }

    @Override
    public void saveLoveLetter(Map in) throws Exception {
        iLoveLetterBService.saveLoveLetter(in);
    }

    @Override
    public void deleteMyLoveLetter(Map in) throws Exception {
        iLoveLetterBService.deleteLoveLetter(in);
    }
}
