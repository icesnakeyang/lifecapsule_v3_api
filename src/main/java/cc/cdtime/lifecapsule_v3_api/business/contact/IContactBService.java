package cc.cdtime.lifecapsule_v3_api.business.contact;


import java.util.Map;

public interface IContactBService {
    /**
     * 创建一个联系人
     *
     * @param in
     */
    void saveContact(Map in) throws Exception;

    /**
     * 读取联系人列表
     *
     * @param in
     * @return
     */
    Map listContact(Map in) throws Exception;

    /**
     * 读取一个联系人详情
     *
     * @param in
     * @return
     */
    Map getContact(Map in) throws Exception;

    /**
     * 物理删除一个联系人
     *
     * @param in
     * @throws Exception
     */
    void deleteContact(Map in) throws Exception;
}
