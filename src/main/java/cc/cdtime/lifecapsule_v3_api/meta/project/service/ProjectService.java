package cc.cdtime.lifecapsule_v3_api.meta.project.service;

import cc.cdtime.lifecapsule_v3_api.meta.project.dao.ProjectDao;
import cc.cdtime.lifecapsule_v3_api.meta.project.entity.Project;
import cc.cdtime.lifecapsule_v3_api.meta.project.entity.ProjectView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class ProjectService implements IProjectService {
    private final ProjectDao projectDao;

    public ProjectService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public void createProject(Project project) throws Exception {
        projectDao.createProject(project);
    }

    @Override
    public ArrayList<ProjectView> listProject(Map qIn) throws Exception {
        ArrayList<ProjectView> arrayList = projectDao.listProject(qIn);
        return arrayList;
    }

    @Override
    public ProjectView getProject(Map qIn) throws Exception {
        ProjectView projectView = projectDao.getProject(qIn);
        return projectView;
    }

    @Override
    public void updateProject(Map qIn) throws Exception {
        projectDao.updateProject(qIn);
    }
}
