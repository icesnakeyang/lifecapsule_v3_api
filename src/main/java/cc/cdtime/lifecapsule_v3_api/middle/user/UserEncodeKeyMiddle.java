package cc.cdtime.lifecapsule_v3_api.middle.user;

import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKey;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserEncodeKeyView;
import cc.cdtime.lifecapsule_v3_api.meta.user.service.IUserEncodeKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserEncodeKeyMiddle implements IUserEncodeKeyMiddle {
    private final IUserEncodeKeyService iUserEncodeKeyService;

    public UserEncodeKeyMiddle(IUserEncodeKeyService iUserEncodeKeyService) {
        this.iUserEncodeKeyService = iUserEncodeKeyService;
    }

    @Override
    public void createUserEncodeKey(UserEncodeKey userEncodeKey) throws Exception {
        iUserEncodeKeyService.createUserEncodeKey(userEncodeKey);
    }

    @Override
    public UserEncodeKeyView getUserEncodeKey(Map qIn) throws Exception {
        UserEncodeKeyView userEncodeKeyView = iUserEncodeKeyService.getUserEncodeKey(qIn);
        return userEncodeKeyView;
    }

    @Override
    public void updateUserEncodeKey(Map qIn) throws Exception {
        iUserEncodeKeyService.updateUserEncodeKey(qIn);
    }
}
