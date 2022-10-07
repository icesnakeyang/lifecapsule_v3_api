package cc.cdtime.lifecapsule_v3_api.middle.author;

import cc.cdtime.lifecapsule_v3_api.meta.author.dao.AuthorLogDao;
import cc.cdtime.lifecapsule_v3_api.meta.author.entity.AuthorLog;
import cc.cdtime.lifecapsule_v3_api.meta.author.entity.AuthorLogView;
import cc.cdtime.lifecapsule_v3_api.meta.author.service.IAuthorLogService;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class AuthorMiddle implements IAuthorMiddle {
    private final IAuthorLogService iAuthorLogService;

    public AuthorMiddle(IAuthorLogService iAuthorLogService) {
        this.iAuthorLogService = iAuthorLogService;
    }

    @Override
    public void createAuthorLog(AuthorLog authorLog) throws Exception {
        iAuthorLogService.createAuthorLog(authorLog);
    }

    @Override
    public AuthorLogView getAuthorLog(Map qIn) throws Exception {
        AuthorLogView authorLogView = iAuthorLogService.getAuthorLog(qIn);
        return authorLogView;
    }

    @Override
    public ArrayList<AuthorLogView> listAuthorLog(Map qIn) throws Exception {
        ArrayList<AuthorLogView> authorLogViews = iAuthorLogService.listAuthorLog(qIn);
        return authorLogViews;
    }

    @Override
    public void updateAuthorLog(Map qIn) throws Exception {
        iAuthorLogService.updateAuthorLog(qIn);
    }
}
