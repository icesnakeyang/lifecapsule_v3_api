package cc.cdtime.lifecapsule_v3_api.admin.maintenance;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUserView;
import cc.cdtime.lifecapsule_v3_api.meta.maintenance.IMaintenanceService;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserBase;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKey;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserLoginName;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.admin.IAdminUserMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserEncodeKeyMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AdminMaintenanceBService implements IAdminMaintenanceBService {
    private final IAdminUserMiddle iAdminUserMiddle;
    private final IMaintenanceService iMaintenanceService;
    private final IUserMiddle iUserMiddle;
    private final INoteMiddle iNoteMiddle;
    private final IUserEncodeKeyMiddle iUserEncodeKeyMiddle;

    public AdminMaintenanceBService(IAdminUserMiddle iAdminUserMiddle,
                                    IMaintenanceService iMaintenanceService,
                                    IUserMiddle iUserMiddle,
                                    INoteMiddle iNoteMiddle,
                                    IUserEncodeKeyMiddle iUserEncodeKeyMiddle) {
        this.iAdminUserMiddle = iAdminUserMiddle;
        this.iMaintenanceService = iMaintenanceService;
        this.iUserMiddle = iUserMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iUserEncodeKeyMiddle = iUserEncodeKeyMiddle;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void restoreOldDatabase(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

//        ArrayList<Maintenance> noteOldList = iMaintenanceService.listNoteOld();

        ArrayList<UserView> userViewsOld = iMaintenanceService.listUserOld();

        /**
         * 把旧的user表里的数据添加到新的user表里
         */
        for (int i = 0; i < userViewsOld.size(); i++) {
            UserView userViewOld = userViewsOld.get(i);
            qIn = new HashMap();
            qIn.put("userId", userViewOld.getUserId());
            UserView userViewNow = iUserMiddle.getUser(qIn, true, false);
            int cc = 0;
            if (userViewNow == null) {
                UserLoginName userLoginName = new UserLoginName();
                if (userViewOld.getPhone() != null) {
                    if (userViewOld.getPhone().equals("19113161230")) {
                        userLoginName.setLoginName("19113161230_old");
                    } else {
                        userLoginName.setLoginName(userViewOld.getPhone());
                    }
                    cc++;
                }
                if (userViewOld.getEmail() != null) {
                    userLoginName.setLoginName(userViewOld.getEmail());
                    cc++;
                }
                if (cc == 0) {
                    String ss = GogoTools.generateString(16);
                    userLoginName.setLoginName(ss);
                }
                userLoginName.setUserId(userViewOld.getUserId());
                userLoginName.setPassword(userViewOld.getPassword());
                iUserMiddle.createUserLoginName(userLoginName);

                UserBase userBase = new UserBase();
                userBase.setUserId(userViewOld.getUserId());
                userBase.setCreateTime(userViewOld.getCreateTime());
                iUserMiddle.createUserBase(userBase);
            }

        }

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

        /**
         * 把旧笔记数据添加到新笔记表里
         */
//        ArrayList<NoteView> noteOldList = iMaintenanceService.listNoteOld();
//        for(int i=0;i<noteOldList.size();i++){
//            NoteView noteViewOld=noteOldList.get(i);
//
//            NoteView noteViewNow=iNoteMiddle.getNoteTiny(noteViewOld.getNoteId(), true,null);
//            if(noteViewNow==null){
//                NoteInfo noteInfo=new NoteInfo();
//                noteInfo.setStatus(noteViewOld.getStatus());
//                noteInfo.setNoteId(noteViewOld.getNoteId());
//                noteInfo.setEncrypt(noteViewOld.getEncrypt());
//                noteInfo.setContent(noteViewOld.getContent());
//                noteInfo.setCategoryId(noteViewOld.getCategoryId());
//                noteInfo.setCreateTime(noteViewOld.getCreateTime());
//                noteInfo.setTitle(noteViewOld.getTitle());
//                noteInfo.setUserEncodeKey(noteViewOld.getUserEncodeKey());
//                noteInfo.setUserId(noteViewOld.getUserId());
//                iNoteMiddle.createNoteInfo(noteInfo);
//            }
//        }


        Map out = new HashMap();

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map moveContentToIndex(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

//        ArrayList<Maintenance> noteOldList = iMaintenanceService.listNoteOld();

        ArrayList<NoteView> noteViews = iMaintenanceService.listAllNote();

        int cContent = 0;
        int cKey = 0;
        for (int i = 0; i < noteViews.size(); i++) {
            NoteView noteView = noteViews.get(i);
            if (noteView.getUserEncodeKey() != null) {
                cKey++;
                /**
                 * 在user_encode_key表里创建
                 */
                UserEncodeKey userEncodeKey = new UserEncodeKey();
                userEncodeKey.setIndexId(noteView.getNoteId());
                userEncodeKey.setEncodeKey(noteView.getUserEncodeKey());
                userEncodeKey.setUserId(noteView.getUserId());
                userEncodeKey.setEncodeKeyId(GogoTools.UUID32());
                userEncodeKey.setCreateTime(new Date());
                iUserEncodeKeyMiddle.createUserEncodeKey(userEncodeKey);
            }

        }

        Map out = new HashMap();
        out.put("cContent", cContent);
        out.put("cKey", cKey);

        return out;
    }
}
