package cc.cdtime.lifecapsule_v3_api.admin.maintenance;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUserView;
import cc.cdtime.lifecapsule_v3_api.meta.category.entity.Category;
import cc.cdtime.lifecapsule_v3_api.meta.category.entity.CategoryView;
import cc.cdtime.lifecapsule_v3_api.meta.maintenance.IMaintenanceService;
import cc.cdtime.lifecapsule_v3_api.meta.maintenance.Maintenance;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserBase;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserLoginName;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.admin.IAdminUserMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.category.ICategoryMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AdminMaintenanceBService implements IAdminMaintenanceBService {
    private final IAdminUserMiddle iAdminUserMiddle;
    private final IMaintenanceService iMaintenanceService;
    private final IUserMiddle iUserMiddle;
    private final ICategoryMiddle iCategoryMiddle;
    private final INoteMiddle iNoteMiddle;

    public AdminMaintenanceBService(IAdminUserMiddle iAdminUserMiddle,
                                    IMaintenanceService iMaintenanceService,
                                    IUserMiddle iUserMiddle,
                                    ICategoryMiddle iCategoryMiddle,
                                    INoteMiddle iNoteMiddle) {
        this.iAdminUserMiddle = iAdminUserMiddle;
        this.iMaintenanceService = iMaintenanceService;
        this.iUserMiddle = iUserMiddle;
        this.iCategoryMiddle = iCategoryMiddle;
        this.iNoteMiddle = iNoteMiddle;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void restoreOldDatabase(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

//        ArrayList<Maintenance> noteOldList = iMaintenanceService.listNoteOld();

        ArrayList<UserView> userViews = iMaintenanceService.listUserOld();

//        for (int i = 0; i < userViews.size(); i++) {
//            UserView userView = userViews.get(i);
//            UserLoginName userLoginName = new UserLoginName();
//            if (userView.getPhone() != null) {
//                userLoginName.setLoginName(userView.getPhone());
//            }
//            if (userView.getEmail() != null) {
//                userLoginName.setLoginName(userView.getEmail());
//            }
//            userLoginName.setUserId(userView.getUserId());
//            userLoginName.setPassword(userView.getPassword());
//            iUserMiddle.createUserLoginName(userLoginName);
//
//            UserBase userBase = new UserBase();
//            userBase.setUserId(userView.getUserId());
//            userBase.setCreateTime(userView.getCreateTime());
//            iUserMiddle.createUserBase(userBase);
//        }

//        ArrayList<CategoryView> categoryViews = iMaintenanceService.listCategoryOld();
//        for (int i = 0; i < categoryViews.size(); i++) {
//            CategoryView categoryView = categoryViews.get(i);
//
//            Category category = new Category();
//            category.setRemark(categoryView.getRemark());
//            category.setCategoryId(categoryView.getCategoryId());
//            category.setCategoryName(categoryView.getCategoryName());
//            category.setUserId(categoryView.getUserId());
//            category.setNoteType(categoryView.getNoteType());
//            iCategoryMiddle.createCategory(category);
//
//        }

        ArrayList<NoteView> noteOldList = iMaintenanceService.listNoteOld();
        for(int i=0;i<noteOldList.size();i++){
            NoteView noteView=noteOldList.get(i);

            NoteInfo noteInfo=new NoteInfo();
            noteInfo.setStatus(noteView.getStatus());
            noteInfo.setNoteId(noteView.getNoteId());
            noteInfo.setEncrypt(noteView.getEncrypt());
            noteInfo.setContent(noteView.getContent());
            noteInfo.setCategoryId(noteView.getCategoryId());
            noteInfo.setCreateTime(noteView.getCreateTime());
            noteInfo.setTitle(noteView.getTitle());
            noteInfo.setUserEncodeKey(noteView.getUserEncodeKey());
            noteInfo.setUserId(noteView.getUserId());
            iNoteMiddle.createNoteInfo(noteInfo);
        }


        Map out = new HashMap();

    }
}
