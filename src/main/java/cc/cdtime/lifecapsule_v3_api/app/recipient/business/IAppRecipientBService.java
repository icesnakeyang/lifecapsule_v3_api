package cc.cdtime.lifecapsule_v3_api.app.recipient.business;

import java.util.Map;

public interface IAppRecipientBService {
    /**
     * 用户查看接收人列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listRecipient(Map in) throws Exception;

    /**
     * App创建或者保存一个接收人
     *
     * @param in
     * @throws Exception
     */
    void saveRecipient(Map in) throws Exception;

    Map getRecipient(Map in) throws Exception;

    /**
     * App物理删除一个接收人信息
     * @param in
     * @throws Exception
     */
    void deleteRecipient(Map in) throws Exception;
}
