package cc.cdtime.lifecapsule_v3_api.meta.user.service;

import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKey;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKeyView;

import java.util.Map;

public interface IUserEncodeKeyService {
    void createUserEncodeKey(UserEncodeKey userEncodeKey) throws Exception;

    /**
     * 读取一个用户秘钥记录
     *
     * @param indexId
     * @return
     * @throws Exception
     */
    UserEncodeKeyView getUserEncodeKey(String indexId) throws Exception;

    /**
     * @param qIn encodeKey
     *            indexId
     */
    void updateUserEncodeKey(Map qIn) throws Exception;

    void deleteUserEncodeKey(String indexId) throws Exception;
}
