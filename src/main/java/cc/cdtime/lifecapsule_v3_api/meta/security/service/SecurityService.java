package cc.cdtime.lifecapsule_v3_api.meta.security.service;

import cc.cdtime.lifecapsule_v3_api.meta.security.dao.SecurityDao;
import cc.cdtime.lifecapsule_v3_api.meta.security.entity.SecurityKey;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements ISecurityService{
    private final SecurityDao securityDao;

    public SecurityService(SecurityDao securityDao) {
        this.securityDao = securityDao;
    }

    @Override
    public void saveRSA(SecurityKey securityKey) throws Exception {
        securityDao.saveRSA(securityKey);
    }

    @Override
    public String getRSAKey(String keyToken) throws Exception {
        SecurityKey securityKey = securityDao.getRSAPrivateKey(keyToken);
        return securityKey.getPrivateRSA();
    }

    @Override
    public void deleteRSAKey(String keyToken) throws Exception {
        securityDao.deleteRSAKey(keyToken);
    }
}
