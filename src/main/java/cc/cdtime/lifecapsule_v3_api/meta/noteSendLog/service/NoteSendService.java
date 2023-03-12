package cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.service;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.dao.NoteSendDao;
import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLog;
import cc.cdtime.lifecapsule_v3_api.meta.noteSendLog.entity.NoteSendLogView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKey;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Service
public class NoteSendService implements INoteSendService {
    private final NoteSendDao noteSendDao;

    public NoteSendService(NoteSendDao noteSendDao) {
        this.noteSendDao = noteSendDao;
    }

    @Override
    public void createNoteSendLog(NoteSendLog noteSendLog) throws Exception {
        noteSendDao.createNoteSendLog(noteSendLog);
    }

    @Override
    public ArrayList<NoteSendLogView> listNoteSendLog(Map qIn) throws Exception {
        ArrayList<NoteSendLogView> noteSendLogViews = noteSendDao.listNoteSendLog(qIn);
        return noteSendLogViews;
    }

    @Override
    public Integer totalNoteSendLog(Map qIn) throws Exception {
        Integer total = noteSendDao.totalNoteSendLog(qIn);
        return total;
    }

    @Override
    public NoteSendLogView getNoteSendLog(Map qIn) throws Exception {
        NoteSendLogView noteSendLogView = noteSendDao.getNoteSendLog(qIn);
        return noteSendLogView;
    }

    @Override
    public void updateNoteSendLog(Map qIn) throws Exception {
        noteSendDao.updateNoteSendLog(qIn);
    }

    @Override
    public void deleteNoteSendLog(String sendLogId) throws Exception {
        noteSendDao.deleteNoteSendLog(sendLogId);
    }
}
