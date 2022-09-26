package cc.cdtime.lifecapsule_v3_api.web.trigger;

import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import cc.cdtime.lifecapsule_v3_api.framework.vo.TriggerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/web/trigger")
public class WebTriggerController {
    private final IWebTriggerBService iWebTriggerBService;

    public WebTriggerController(IWebTriggerBService iWebTriggerBService) {
        this.iWebTriggerBService = iWebTriggerBService;
    }
}
