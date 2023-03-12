package cc.cdtime.lifecapsule_v3_api.meta.project.dao;

import cc.cdtime.lifecapsule_v3_api.meta.project.entity.Project;
import cc.cdtime.lifecapsule_v3_api.meta.project.entity.ProjectView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface ProjectDao {
    void createProject(Project project);

    ArrayList<ProjectView> listProject(Map qIn);

    /**
     * 读取一个项目
     *
     * @param qIn projectName
     *            projectId
     *            user_id
     * @return
     */
    ProjectView getProject(Map qIn);

    /**
     * 修改项目
     *
     * @param qIn projectName
     *            projectId
     */
    void updateProject(Map qIn);
}
