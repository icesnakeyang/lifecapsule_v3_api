package cc.cdtime.lifecapsule_v3_api.app.author;

import cc.cdtime.lifecapsule_v3_api.business.author.IAuthorBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppAuthorBService implements IAppAuthorBService {
    private final IAuthorBService iAuthorBService;

    public AppAuthorBService(IAuthorBService iAuthorBService) {
        this.iAuthorBService = iAuthorBService;
    }

    @Override
    public Map getMyAuthorInfo(Map in) throws Exception {
        Map out = iAuthorBService.getAuthorInfo(in);
        return out;
    }
}
