package cc.cdtime.lifecapsule_v3_api.business.adminStatistic;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminStatisticView;
import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUserView;
import cc.cdtime.lifecapsule_v3_api.meta.email.entity.UserEmailView;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.meta.userAct.entity.UserActView;
import cc.cdtime.lifecapsule_v3_api.middle.admin.IAdminStatisticMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.admin.IAdminUserMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminStatisticBService implements IAdminStatisticBService {
    private final IAdminUserMiddle iAdminUserMiddle;
    private final IAdminStatisticMiddle iAdminStatisticMiddle;
    private final IUserMiddle iUserMiddle;
    private final INoteMiddle iNoteMiddle;

    public AdminStatisticBService(IAdminUserMiddle iAdminUserMiddle,
                                  IAdminStatisticMiddle iAdminStatisticMiddle,
                                  IUserMiddle iUserMiddle,
                                  INoteMiddle iNoteMiddle) {
        this.iAdminUserMiddle = iAdminUserMiddle;
        this.iAdminStatisticMiddle = iAdminStatisticMiddle;
        this.iUserMiddle = iUserMiddle;
        this.iNoteMiddle = iNoteMiddle;
    }

    @Override
    public Map totalUserLogin(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");
        Date startTime = (Date) in.get("startTime");
        Date endTime = (Date) in.get("endTime");

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        if (startTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String s = sdf.format(startTime);
            startTime = sdf.parse(s);
        }
        qIn.put("startTime", startTime);
        if (endTime != null) {
            /**
             * 如果有endtime，endtime需要+1天
             */
            long time = endTime.getTime();
            endTime.setTime(time + 1000 * 3600 * 24);
        }
        qIn.put("endTime", endTime);
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);

        ArrayList<AdminStatisticView> adminStatisticViews = iAdminStatisticMiddle.totalUserLogin(qIn);

        Map out = new HashMap();

        for (int i = 0; i < adminStatisticViews.size(); i++) {
            qIn = new HashMap();
            qIn.put("userId", adminStatisticViews.get(i).getUserId());
            UserView userView = iUserMiddle.getUser(qIn, true, false);
            adminStatisticViews.get(i).setNickname(userView.getNickname());
            adminStatisticViews.get(i).setEmail(userView.getEmail());
        }
        out.put("statistics", adminStatisticViews);

        return out;

    }

    @Override
    public Map listTopNote(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        qIn.put("size", 10);

        ArrayList<NoteView> noteViews = iAdminStatisticMiddle.listNoteInfo(qIn);

        Map out = new HashMap();
        out.put("noteList", noteViews);

        return out;
    }

    @Override
    public Map loadUserStatistic(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        Integer totalUserLogs = iUserMiddle.totalUserLoginLog(qIn);
        Integer totalUser = iUserMiddle.totalUser(qIn);
        Integer totalNote = iNoteMiddle.totalNote(qIn);

        /**
         * 今日日活
         * 今天有用户行为记录的用户总数
         * 1个用户只能算1次日活
         */
        qIn = new HashMap();
        qIn.put("startTime", GogoTools.cutTime(new Date()));
        qIn.put("endTime", new Date());
        Integer totalDUA = iAdminStatisticMiddle.totalUserAct(qIn);


        Map out = new HashMap();
        out.put("totalUserLogs", totalUserLogs);
        out.put("totalUser", totalUser);
        out.put("totalNote", totalNote);
        out.put("totalDUA", totalDUA);

        return out;
    }

    @Override
    public Map loadUserData(Map in) throws Exception {
        String token = in.get("token").toString();
        String userId = in.get("userId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        qIn.put("userId", userId);
        UserView userView = iUserMiddle.getUser(qIn, false, false);

        /**
         * 读取用户最近的登录时间
         */

        /**
         * 读取用户
         */

        Map out = new HashMap();
        out.put("userData", userView);

        return out;
    }

    @Override
    public Map listUserBindEmail(Map in) throws Exception {
        String token = in.get("token").toString();
        String emailKey = in.get("emailKey").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        qIn.put("emailKey", emailKey);
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        ArrayList<UserEmailView> userEmailViews = iAdminUserMiddle.listUserEmail(qIn);

        Map out = new HashMap();
        out.put("userEmailList", userEmailViews);

        return out;
    }
}
