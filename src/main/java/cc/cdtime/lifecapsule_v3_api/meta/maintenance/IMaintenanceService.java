package cc.cdtime.lifecapsule_v3_api.meta.maintenance;

import cc.cdtime.lifecapsule_v3_api.meta.category.entity.CategoryView;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;

import java.util.ArrayList;
import java.util.Map;

public interface IMaintenanceService {
    ArrayList<NoteView> listNoteOld() throws Exception;

    void updateNoteOld(Map qIn) throws Exception;

    ArrayList<UserView> listUserOld() throws Exception;

    ArrayList<CategoryView> listCategoryOld() throws Exception;
}
