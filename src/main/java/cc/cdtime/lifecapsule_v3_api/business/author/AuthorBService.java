package cc.cdtime.lifecapsule_v3_api.business.author;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.meta.author.entity.AuthorLogView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.author.IAuthorMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthorBService implements IAuthorBService {
    private final IUserMiddle iUserMiddle;
    private final IAuthorMiddle iAuthorMiddle;

    public AuthorBService(IUserMiddle iUserMiddle,
                          IAuthorMiddle iAuthorMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iAuthorMiddle = iAuthorMiddle;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map getAuthorInfo(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        qIn.put("status", ESTags.DEFAULT);
        AuthorLogView authorLogView = iAuthorMiddle.getAuthorLog(qIn);

        String userName = userView.getNickname();

        if (authorLogView != null) {
            userName = authorLogView.getAuthorName();
        }

        Map out = new HashMap();
        out.put("authorName", userName);

        return out;
    }

    @Override
    public Map listMyAuthorInfo(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        ArrayList<AuthorLogView> authorLogViews = iAuthorMiddle.listAuthorLog(qIn);

        Map out=new HashMap();
        out.put("authorList", authorLogViews);

        return out;
    }
}
