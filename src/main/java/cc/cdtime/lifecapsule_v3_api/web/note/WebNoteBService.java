package cc.cdtime.lifecapsule_v3_api.web.note;

import cc.cdtime.lifecapsule_v3_api.business.category.ICategoryBService;
import cc.cdtime.lifecapsule_v3_api.business.note.INoteBService;
import cc.cdtime.lifecapsule_v3_api.business.noteSend.INoteSendBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebNoteBService implements IWebNoteBService {
    private final INoteBService iNoteBService;
    private final ICategoryBService iCategoryBService;
    private final INoteSendBService iNoteSendBService;

    public WebNoteBService(INoteBService iNoteBService,
                           ICategoryBService iCategoryBService,
                           INoteSendBService iNoteSendBService) {
        this.iNoteBService = iNoteBService;
        this.iCategoryBService = iCategoryBService;
        this.iNoteSendBService = iNoteSendBService;
    }

    @Override
    public Map listMyNote(Map in) throws Exception {
        Map out = iNoteBService.listNote(in);
        return out;
    }

    @Override
    public Map getMyNote(Map in) throws Exception {
        Map out = iNoteBService.getMyNote(in);
        return out;
    }

    @Override
    public Map listMyCategory(Map in) throws Exception {
        Map out = iCategoryBService.listCategory(in);
        return out;
    }

    @Override
    public void saveMyCategory(Map in) throws Exception {
        iCategoryBService.saveCategory(in);
    }

    @Override
    public void deleteMyCategory(Map in) throws Exception {
        iCategoryBService.deleteCategory(in);
    }

    @Override
    public Map saveMyNote(Map in) throws Exception {
        Map out = iNoteBService.saveNote(in);
        return out;
    }

    @Override
    public void deleteMyNote(Map in) throws Exception {
        iNoteBService.deleteNote(in);
    }

    @Override
    public Map loadMyNoteSendStatistic(Map in) throws Exception {
        Map out = iNoteSendBService.loadMyNoteSendStatistic(in);
        return out;
    }

    @Override
    public void saveMyNoteTags(Map in) throws Exception {
        iNoteBService.saveMyNoteTags(in);
    }
}
