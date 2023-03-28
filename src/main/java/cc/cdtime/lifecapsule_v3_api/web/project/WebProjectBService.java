package cc.cdtime.lifecapsule_v3_api.web.project;

import cc.cdtime.lifecapsule_v3_api.business.project.IProjectBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebProjectBService implements IWebProjectBService {
    private final IProjectBService iProjectBService;

    public WebProjectBService(IProjectBService iProjectBService) {
        this.iProjectBService = iProjectBService;
    }

    @Override
    public void saveMyProject(Map in) throws Exception {
        iProjectBService.saveProject(in);
    }

    @Override
    public Map listMyProject(Map in) throws Exception {
        Map out = iProjectBService.listProject(in);
        return out;
    }

    @Override
    public Map getProject(Map in) throws Exception {
        Map out = iProjectBService.getProject(in);
        return out;
    }
}
