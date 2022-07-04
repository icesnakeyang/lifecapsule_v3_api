package cc.cdtime.lifecapsule_v3_api.middle.security;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.security.entity.SecurityKey;
import cc.cdtime.lifecapsule_v3_api.meta.security.service.ISecurityService;
import org.springframework.stereotype.Service;

@Service
public class SecurityMiddle implements ISecurityMiddle {
    private final ISecurityService iSecurityService;

    public SecurityMiddle(ISecurityService iSecurityService) {
        this.iSecurityService = iSecurityService;
    }

    @Override
    public void saveRSA(SecurityKey securityKey) throws Exception {
        iSecurityService.saveRSA(securityKey);
    }

    @Override
    public String getRSAKey(String keyToken) throws Exception {
        String privateKey = iSecurityService.getRSAKey(keyToken);
        return privateKey;
    }

    @Override
    public void deleteRSAKey(String keyToken) throws Exception {
        iSecurityService.deleteRSAKey(keyToken);
    }

    @Override
    public String takeNoteAES(String keyToken, String encryptKey) throws Exception {
        String privateKey = iSecurityService.getRSAKey(keyToken);
        String strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);
        iSecurityService.deleteRSAKey(keyToken);
        return strAESKey;
    }
}
