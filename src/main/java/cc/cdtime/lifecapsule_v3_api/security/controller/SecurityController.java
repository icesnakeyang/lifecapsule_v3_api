package cc.cdtime.lifecapsule_v3_api.security.controller;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.framework.vo.Response;
import cc.cdtime.lifecapsule_v3_api.security.business.ISecurityBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule3_api/security")
public class SecurityController {
    private final ISecurityBService iSecurityBService;

    public SecurityController(ISecurityBService iSecurityBService) {
        this.iSecurityBService = iSecurityBService;
    }

    /**
     * 请求一个RSA公钥
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/request_rsa_public_key")
    public Response requestRSAPublicKey() {
        Response response = new Response();
        try {
            Map in = new HashMap();

            Map keyPairRSAMap = GogoTools.generateRSAKey();
            String keyToken = GogoTools.UUID32();
            in.put("privateKey", keyPairRSAMap.get("privateKey"));
            in.put("keyToken", keyToken);
            iSecurityBService.saveRSAKey(in);

            Map out = new HashMap();
            out.put("publicKey", keyPairRSAMap.get("publicKey"));
            out.put("keyToken", keyToken);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("requestRSAPublicKey error:" + ex.getMessage());
            }
        }
        return response;
    }
}
