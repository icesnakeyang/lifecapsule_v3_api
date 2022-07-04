package cc.cdtime.lifecapsule_v3_api.meta.security.service;

import cc.cdtime.lifecapsule_v3_api.meta.security.entity.SecurityKey;

public interface ISecurityService {
    /**
     * 保存一个私钥
     *
     * @param securityKey
     */
    void saveRSA(SecurityKey securityKey) throws Exception;

    /**
     * 读取一个私钥
     *
     * @param keyToken
     * @return
     */
    String getRSAKey(String keyToken) throws Exception;

    /**
     * 删除一个私钥
     *
     * @param keyToken
     */
    void deleteRSAKey(String keyToken) throws Exception;
}
