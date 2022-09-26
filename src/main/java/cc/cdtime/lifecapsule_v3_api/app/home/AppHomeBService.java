package cc.cdtime.lifecapsule_v3_api.app.home;

import cc.cdtime.lifecapsule_v3_api.business.home.statistic.IHomeStatisticBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppHomeBService implements IAppHomeBService {
    private final IHomeStatisticBService iHomeStatisticBService;

    public AppHomeBService(IHomeStatisticBService iHomeStatisticBService) {
        this.iHomeStatisticBService = iHomeStatisticBService;
    }

    @Override
    public Map loadMyNoteSendStatistic(Map in) throws Exception {
        Map out = iHomeStatisticBService.loadMyNoteSendStatistic(in);
        return out;
    }
}
