package cc.cdtime.lifecapsule_v3_api.meta.user.dao;

import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKey;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKeyView;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserEncodeKeyDao {
    void createUserEncodeKey(UserEncodeKey userEncodeKey);

    /**
     * 读取一个用户秘钥记录
     *
     * @param qIn encodeKeyId
     *            indexId
     * @return
     */
    UserEncodeKeyView getUserEncodeKey(Map qIn);

    /**
     * @param qIn encodeKey
     *            indexId
     */
    void updateUserEncodeKey(Map qIn);

    void deleteUserEncodeKey(String indexId);
}
