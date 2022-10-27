package cc.cdtime.lifecapsule_v3_api.business.motto;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.motto.entity.Motto;
import cc.cdtime.lifecapsule_v3_api.meta.motto.entity.MottoLog;
import cc.cdtime.lifecapsule_v3_api.meta.motto.entity.MottoView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.motto.IMottoMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MottoBService implements IMottoBService {
    private final IUserMiddle iUserMiddle;
    private final IMottoMiddle iMottoMiddle;

    public MottoBService(IUserMiddle iUserMiddle, IMottoMiddle iMottoMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iMottoMiddle = iMottoMiddle;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publishMotto(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();
        String content = in.get("content").toString();
        String authorName = in.get("authorName").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Motto motto = new Motto();
        motto.setMottoId(GogoTools.UUID32());
        motto.setCreateTime(new Date());
        motto.setStatus(ESTags.ACTIVE.toString());
        motto.setNoteId(noteId);
        motto.setContent(content);
        motto.setUserId(userView.getUserId());
        motto.setAuthorName(authorName);
        iMottoMiddle.createMotto(motto);
    }

    @Override
    public Map getMottoRandom(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("random", true);
        qIn.put("status", ESTags.ACTIVE);
        MottoView mottoView = iMottoMiddle.getMotto(qIn, true);

        Map out = new HashMap();
        out.put("motto", mottoView);

        return out;

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void likeMotto(Map in) throws Exception {
        String token = in.get("token").toString();
        String mottoId = in.get("mottoId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("mottoId", mottoId);
        MottoView mottoView = iMottoMiddle.getMotto(qIn, false);

        /**
         * 创建motto日志
         * 更改likes
         */

        qIn = new HashMap();
        qIn.put("mottoId", mottoView.getMottoId());
        qIn.put("userId", userView.getUserId());
        ArrayList<MottoView> mottoViews = iMottoMiddle.listMotto(qIn);
        if (mottoViews.size() > 0) {
            //当前用户已经点过赞了
            throw new Exception("10061");
        }

        MottoLog mottoLog = new MottoLog();
        mottoLog.setMottoId(mottoView.getMottoId());
        mottoLog.setLogType(ESTags.USER_LIKE_MOTTO.toString());
        mottoLog.setCreateTime(new Date());
        mottoLog.setUserId(userView.getUserId());
        iMottoMiddle.createMottoLog(mottoLog);

        qIn = new HashMap();
        qIn.put("mottoId", mottoView.getMottoId());
        qIn.put("likes", mottoView.getLikes() + 1);
        iMottoMiddle.updateMotto(qIn);
    }
}
