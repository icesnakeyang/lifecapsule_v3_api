package cc.cdtime.lifecapsule_v3_api.business.project;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.project.entity.Project;
import cc.cdtime.lifecapsule_v3_api.meta.project.entity.ProjectView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.project.IProjectMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProjectBService implements IProjectBService {
    private final IUserMiddle iUserMiddle;
    private final IProjectMiddle iProjectMiddle;

    public ProjectBService(IUserMiddle iUserMiddle,
                           IProjectMiddle iProjectMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iProjectMiddle = iProjectMiddle;
    }

    @Override
    public Map listProject(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        ArrayList<ProjectView> projectViews = iProjectMiddle.listProject(qIn);

        Map out = new HashMap();
        out.put("projectList", projectViews);

        return out;
    }

    @Override
    public Map getProject(Map in) throws Exception {
        String token = in.get("token").toString();
        String projectName = (String) in.get("projectName");
        String projectId = (String) in.get("projectId");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("projectName", projectName);
        qIn.put("projectId", projectId);
        qIn.put("userId", userView.getUserId());
        ProjectView projectView = iProjectMiddle.getProject(qIn, true);

        Map out = new HashMap();
        out.put("project", projectView);

        return out;
    }

    @Override
    public void saveProject(Map in) throws Exception {
        String token = in.get("token").toString();
        String projectName = in.get("projectName").toString();
        String projectId = (String) in.get("projectId");

        if (projectName.equals("")) {
            //项目名称不能为空
            throw new Exception("10082");
        }

        /**
         * 读取当前用户
         */
        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        if (projectId != null) {
            /**
             * 有projectId，查看，如果查询到，返回错误
             */
            qIn = new HashMap();
            qIn.put("userId", userView.getUserId());
            qIn.put("projectId", projectId);
            ProjectView projectView = iProjectMiddle.getProject(qIn, false);

            /**
             * 判断项目名称是否一致，不一致修改，一致不处理
             */
            if (!projectView.getProjectName().equals(projectName)) {
                qIn.put("projectName", projectName);
                iProjectMiddle.updateProject(qIn);
            }
        } else {
            qIn = new HashMap();
            qIn.put("userId", userView.getUserId());
            qIn.put("projectName", projectName);
            ProjectView projectView = iProjectMiddle.getProject(qIn, true);
            if (projectView == null) {
                //新增
                Project project = new Project();
                project.setProjectId(GogoTools.UUID32());
                project.setProjectName(projectName);
                project.setCreateTime(new Date());
                project.setUserId(userView.getUserId());
                iProjectMiddle.createProject(project);
            } else {
                //已存在，不处理
            }
        }
    }
}
