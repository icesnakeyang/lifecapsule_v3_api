package cc.cdtime.lifecapsule_v3_api.web.motto;

import cc.cdtime.lifecapsule_v3_api.business.motto.IMottoBService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebMottoBService implements IWebMottoBService {
    private final IMottoBService iMottoBService;

    public WebMottoBService(IMottoBService iMottoBService) {
        this.iMottoBService = iMottoBService;
    }

    @Override
    public void publishMotto(Map in) throws Exception {
        iMottoBService.publishMotto(in);
    }
}
