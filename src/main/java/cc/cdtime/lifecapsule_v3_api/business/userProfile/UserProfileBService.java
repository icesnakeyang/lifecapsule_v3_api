package cc.cdtime.lifecapsule_v3_api.business.userProfile;

import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserProfileBService implements IUserProfileBService {
    private final IUserMiddle iUserMiddle;

    public UserProfileBService(IUserMiddle iUserMiddle) {
        this.iUserMiddle = iUserMiddle;
    }

    @Override
    public void saveNickname(Map in) throws Exception {
        String token = in.get("token").toString();
        String nickname = in.get("nickname").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        qIn.put("nickname", nickname);
        iUserMiddle.updateUserBase(qIn);
    }
}
