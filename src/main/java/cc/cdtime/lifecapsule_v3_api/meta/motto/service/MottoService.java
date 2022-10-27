package cc.cdtime.lifecapsule_v3_api.meta.motto.service;

import cc.cdtime.lifecapsule_v3_api.meta.motto.dao.MottoDao;
import cc.cdtime.lifecapsule_v3_api.meta.motto.entity.Motto;
import cc.cdtime.lifecapsule_v3_api.meta.motto.entity.MottoLog;
import cc.cdtime.lifecapsule_v3_api.meta.motto.entity.MottoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class MottoService implements IMottoService {
    private final MottoDao mottoDao;

    public MottoService(MottoDao mottoDao) {
        this.mottoDao = mottoDao;
    }

    @Override
    public void createMotto(Motto motto) throws Exception {
        mottoDao.createMotto(motto);
    }

    @Override
    public MottoView getMotto(Map qIn) throws Exception {
        MottoView mottoView = mottoDao.getMotto(qIn);
        return mottoView;
    }

    @Override
    public void createMottoLog(MottoLog mottoLog) throws Exception {
        mottoDao.createMottoLog(mottoLog);
    }

    @Override
    public void updateMotto(Map qIn) throws Exception {
        mottoDao.updateMotto(qIn);
    }

    @Override
    public ArrayList<MottoView> listMotto(Map qIn) throws Exception {
        ArrayList<MottoView> mottoViews = mottoDao.listMotto(qIn);
        return mottoViews;
    }

    @Override
    public Integer totalMotto(Map qIn) throws Exception {
        Integer total = mottoDao.totalMotto(qIn);
        return total;
    }
}
