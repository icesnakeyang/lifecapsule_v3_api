package cc.cdtime.lifecapsule_v3_api.business.recipient;

import java.util.Map;

public interface IRecipientBService {
    Map listRecipient(Map in) throws Exception;

    void createNoteRecipient(Map in) throws Exception;

    void deleteNoteRecipient(Map in) throws Exception;

    void saveRecipient(Map in) throws Exception;

    Map getRecipient(Map in) throws Exception;

    void deleteRecipient(Map in) throws Exception;

    /**
     * 把一个联系人添加到接收人里
     *
     * @param in
     * @throws Exception
     */
    void addContactToRecipient(Map in) throws Exception;


    void addEmailToRecipient(Map in) throws Exception;
}
