package cc.cdtime.lifecapsule_v3_api.meta.contact.dao;

import cc.cdtime.lifecapsule_v3_api.meta.contact.entity.Contact;
import cc.cdtime.lifecapsule_v3_api.meta.contact.entity.ContactView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface ContactDao {
    /**
     * 创建一个联系人
     *
     * @param contact
     */
    void createContact(Contact contact);

    void createContactRemark(Contact contact);

    /**
     * 读取联系人列表
     *
     * @param qIn userId
     *            offset
     *            size
     * @return
     */
    ArrayList<ContactView> listContact(Map qIn);

    /**
     * 读取一个联系人详情
     *
     * @param qIn contactId
     *            phone
     *            email
     * @return
     */
    ContactView getContact(Map qIn);

    ContactView getContactRemark(Map qIn);

    /**
     * 修改一个联系人信息
     *
     * @param qIn contactName
     *            phone
     *            remark
     *            email
     *            contactId
     */
    void updateContact(Map qIn);

    void updateContactRemark(Map qIn);

    /**
     * 物理删除一个联系人
     *
     * @param contactId
     */
    void deleteContact(String contactId);

    void deleteContactRemark(String contactId);
}
