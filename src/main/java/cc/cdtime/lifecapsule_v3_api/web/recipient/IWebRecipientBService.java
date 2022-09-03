package cc.cdtime.lifecapsule_v3_api.web.recipient;

import java.util.Map;

public interface IWebRecipientBService {
    /**
     * 把一个联系人添加到接收人里
     *
     * @param in
     * @throws Exception
     */
    void addContactToRecipient(Map in) throws Exception;

    /**
     * 读取我的一篇笔记的接收人列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listMyNoteRecipient(Map in) throws Exception;

    Map getRecipient(Map in) throws Exception;

    void deleteRecipient(Map in) throws Exception;

    void saveRecipient(Map in) throws Exception;

    /**
     * 添加一个email为接收人
     *
     * @param in
     * @throws Exception
     */
    void addEmailToRecipient(Map in) throws Exception;
}
