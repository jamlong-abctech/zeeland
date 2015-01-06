package com.abctech.zeeland.wsObject.update;

import com.abctech.zeeland.wsObject.transform.base.ContactTransformer;
import no.zett.model.base.Contact;
import no.zett.model.base.ContactAttribute;
import no.zett.model.enums.ContactAttributeType;
import no.zett.service.facade.ZettContact;
import no.zett.service.facade.ZettContactAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ContactListUpdate {

    private final static Logger log = LoggerFactory.getLogger(ContactListUpdate.class);

    public ZettContact[] update(ZettContact[] arrayOfZettContactfParam,List<Contact> contactList,Contact extendedContact) {

        List<ZettContact> zettContactList = retrieveZettContact(arrayOfZettContactfParam);

        if(zettContactList.size() > 0){
            for(ZettContact zettContact:zettContactList){
                for(Contact contact:contactList){
                    if(contact.getContactId()!=null && contact.getContactId()>0){
                        if(contact.getContactId().equals(zettContact.getContactId())){
                            zettContact.setName(contact.getName());
                            zettContact.setEmail(contact.getEmail());

                            if(zettContact.getAttributesRaw()!=null){
                                List<ZettContactAttribute> zettContactAttributeList = zettContact.getAttributesRaw();
                             
                                if(zettContactAttributeList == null){
                                    zettContactAttributeList = new ArrayList<ZettContactAttribute>();
                                }
                                for(ContactAttribute contactAttribute:contact.getAttributesRaw()){
                                    log.debug("ID : " + contactAttribute.getAttributeId());
                                    log.debug("Phone : " + contactAttribute.getValue());
                                    if(contactAttribute.getAttributeId() == null){
                                        if(contactAttribute.getTypeValue().equals(ContactAttributeType.PHONE.toInteger())){
                                            ZettContactAttribute zettContactAttribute = new ZettContactAttribute();
                                            zettContactAttribute.setType(ContactAttributeType.PHONE.toInteger());
                                            zettContactAttribute.setSection("DEFAULT");
                                            zettContactAttribute.setValue(contactAttribute.getValue());
                                            zettContactAttributeList.add(zettContactAttribute);
                                        }

                                        if(contactAttribute.getTypeValue().equals(ContactAttributeType.TITLE.toInteger())){
                                            ZettContactAttribute zettContactAttribute = new ZettContactAttribute();
                                            zettContactAttribute.setType(ContactAttributeType.TITLE.toInteger());
                                            zettContactAttribute.setSection("DEFAULT");
                                            zettContactAttribute.setValue(contactAttribute.getValue());
                                            zettContactAttributeList.add(zettContactAttribute);
                                        }

                                        if(contactAttribute.getTypeValue().equals(ContactAttributeType.MOBILE.toInteger())){
                                            ZettContactAttribute zettContactAttribute = new ZettContactAttribute();
                                            zettContactAttribute.setType(ContactAttributeType.MOBILE.toInteger());
                                            zettContactAttribute.setSection("DEFAULT");
                                            zettContactAttribute.setValue(contactAttribute.getValue());
                                            zettContactAttributeList.add(zettContactAttribute);
                                        }
                                    }else{

                                        for(ZettContactAttribute zettContactAttribute:zettContactAttributeList){
                                            if(contactAttribute.getAttributeId().equals(zettContactAttribute.getAttributeId())){
                                                zettContactAttribute.setValue(contactAttribute.getValue());
                                            }
                                        }
                                    }
                                }

                                ZettContactAttribute[] arrayOfZettContactAttribute = new ZettContactAttribute[zettContactAttributeList.size()];
                                for(ZettContactAttribute zettContactAttribute:arrayOfZettContactAttribute){
                                    zettContact.getAttributesRaw().add(zettContactAttribute);
                                }
                            }

                        }
                    }
                }
            }

            //in case array of zett contact is not null - add extended to the all contact
            if(extendedContact != null){
                if(extendedContact.getName()!=null && extendedContact.getEmail()!=null){
                    if(!extendedContact.getName().isEmpty() && !extendedContact.getEmail().isEmpty()){
                        ContactTransformer contactTransformer = new ContactTransformer();
                        ZettContact extenedZettContact = contactTransformer.transform(extendedContact);
                        zettContactList.add(extenedZettContact);
                    }
                }
            }

        }else{
            //in case array of zett contact is null - create a new one
            if(extendedContact != null){
                if(extendedContact.getName()!=null && extendedContact.getEmail()!=null){
                    if(!extendedContact.getName().isEmpty() && !extendedContact.getEmail().isEmpty()){
                        zettContactList = new ArrayList<ZettContact>();
                        ContactTransformer contactTransformer = new ContactTransformer();
                        ZettContact zettContact = contactTransformer.transform(extendedContact);
                        zettContactList.add(zettContact);
                    }
                }
            }
        }

        ZettContact[] arrayOfZettContact = new ZettContact[zettContactList.size()];
        return zettContactList.toArray(arrayOfZettContact);
    }

    private List<ZettContact> retrieveZettContact(ZettContact[] zettContacts){
        List<ZettContact> zettContactList = new ArrayList<ZettContact>();
        for(ZettContact zettContact : zettContacts){
            zettContactList.add(zettContact);
        }
        return  zettContactList;
    }

}
