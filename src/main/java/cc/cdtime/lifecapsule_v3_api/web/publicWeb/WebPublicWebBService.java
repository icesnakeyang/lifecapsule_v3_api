package cc.cdtime.lifecapsule_v3_api.web.publicWeb;

import cc.cdtime.lifecapsule_v3_api.business.publicWeb.IPublicWebBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SQLInputImpl;
import java.util.Map;

@Service
public class WebPublicWebBService implements IWebPublicWebBService {
    private final IPublicWebBService iPublicWebBService;

    public WebPublicWebBService(IPublicWebBService iPublicWebBService) {
        this.iPublicWebBService = iPublicWebBService;
    }

    @Override
    public void publishNoteToPublicWeb(Map in) throws Exception {
        iPublicWebBService.publishNoteToPublicWeb(in);
    }

    @Override
    public Map listMyPublicNote(Map in) throws Exception {
        Map out = iPublicWebBService.listPublicNote(in);
        return out;
    }

    @Override
    public Map getMyPublicNote(Map in) throws Exception {
        Map out = iPublicWebBService.getMyPublicNote(in);
        return out;
    }

    @Override
    public Map getArticle(Map in) throws Exception {
        Map out = iPublicWebBService.getArticle(in);
        return out;
    }

    @Override
    public void updateMyPublicNote(Map in) throws Exception {
        iPublicWebBService.updateNotePublic(in);
    }
}
