package cc.cdtime.lifecapsule_v3_api.business.history;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.security.ISecurityMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class HistoryBService implements IHistoryBService {
    private final IUserMiddle iUserMiddle;
    private final INoteMiddle iNoteMiddle;
    private final ISecurityMiddle iSecurityMiddle;

    public HistoryBService(IUserMiddle iUserMiddle,
                           INoteMiddle iNoteMiddle,
                           ISecurityMiddle iSecurityMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iSecurityMiddle = iSecurityMiddle;
    }

    @Override
    public Map loadHistoryHome(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);

        /**
         * 读取过去10年的今天的笔记
         */
        Calendar c1 = Calendar.getInstance();
        c1.setTime(new Date());
        int year = c1.get(Calendar.YEAR);
        int month = c1.get(Calendar.MONTH);
        int day = c1.get(Calendar.DAY_OF_MONTH);
        ArrayList<NoteView> noteViews = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            year = year - 1;
            c1.set(year, month, day - 1);
            Calendar c2 = Calendar.getInstance();
            c2.set(year, month, day + 1);
            qIn.put("createTimeStart", c1.getTime());
            qIn.put("createTimeEnd", c2.getTime());
            qIn.put("userId", userView.getUserId());
            ArrayList<NoteView> list = iNoteMiddle.listHistoryNote(qIn);
            noteViews.addAll(list);
        }

        /**
         * 随机捞历史笔记
         */
        qIn = new HashMap();
        qIn.put("size", 10);
        qIn.put("userId", userView.getUserId());
        ArrayList<NoteView> noteViews1 = iNoteMiddle.listHistoryRandom(qIn);

        noteViews.addAll(noteViews1);

        Map out = new HashMap();
        out.put("noteList", noteViews);

        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void replyMyNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String pid = in.get("pid").toString();
        String content = in.get("content").toString();
        String title = in.get("title").toString();
        String encryptKey = in.get("encryptKey").toString();
        String keyToken = in.get("keyToken").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String strAESKey = null;
        String privateKey = iSecurityMiddle.getRSAKey(keyToken);
        strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);
        iSecurityMiddle.deleteRSAKey(keyToken);

        NoteView pNote = iNoteMiddle.getNoteTiny(pid, false, userView.getUserId());

        NoteInfo noteInfo = new NoteInfo();
        noteInfo.setNoteId(GogoTools.UUID32());
        noteInfo.setUserId(userView.getUserId());
        noteInfo.setContent(content);
        noteInfo.setUserEncodeKey(strAESKey);
        noteInfo.setEncrypt(1);
        noteInfo.setTitle(title);
        noteInfo.setCreateTime(new Date());
        noteInfo.setStatus(ESTags.ACTIVE.toString());
        noteInfo.setPid(pid);
        iNoteMiddle.createNoteInfo(noteInfo);
    }

    @Override
    public Map searchHistoryNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String searchKey = in.get("searchKey").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);
        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        qIn.put("titleKey", searchKey);
        ArrayList<NoteView> noteViews = iNoteMiddle.listHistoryNote(qIn);

        Map out = new HashMap();
        out.put("noteList", noteViews);
        return out;
    }
}
