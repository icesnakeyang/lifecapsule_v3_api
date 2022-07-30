package cc.cdtime.lifecapsule_v3_api.web.quadTask;

import cc.cdtime.lifecapsule_v3_api.business.quadTask.IQuadTaskBService;
import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebQuadTaskBService implements IWebQuadTaskBService {
    private final IQuadTaskBService iQuadTaskBService;

    public WebQuadTaskBService(IQuadTaskBService iQuadTaskBService) {
        this.iQuadTaskBService = iQuadTaskBService;
    }

    @Override
    public Map listMyQuadTask(Map in) throws Exception {
        Map out = iQuadTaskBService.listQuadTask(in);
        return out;
    }

    @Override
    public void createMyQuadTask(Map in) throws Exception {
        iQuadTaskBService.createQuadTask(in);
    }

    @Override
    public void updateMyQuadTask(Map in) throws Exception {
        iQuadTaskBService.updateQuadTask(in);
    }

    @Override
    public Map getMyQuadTask(Map in) throws Exception {
        Map out = iQuadTaskBService.getQuadTask(in);
        return out;
    }

    @Override
    public void setMyTaskComplete(Map in) throws Exception {
        in.put("status", ESTags.COMPLETE);
        iQuadTaskBService.setTaskStatus(in);
    }

    @Override
    public void setMyTaskProgress(Map in) throws Exception {
        in.put("status", ESTags.PROGRESS);
        iQuadTaskBService.setTaskStatus(in);
    }

    @Override
    public void increaseQuadTaskPriority(Map in) throws Exception {
        iQuadTaskBService.increaseQuadTaskPriority(in);
    }

    @Override
    public void decreaseQuadTaskPriority(Map in) throws Exception {
        iQuadTaskBService.decreaseQuadTaskPriority(in);
    }

    @Override
    public void deleteQuadTask(Map in) throws Exception {
        iQuadTaskBService.deleteTask(in);
    }
}
