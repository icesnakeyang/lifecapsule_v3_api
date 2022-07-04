package cc.cdtime.lifecapsule_v3_api.meta.security.dao;

import cc.cdtime.lifecapsule_v3_api.meta.security.entity.SecurityKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SecurityDao {
    /**
     * 保存一个私钥
     *
     * @param securityKey
     */
    void saveRSA(SecurityKey securityKey);

    /**
     * 读取一个私钥
     *
     * @param keyToken
     * @return
     */
    SecurityKey getRSAPrivateKey(String keyToken);

    /**
     * 删除一个私钥
     *
     * @param keyToken
     */
    void deleteRSAKey(String keyToken);
}
