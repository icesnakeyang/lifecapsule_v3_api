package cc.cdtime.lifecapsule_v3_api.meta.admin.dao;

import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminStatisticView;
import cc.cdtime.lifecapsule_v3_api.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule_v3_api.meta.userAct.entity.UserActView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface AdminStatisticDao {
    /**
     * 统计用户登录次数
     *
     * @param qIn startTime
     *            endTime
     *            size
     *            offset
     * @return
     */
    ArrayList<AdminStatisticView> totalUserLogin(Map qIn);

    /**
     * 管理员查询笔记列表
     *
     * @param qIn size
     * @return
     */
    ArrayList<NoteView> listNoteInfo(Map qIn);

    /**
     * 统计用户行为
     *
     * @param qIn startTime
     *            endTime
     * @return
     */
    Integer totalUserAct(Map qIn);

    ArrayList<UserActView> listUserActTotal(Map qIn);


}
