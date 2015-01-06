package com.abctech.zeeland.wsObject.update;


import no.zett.service.facade.ZettCompanyObject;
import no.zett.service.facade.ZettContact;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DeleteCompanyContactListTest {
    private final static org.slf4j.Logger log = LoggerFactory.getLogger(DeleteCompanyContactListTest.class);


    public ZettCompanyObject deleteCompanyContact(ZettCompanyObject zettCompanyObject, int contactId) {

           log.debug("ContactID : " + contactId);
           List<ZettContact> contactList = zettCompanyObject.getContacts();
           log.debug("Size before delete " + contactList.size());
           List<ZettContact> newcontact = new ArrayList<ZettContact>();
           ZettContact deleteContact = null;

           for (ZettContact contact : contactList) {

               if (contactList.size() > 1) {

                       if (contact.getContactId() == contactId) {
                           deleteContact = contact;
                           log.debug("#####  Found ######");

                       }

               } else {

                   log.debug("The company needs at least one contact");

               }


           }

           if (deleteContact != null) {
               contactList.remove(deleteContact);

           }

           List<ZettContact> arrayOfZettContact1 = new ArrayList<ZettContact>();
           for (ZettContact zettContact : contactList) {
               arrayOfZettContact1.add(zettContact);
           }

           for(ZettContact zettContact:arrayOfZettContact1){
               zettCompanyObject.getContacts().add(zettContact);
           }

           return zettCompanyObject;


       }


    @Test
    public void deleteZettContactTest() throws  Exception{
        ZettCompanyObject zettCompanyObject = new ZettCompanyObject();

        ZettContact zettContact = new ZettContact();
        ZettContact zettContact1 = new ZettContact();
        List<ZettContact>zettContactList = new ArrayList<ZettContact>();

        zettCompanyObject.setObjectId(0001);
        zettContact.setContactId(34666639);
        zettContact.setName("Test1");
        zettContact.setEmail("test1@test.com");
        zettContactList.add(zettContact);

        zettContact1.setContactId(34666640);
        zettContact1.setName("Test2");
        zettContact1.setEmail("test2@test.com");
        zettContactList.add(zettContact);

        List<ZettContact> arrayOfZettContact = new ArrayList<ZettContact>();
        for(ZettContact addContact : zettContactList){
            arrayOfZettContact.add(addContact);
        }

        /* wait for fix the autowire proble in test
        zettCompanyObject.setContacts(objectFactory.createZettCompanyObjectContacts(arrayOfZettContact));
        zettCompanyObject = new CompanyUpdate().deleteContact(zettCompanyObject.getObjectId() , 34666639)      ;

        Assert.assertEquals(1, zettCompanyObject.getContacts().getValue().getZettContact().size() );
        zettCompanyObject = new CompanyUpdate().deleteContact(zettCompanyObject.getObjectId(), 34666640);
        Assert.assertEquals(1, zettCompanyObject.getContacts().getValue().getZettContact().size());
        */


    }


}