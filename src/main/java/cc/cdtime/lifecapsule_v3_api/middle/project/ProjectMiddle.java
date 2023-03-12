package cc.cdtime.lifecapsule_v3_api.middle.project;

import cc.cdtime.lifecapsule_v3_api.meta.project.entity.Project;
import cc.cdtime.lifecapsule_v3_api.meta.project.entity.ProjectView;
import cc.cdtime.lifecapsule_v3_api.meta.project.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class ProjectMiddle implements IProjectMiddle {
    private final IProjectService iProjectService;

    public ProjectMiddle(IProjectService iProjectService) {
        this.iProjectService = iProjectService;
    }

    @Override
    public void createProject(Project project) throws Exception {
        iProjectService.createProject(project);
    }

    @Override
    public ArrayList<ProjectView> listProject(Map qIn) throws Exception {
        ArrayList<ProjectView> projectViews = iProjectService.listProject(qIn);
        return projectViews;
    }

    @Override
    public ProjectView getProject(Map qIn, Boolean returnNull) throws Exception {
        ProjectView projectView = iProjectService.getProject(qIn);
        if (projectView == null) {
            if (returnNull) {
                return null;
            }
            //没有查询到该项目
            throw new Exception("10081");
        }
        return projectView;
    }

    @Override
    public void updateProject(Map qIn) throws Exception {
        iProjectService.updateProject(qIn);
    }
}
