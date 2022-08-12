package cc.cdtime.lifecapsule_v3_api.app.note.business;

import cc.cdtime.lifecapsule_v3_api.business.category.ICategoryBService;
import cc.cdtime.lifecapsule_v3_api.business.note.INoteBService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppNoteBservice implements IAppNoteBService {
    private final INoteBService iNoteBService;
    private final ICategoryBService iCategoryBService;

    public AppNoteBservice(INoteBService iNoteBService,
                           ICategoryBService iCategoryBService) {
        this.iNoteBService = iNoteBService;
        this.iCategoryBService = iCategoryBService;
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
    public Map saveMyNote(Map in) throws Exception {
        Map out = iNoteBService.saveNote(in);
        return out;
    }

    @Override
    public void deleteMyNote(Map in) throws Exception {
        iNoteBService.deleteNote(in);
    }

    @Override
    public Map listRecipient(Map in) throws Exception {
        return null;
    }

    @Override
    public Map listNoteTrigger(Map in) throws Exception {
        return null;
    }

    @Override
    public Map saveNoteTrigger(Map in) throws Exception {
        return null;
    }

    @Override
    public void createNoteRecipient(Map in) throws Exception {

    }

    @Override
    public void deleteNoteRecipient(Map in) throws Exception {

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
    public Map getMyCategory(Map in) throws Exception {
        Map out = iCategoryBService.getCategory(in);
        return out;
    }

    @Override
    public Map getMyDefaultCategory(Map in) throws Exception {
        Map out = iCategoryBService.getMyDefaultCategory(in);
        return out;
    }

    @Override
    public Map totalMyNote(Map in) throws Exception {
        Map out = iNoteBService.totalNote(in);
        return out;
    }

    @Override
    public Map getMyNoteTiny(Map in) throws Exception {
        Map out = iNoteBService.getNoteTiny(in);
        return out;
    }
}
