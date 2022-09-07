package cc.cdtime.lifecapsule_v3_api.meta.recipient.sevice;


import cc.cdtime.lifecapsule_v3_api.meta.recipient.entity.NoteRecipient;
import cc.cdtime.lifecapsule_v3_api.meta.recipient.entity.RecipientView;

import java.util.ArrayList;
import java.util.Map;

public interface IRecipientService {
    /**
     * 创建一个笔记的接收人
     *
     * @param noteRecipient
     */
    void createNoteRecipient(NoteRecipient noteRecipient) throws Exception;

    /**
     * 查询接收人列表
     *
     * @param qIn noteId
     * @return
     */
    ArrayList<RecipientView> listNoteRecipient(Map qIn) throws Exception;

    /**
     * 查询一个接收人信息
     *
     * @param recipientId
     * @return
     */
    RecipientView getRecipient(String recipientId) throws Exception;

    RecipientView getRecipientTiny(String recipientId) throws Exception;

    /**
     * 删除一个接收人
     *
     * @param qIn recipientId
     *            noteId
     */
    void deleteNoteRecipient(Map qIn) throws Exception;

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
    void updateNoteRecipient(Map qIn) throws Exception;
}
