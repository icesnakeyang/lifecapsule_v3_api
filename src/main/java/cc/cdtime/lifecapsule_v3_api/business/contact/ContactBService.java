package cc.cdtime.lifecapsule_v3_api.business.contact;

import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.contact.entity.Contact;
import cc.cdtime.lifecapsule_v3_api.meta.contact.entity.ContactView;
import cc.cdtime.lifecapsule_v3_api.meta.user.entity.UserView;
import cc.cdtime.lifecapsule_v3_api.middle.contact.IContactMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ContactBService implements IContactBService {
    private final IContactMiddle iContactMiddle;
    private final IUserMiddle iUserMiddle;

    public ContactBService(IContactMiddle iContactMiddle,
                           IUserMiddle iUserMiddle) {
        this.iContactMiddle = iContactMiddle;
        this.iUserMiddle = iUserMiddle;
    }

    @Override
    public void saveContact(Map in) throws Exception {
        String token = in.get("token").toString();
        String contactName = in.get("contactName").toString();
        String phone = in.get("phone").toString();
        String email = in.get("email").toString();
        String contactId = (String) in.get("contactId");
        String remark = (String) in.get("remark");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        if (contactId == null || contactId.equals("")) {
            /**
             * 新增联系人
             */
            Contact contact = new Contact();
            contact.setContactId(GogoTools.UUID32());
            contact.setContactName(contactName);
            contact.setEmail(email);
            contact.setPhone(phone);
            contact.setUserId(userView.getUserId());
            contact.setRemark(remark);
            iContactMiddle.createContact(contact);
        } else {
            /**
             * 修改联系人
             */
            qIn = new HashMap();
            qIn.put("contactId", contactId);
            ContactView contactView = iContactMiddle.getContact(qIn, false, userView.getUserId());
            int cc = 0;
            if (!contactView.getContactName().equals(contactName)) {
                qIn.put("contactName", contactName);
                cc++;
            }
            if (!contactView.getPhone().equals(phone)) {
                qIn.put("phone", phone);
                cc++;
            }
            if (!contactView.getEmail().equals(email)) {
                qIn.put("email", email);
                cc++;
            }
            if (!contactView.getRemark().equals(remark)) {
                qIn.put("remark", remark);
                cc++;
            }
            if (cc > 0) {
                qIn.put("contactId", contactId);
                iContactMiddle.updateContact(qIn);
            }
        }
    }

    @Override
    public Map listContact(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        ArrayList<ContactView> contactViews = iContactMiddle.listContact(qIn);
        Integer totalContact = iContactMiddle.totalContact(qIn);

        Map out = new HashMap();
        out.put("contactList", contactViews);
        out.put("totalContact", totalContact);

        return out;
    }

    @Override
    public Map getContact(Map in) throws Exception {
        String token = in.get("token").toString();
        String contactId = (String) in.get("contactId");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("contactId", contactId);
        ContactView contactView = iContactMiddle.getContact(qIn, false, userView.getUserId());

        Map out = new HashMap();
        out.put("contact", contactView);

        return out;
    }

    @Override
    public void deleteContact(Map in) throws Exception {
        String token = in.get("token").toString();
        String contactId = in.get("contactId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("contactId", contactId);
        ContactView contactView = iContactMiddle.getContact(qIn, false, userView.getUserId());

        iContactMiddle.deleteContact(contactId);
    }
}
