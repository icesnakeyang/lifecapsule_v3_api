package cc.cdtime.lifecapsule_v3_api.meta.recipient.dao;

import cc.cdtime.lifecapsule_v3_api.meta.recipient.entity.NoteRecipient;
import cc.cdtime.lifecapsule_v3_api.meta.recipient.entity.RecipientView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface RecipientDao {
    /**
     * 创建一个笔记的接收人
     *
     * @param noteRecipient
     */
    void createNoteRecipient(NoteRecipient noteRecipient);

    /**
     * 查询接收人列表
     *
     * @param qIn noteId
     * @return
     */
    ArrayList<RecipientView> listNoteRecipient(Map qIn);

    /**
     * 查询一个接收人信息
     *
     * @param recipientId
     * @return
     */
    RecipientView getRecipient(String recipientId);

    RecipientView getRecipientTiny(String recipientId);

    /**
     * 删除一个接收人
     *
     * @param qIn recipientId
     *            noteId
     */
    void deleteNoteRecipient(Map qIn);

    /**
     * 修改接收人
     *
     * @param qIn name
     *            phone
     *            email
     *            remark
     *            status
     *            recipientId
     */
    void updateNoteRecipient(Map qIn);
}
