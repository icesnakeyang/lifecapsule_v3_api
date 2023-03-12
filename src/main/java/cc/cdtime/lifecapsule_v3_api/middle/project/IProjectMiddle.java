package cc.cdtime.lifecapsule_v3_api.middle.project;

import cc.cdtime.lifecapsule_v3_api.meta.project.entity.Project;
import cc.cdtime.lifecapsule_v3_api.meta.project.entity.ProjectView;

import java.util.ArrayList;
import java.util.Map;

public interface IProjectMiddle {
    void createProject(Project project) throws Exception;

    ArrayList<ProjectView> listProject(Map qIn) throws Exception;

    /**
     * 读取一个项目
     *
     * @param qIn projectName
     *            projectId
     *            user_id
     * @return
     */
    ProjectView getProject(Map qIn, Boolean returnNull) throws Exception;

    /**
     * 修改项目
     *
     * @param qIn projectName
     *            projectId
     */
    void updateProject(Map qIn) throws Exception;
}
