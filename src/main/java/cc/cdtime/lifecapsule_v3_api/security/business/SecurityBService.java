package cc.cdtime.lifecapsule_v3_api.security.business;

import cc.cdtime.lifecapsule_v3_api.meta.security.entity.SecurityKey;
import cc.cdtime.lifecapsule_v3_api.middle.security.ISecurityMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SecurityBService implements ISecurityBService{
    private final ISecurityMiddle iSecurityMiddle;

    public SecurityBService(ISecurityMiddle iSecurityMiddle) {
        this.iSecurityMiddle = iSecurityMiddle;
    }

    @Override
    public void saveRSAKey(Map in) throws Exception {
        String privateKey = in.get("privateKey").toString();
        String keyToken = in.get("keyToken").toString();

        SecurityKey key = new SecurityKey();
        key.setKeyToken(keyToken);
        key.setPrivateRSA(privateKey);

        iSecurityMiddle.saveRSA(key);
    }
}
