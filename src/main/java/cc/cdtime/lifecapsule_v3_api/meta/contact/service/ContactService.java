package cc.cdtime.lifecapsule_v3_api.meta.contact.service;

import cc.cdtime.lifecapsule_v3_api.meta.contact.dao.ContactDao;
import cc.cdtime.lifecapsule_v3_api.meta.contact.entity.Contact;
import cc.cdtime.lifecapsule_v3_api.meta.contact.entity.ContactView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class ContactService implements IContactService {
    private final ContactDao contactDao;

    public ContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @Override
    public void createContact(Contact contact) throws Exception {
        contactDao.createContact(contact);
        if (contact.getRemark() != null) {
            contactDao.createContactRemark(contact);
        }
    }

    @Override
    public ArrayList<ContactView> listContact(Map qIn) throws Exception {
        ArrayList<ContactView> contactViews = contactDao.listContact(qIn);
        return contactViews;
    }

    @Override
    public ContactView getContact(Map qIn) throws Exception {
        ContactView contactView = contactDao.getContact(qIn);
        ContactView remarkView = contactDao.getContactRemark(qIn);
        if (remarkView != null) {
            contactView.setRemark(remarkView.getRemark());
        }
        return contactView;
    }

    @Override
    public void updateContact(Map qIn) throws Exception {
        String contactId = qIn.get("contactId").toString();
        String contactName = (String) qIn.get("contactName");
        String phone = (String) qIn.get("phone");
        String email = (String) qIn.get("email");
        int cc = 0;
        if (contactName != null) {
            cc++;
        }
        if (phone != null) {
            cc++;
        }
        if (email != null) {
            cc++;
        }
        if (cc > 0) {
            contactDao.updateContact(qIn);
        }

        String remark = (String) qIn.get("remark");
        if (remark != null) {
            ContactView contactView = contactDao.getContactRemark(qIn);
            if (contactView == null) {
                /**
                 * content_detail表里还没有创建该条数据，新增一个
                 */
                Contact contact = new Contact();
                contact.setContactId(contactId);
                contact.setRemark(remark);
                contactDao.createContactRemark(contact);
            }
            contactDao.updateContactRemark(qIn);
        }
    }

    @Override
    public void deleteContact(String contactId) throws Exception {
        contactDao.deleteContact(contactId);
        contactDao.deleteContactRemark((contactId));
    }
}
