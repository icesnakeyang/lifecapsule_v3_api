package cc.cdtime.lifecapsule_v3_api.business.topic;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.author.entity.AuthorLog;
import cc.cdtime.lifecapsule_v3_api.meta.author.entity.AuthorLogView;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagView;
import cc.cdtime.lifecapsule_v3_api.meta.topic.entity.Topic;
import cc.cdtime.lifecapsule_v3_api.meta.topic.entity.TopicView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.author.IAuthorMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.tag.ITagMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.topic.ITopicMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TopicBService implements ITopicBService {
    private final IUserMiddle iUserMiddle;
    private final ITopicMiddle iTopicMiddle;
    private final INoteMiddle iNoteMiddle;
    private final IAuthorMiddle iAuthorMiddle;
    private final ITagMiddle iTagMiddle;

    public TopicBService(IUserMiddle iUserMiddle,
                         ITopicMiddle iTopicMiddle,
                         INoteMiddle iNoteMiddle,
                         IAuthorMiddle iAuthorMiddle,
                         ITagMiddle iTagMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iTopicMiddle = iTopicMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iAuthorMiddle = iAuthorMiddle;
        this.iTagMiddle = iTagMiddle;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publishNoteToTopic(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();
        String title = in.get("title").toString();
        String content = in.get("content").toString();
        String authorName = (String) in.get("authorName");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        if (authorName != null) {
            /**
             * 查询笔名是否存在
             */
            qIn = new HashMap();
            qIn.put("authorName", authorName);
            qIn.put("userId", userView.getUserId());
            AuthorLogView authorLogView = iAuthorMiddle.getAuthorLog(qIn);
            if (authorLogView == null) {
                /**
                 * 不存在，创建一个
                 */

                //首先把当前的默认笔记改成active
                changeDefaultLogToActive(userView.getUserId());

                //添加新的笔名，并设置为默认
                AuthorLog authorLog = new AuthorLog();
                authorLog.setAuthorLogId(GogoTools.UUID32());
                authorLog.setUserId(userView.getUserId());
                authorLog.setAuthorName(authorName);
                authorLog.setCreateTime(new Date());
                authorLog.setStatus(ESTags.DEFAULT.toString());
                iAuthorMiddle.createAuthorLog(authorLog);
            } else {
                /**
                 * 笔名已存在，检查是否默认，若默认则不做处理
                 * 如果不是默认，则把当前默认改成active，把该笔名改成默认
                 */
                if (!authorLogView.getStatus().equals(ESTags.DEFAULT.toString())) {
                    //查找默认的笔名，改为active
                    changeDefaultLogToActive(userView.getUserId());

                    //把authorName改成默认
                    qIn = new HashMap();
                    qIn.put("status", ESTags.DEFAULT);
                    qIn.put("authorLogId", authorLogView.getAuthorLogId());
                    iAuthorMiddle.updateAuthorLog(qIn);
                }
            }
        }

        Topic topic = new Topic();
        topic.setTopicId(GogoTools.UUID32());
        topic.setCreateTime(new Date());
        topic.setStatus(ESTags.ACTIVE.toString());
        topic.setTitle(title);
        topic.setUserId(userView.getUserId());
        topic.setContent(content);
        topic.setNoteId(noteId);
        topic.setAuthorName(authorName);
        iTopicMiddle.createTopic(topic);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map listTopicPublic(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer size = (Integer) in.get("size");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn.put("offset", 0);
        qIn.put("size", size);
        qIn.put("status", ESTags.ACTIVE);
        ArrayList<TopicView> topicViews = iTopicMiddle.listTopic(qIn);

        Map out = new HashMap();
        out.put("topicList", topicViews);

        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map getTopicDetail(Map in) throws Exception {
        String token = in.get("token").toString();
        String topicId = in.get("topicId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, false);

        TopicView topicView = iTopicMiddle.getTopic(topicId, false);

        Map out = new HashMap();
        out.put("topic", topicView);

        /**
         * 读取评论列表
         */
        qIn = new HashMap();
        qIn.put("pid", topicView.getTopicId());
        ArrayList<TopicView> topicViews = iTopicMiddle.listTopic(qIn);
        out.put("commentList", topicViews);

        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void replyComment(Map in) throws Exception {
        String token = in.get("token").toString();
        String topicId = in.get("topicId").toString();
        String title = in.get("title").toString();
//        String content = in.get("content").toString();
        String comment = in.get("comment").toString();
        String authorName = in.get("authorName").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        TopicView topicView = iTopicMiddle.getTopic(topicId, false);

        /**
         * 回复话题需不需要创建note呢
         */

        NoteInfo noteInfo = new NoteInfo();
        noteInfo.setContent(comment);
        noteInfo.setNoteId(GogoTools.UUID32());
        noteInfo.setTitle(title);
        noteInfo.setCreateTime(new Date());
        noteInfo.setStatus(ESTags.ACTIVE.toString());
        noteInfo.setEncrypt(0);
        noteInfo.setUserId(userView.getUserId());
        iNoteMiddle.createNoteInfo(noteInfo);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        qIn.put("authorName", authorName);
        AuthorLogView authorLogView = iAuthorMiddle.getAuthorLog(qIn);
        if (authorLogView == null) {
            changeDefaultLogToActive(userView.getUserId());
            AuthorLog authorLog = new AuthorLog();
            authorLog.setAuthorName(authorName);
            authorLog.setAuthorLogId(GogoTools.UUID32());
            authorLog.setStatus(ESTags.DEFAULT.toString());
            authorLog.setUserId(userView.getUserId());
            authorLog.setCreateTime(new Date());
            iAuthorMiddle.createAuthorLog(authorLog);
        } else {
            if (!authorLogView.getStatus().equals(ESTags.DEFAULT.toString())) {
                changeDefaultLogToActive(userView.getUserId());
                qIn = new HashMap();
                qIn.put("authorLogId", authorLogView.getAuthorLogId());
                qIn.put("status", ESTags.DEFAULT);
                iAuthorMiddle.updateAuthorLog(qIn);
            }
        }

        Topic topic = new Topic();
        topic.setTopicId(GogoTools.UUID32());
        topic.setPid(topicId);
        topic.setContent(comment);
        topic.setUserId(userView.getUserId());
        topic.setTitle(title);
        topic.setStatus(ESTags.ACTIVE.toString());
        topic.setAuthorName(authorName);
        topic.setCreateTime(new Date());
        topic.setNoteId(noteInfo.getNoteId());
        iTopicMiddle.createTopic(topic);

        /**
         * 修改父话题的评论次数
         */
        qIn = new HashMap();
        qIn.put("topicId", topicView.getTopicId());
        int comments=1;
        if(topicView.getComments()!=null){
            comments=topicView.getComments()+1;
        }
        qIn.put("comments", comments);
        iTopicMiddle.updateTopic(qIn);
    }

    @Override
    public Map listHotTopicTags(Map in) throws Exception {
        Map qIn = new HashMap();
        qIn.put("size", 10);
        ArrayList<TagView> tagViews = iTagMiddle.listBaseTag(qIn);

        Map out = new HashMap();
        out.put("tagList", tagViews);

        return out;
    }

    private void changeDefaultLogToActive(String userId) throws Exception {
        Map qIn = new HashMap();
        qIn.put("userId", userId);
        qIn.put("status", ESTags.DEFAULT);
        AuthorLogView authorLogView = iAuthorMiddle.getAuthorLog(qIn);
        if (authorLogView != null) {
            qIn = new HashMap();
            qIn.put("status", ESTags.ACTIVE);
            qIn.put("authorLogId", authorLogView.getAuthorLogId());
            iAuthorMiddle.updateAuthorLog(qIn);
        }
    }
}
