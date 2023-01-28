package cc.cdtime.lifecapsule_v3_api.app.future;

import cc.cdtime.lifecapsule_v3_api.business.future.IFutureBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppFutureBService implements IAppFutureBService {
    private final IFutureBService iFutureBService;

    public AppFutureBService(IFutureBService iFutureBService) {
        this.iFutureBService = iFutureBService;
    }

    @Override
    public Map listFuture(Map in) throws Exception {
        Map out = iFutureBService.listFuture(in);
        return out;
    }

    @Override
    public Map getFuture(Map in) throws Exception {
        Map out = iFutureBService.getFuture(in);
        return out;
    }

    @Override
    public void saveFuture(Map in) throws Exception {
        iFutureBService.saveFuture(in);
    }

    @Override
    public void deleteFuture(Map in) throws Exception {
        iFutureBService.deleteFuture(in);
    }

    @Override
    public Map listFutureTrigger(Map in) throws Exception {
        Map out=iFutureBService.listFutureTrigger(in);
        return out;
    }
}
