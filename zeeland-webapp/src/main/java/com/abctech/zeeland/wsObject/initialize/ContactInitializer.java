package com.abctech.zeeland.wsObject.initialize;

import no.zett.model.base.Contact;
import no.zett.model.base.ContactAttribute;

import java.util.ArrayList;
import java.util.List;

public class ContactInitializer {

    public Contact initialize(){
        Contact contact = new Contact();
        List<ContactAttribute> contactAttributeList = new ArrayList<ContactAttribute>();
        /*
        PHONE(1),
                MOBILE(2),
                FAX(3),
                PAGER(4),
                EMAIL(5),
                IM(6),
                COMPANY(7),
                TITLE(8),
                OTHER(9),
                HOMEPAGE(10),
                ADDRESS(11),
                POSTCODE(12),
                POSTLOCATION(13),
                PHOTO(14),
                COUNTRY(15),
                CHATJS(16),;*/

        ContactAttributeInitializer contactAttributeInitializer = new ContactAttributeInitializer();
        ContactAttribute titleAttribute = contactAttributeInitializer.initialize(8);
        ContactAttribute phoneAttribute = contactAttributeInitializer.initialize(1);
        ContactAttribute mobileAttribute = contactAttributeInitializer.initialize(2);
        contactAttributeList.add(titleAttribute);
        contactAttributeList.add(phoneAttribute);
        contactAttributeList.add(mobileAttribute);
        contact.setAttributesRaw(contactAttributeList);
        return contact;
    }
}
