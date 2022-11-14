package cc.cdtime.lifecapsule_v3_api.business.adminTopic;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUserView;
import cc.cdtime.lifecapsule_v3_api.meta.topic.entity.TopicView;
import cc.cdtime.lifecapsule_v3_api.middle.admin.IAdminTopicMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.admin.IAdminUserMiddle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminTopicBService implements IAdminTopicBService {
    private final IAdminUserMiddle iAdminUserMiddle;
    private final IAdminTopicMiddle iAdminTopicMiddle;

    public AdminTopicBService(IAdminUserMiddle iAdminUserMiddle, IAdminTopicMiddle iAdminTopicMiddle) {
        this.iAdminUserMiddle = iAdminUserMiddle;
        this.iAdminTopicMiddle = iAdminTopicMiddle;
    }

    @Override
    public void removeTopic(Map in) throws Exception {
        String token = in.get("token").toString();
        String topicId = in.get("topicId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        qIn.put("topicId", topicId);
        qIn.put("status", ESTags.ADMIN_REMOVE);
        iAdminTopicMiddle.updateTopic(qIn);
    }

    @Override
    public Map listTopic(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");
        Boolean includeChildren = (Boolean) in.get("includeChildren");
        String status = (String) in.get("status");

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        if (includeChildren) {
            qIn.put("isRoot", false);
        } else {
            qIn.put("isRoot", true);
        }
        if (status != null && !status.equals("")) {
            qIn.put("status", status);
        }
        ArrayList<TopicView> topicViews = iAdminTopicMiddle.listTopic(qIn);
        Integer totalTopic = iAdminTopicMiddle.totalTopic(qIn);

        Map out = new HashMap();
        out.put("topicList", topicViews);
        out.put("totalTopic", totalTopic);

        return out;
    }

    @Override
    public Map getTopic(Map in) throws Exception {
        String token = in.get("token").toString();
        String topicId = in.get("topicId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        TopicView topicView = iAdminTopicMiddle.getTopic(topicId);

        Map out = new HashMap();
        out.put("topic", topicView);

        return out;
    }

    @Override
    public void activeTopic(Map in) throws Exception {
        String token = in.get("token").toString();
        String topicId = in.get("topicId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        qIn.put("topicId", topicId);
        qIn.put("status", ESTags.ACTIVE);
        iAdminTopicMiddle.updateTopic(qIn);
    }
}
