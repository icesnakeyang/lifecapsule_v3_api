package cc.cdtime.lifecapsule_v3_api.middle.contact;

import cc.cdtime.lifecapsule_v3_api.meta.contact.entity.Contact;
import cc.cdtime.lifecapsule_v3_api.meta.contact.entity.ContactView;
import cc.cdtime.lifecapsule_v3_api.meta.contact.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class ContactMiddle implements IContactMiddle {
    private final IContactService iContactService;

    public ContactMiddle(IContactService iContactService) {
        this.iContactService = iContactService;
    }

    @Override
    public void createContact(Contact contact) throws Exception {
        iContactService.createContact(contact);
    }

    @Override
    public ArrayList<ContactView> listContact(Map qIn) throws Exception {
        ArrayList<ContactView> contactViews = iContactService.listContact(qIn);
        return contactViews;
    }

    @Override
    public ContactView getContact(Map qIn, Boolean returnNull, String userId) throws Exception {
        ContactView contactView = iContactService.getContact(qIn);
        if (contactView == null) {
            if (returnNull) {
                return null;
            }
            //联系人不存在
            throw new Exception("10030");
        }
        if (userId != null) {
            if (!contactView.getUserId().equals(userId)) {
                //没有查询该联系人权限
                throw new Exception("10031");
            }
        }
        return contactView;
    }

    @Override
    public void updateContact(Map qIn) throws Exception {
        iContactService.updateContact(qIn);
    }

    @Override
    public void deleteContact(String contactId) throws Exception {
        iContactService.deleteContact(contactId);
    }
}
