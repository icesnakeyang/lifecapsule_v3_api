package cc.cdtime.lifecapsule_v3_api.web.contact;

import java.util.Map;

public interface IWebContactBService {
    /**
     * 修改或者创建一个联系人
     *
     * @param in
     */
    void saveMyContact(Map in) throws Exception;

    /**
     * 读取我的联系人列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listMyContact(Map in) throws Exception;

    /**
     * 读取一个联系人详情
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getMyContact(Map in) throws Exception;

    /**
     * 删除一个联系人
     *
     * @param in
     * @throws Exception
     */
    void deleteMyContact(Map in) throws Exception;
}
