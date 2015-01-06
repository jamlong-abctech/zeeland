package com.abctech.zeeland.wsObject.transform.base;

import no.zett.model.base.Contact;
import no.zett.service.facade.ZettContact;

import java.util.ArrayList;
import java.util.List;

public class ContactListTransformer{

    //private final static Logger log = LoggerFactory.getLogger(ContactListTransformer.class);

    public ZettContact[] transform(List<Contact> contactList){
        ZettContact[] arrayOfZettContact = new ZettContact[contactList.size()];
        ContactTransformer contactTransformer = new ContactTransformer();
        if(contactList.size()>0){
            for(int i = 0;i<contactList.size();i++){
                ZettContact zettContact = contactTransformer.transform(contactList.get(i));
                arrayOfZettContact[i] = zettContact;
            }
        }
        return arrayOfZettContact;
    }
    
    public List<Contact> transform(ZettContact[] arrayOfZettContact){
        List<Contact> contactList = new ArrayList<Contact>();
        ContactTransformer contactTransformer = new ContactTransformer();
        for(int i = 0;i<arrayOfZettContact.length;i++){
            Contact contact = contactTransformer.transform(arrayOfZettContact[i]);
            contactList.add(contact);
        }

        return contactList;
    }
}
