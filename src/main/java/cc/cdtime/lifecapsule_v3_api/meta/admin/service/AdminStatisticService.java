package cc.cdtime.lifecapsule_v3_api.meta.admin.service;

import cc.cdtime.lifecapsule_v3_api.meta.admin.dao.AdminStatisticDao;
import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminStatisticView;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.userAct.entity.UserActView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class AdminStatisticService implements IAdminStatisticService {
    private final AdminStatisticDao adminStatisticDao;

    public AdminStatisticService(AdminStatisticDao adminStatisticDao) {
        this.adminStatisticDao = adminStatisticDao;
    }

    @Override
    public ArrayList<AdminStatisticView> totalUserLogin(Map qIn) throws Exception {
        ArrayList<AdminStatisticView> adminStatisticViews = adminStatisticDao.totalUserLogin(qIn);
        return adminStatisticViews;
    }

    @Override
    public ArrayList<NoteView> listNoteInfo(Map qIn) throws Exception {
        ArrayList<NoteView> noteViews = adminStatisticDao.listNoteInfo(qIn);
        return noteViews;
    }

    @Override
    public Integer totalUserAct(Map qIn) throws Exception {
        Integer total = adminStatisticDao.totalUserAct(qIn);
        return total;
    }
}
