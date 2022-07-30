package cc.cdtime.lifecapsule_v3_api.meta.maintenance;

import cc.cdtime.lifecapsule_v3_api.meta.category.entity.CategoryView;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface MaintenanceDao {
    ArrayList<NoteView> listNoteOld();

    void updateNoteOld(Map qIn);

    ArrayList<UserView> listUserOld();

    ArrayList<CategoryView> listCategoryOld();
}
