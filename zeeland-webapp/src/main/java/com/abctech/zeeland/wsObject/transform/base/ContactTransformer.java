package com.abctech.zeeland.wsObject.transform.base;

import com.abctech.zeeland.wsObject.initialize.ContactAttributeInitializer;
import no.zett.model.base.Contact;
import no.zett.model.base.ContactAttribute;
import no.zett.model.enums.ContactAttributeType;
import no.zett.service.facade.ZettContact;
import no.zett.service.facade.ZettContactAttribute;

import java.util.ArrayList;
import java.util.List;

public class ContactTransformer{

    public ZettContact transform(Contact contact){
        ZettContact zettContact = new ZettContact();
        if(contact.getContactId()!=null && contact.getContactId()>0){
            zettContact.setContactId(contact.getContactId());
        }
        zettContact.setDescription(contact.getDescription());
        zettContact.setEmail(contact.getEmail());
        zettContact.setName(contact.getName());
        if(contact.getAttributesRaw()!= null && contact.getAttributesRaw().size()>0){
            ContactAttributeListTransformer contactAttributeListTransformer = new ContactAttributeListTransformer();
            ZettContactAttribute[] arrayOfZettContactAttribute = contactAttributeListTransformer.transform(contact.getAttributesRaw());
                //zettContact.setAttributesRaw(arrayOfZettContactAttribute);
            if(arrayOfZettContactAttribute.length>0){
                for(ZettContactAttribute zettContactAttribute:arrayOfZettContactAttribute){
                    zettContact.getAttributesRaw().add(zettContactAttribute);
                }
            }
        }
        return zettContact;
    }

    public Contact transform(ZettContact zettContact){
        Contact contact = new Contact();
        contact.setContactId(zettContact.getContactId());
        if(zettContact.getDescription()!=null){
            contact.setDescription(zettContact.getDescription());
        }
        if(zettContact.getEmail()!=null){
            contact.setEmail(zettContact.getEmail());
        }
        if(zettContact.getName()!=null){
            contact.setName(zettContact.getName());
        }

        /*
        for this function add attribute in the object
        0 = title
        1 = phone
        2 = mobile
        */

        List<ContactAttribute> contactAttributeRawList = new ArrayList<ContactAttribute>();

        ContactAttributeInitializer contactAttributeInitializer = new ContactAttributeInitializer();
        ContactAttribute titleAttribute = contactAttributeInitializer.initialize(ContactAttributeType.TITLE.toInteger());
        ContactAttribute phoneAttribute = contactAttributeInitializer.initialize(ContactAttributeType.PHONE.toInteger());
        ContactAttribute mobileAttribute = contactAttributeInitializer.initialize(ContactAttributeType.MOBILE.toInteger());
        
        
        if(zettContact.getAttributesRaw()!=null && zettContact.getAttributesRaw()!=null){
            for(ZettContactAttribute zettContactAttribute:zettContact.getAttributesRaw()){
                if(zettContactAttribute.getType() == (ContactAttributeType.TITLE.toInteger())){
                    if(zettContactAttribute.getValue()!=null){
                        titleAttribute.setAttributeId(zettContactAttribute.getAttributeId());
                        titleAttribute.setValue(zettContactAttribute.getValue());
                    }
                }

                if(zettContactAttribute.getType() == (ContactAttributeType.PHONE.toInteger())){
                    if(zettContactAttribute.getValue()!=null){
                        phoneAttribute.setAttributeId(zettContactAttribute.getAttributeId());
                        phoneAttribute.setValue(zettContactAttribute.getValue());
                    }
                }

                if(zettContactAttribute.getType() == (ContactAttributeType.MOBILE.toInteger())){
                    if(zettContactAttribute.getValue()!=null){
                        mobileAttribute.setAttributeId(zettContactAttribute.getAttributeId());
                        mobileAttribute.setValue(zettContactAttribute.getValue());
                    }
                }
            }

        }

        contactAttributeRawList.add(titleAttribute);
        contactAttributeRawList.add(phoneAttribute);
        contactAttributeRawList.add(mobileAttribute);
        contact.setAttributesRaw(contactAttributeRawList);

        return contact;
    }

}
