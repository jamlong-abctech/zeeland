package com.abctech.zeeland.wsObject.update;

import com.abctech.zeeland.form.data.ExtendedCompanyObject;
import com.abctech.zeeland.form.util.XMLGregorianCalendarGenerator;
import com.abctech.zeeland.wsAuthentication.WebserviceAuthentication;
import com.abctech.zeeland.wsObject.transform.base.ObjectAttributeTransformer;
import com.abctech.zeeland.wsObject.transform.companyobject.ExtendedCompanyObjectTransformer;
import no.zett.service.facade.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class CompanyUpdate {

    private final static Logger log = LoggerFactory.getLogger(CompanyUpdate.class);

    @Resource(name = "webserviceAuthentication")
    private WebserviceAuthentication webserviceAuthentication;

    @Autowired
    private CompanyService companyServicePortType;

    public ZettCompanyObject add(ExtendedCompanyObject extendedCompanyObject) throws ServiceException_Exception {

        log.debug("add new company");

        extendedCompanyObject.setObjectId(null);
        ExtendedCompanyObjectTransformer extendedCompanyObjectTransformer = new ExtendedCompanyObjectTransformer();
        ZettCompanyObject zettCompanyObject = extendedCompanyObjectTransformer.transform(extendedCompanyObject);

        //add new created date
        XMLGregorianCalendarGenerator xmlGregorianCalendarGenerator = new XMLGregorianCalendarGenerator();
        zettCompanyObject.setCreatedTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar());
        zettCompanyObject.setCreatedBy("Zeeland");

        //if has new attribute, add new attribute also.
        log.debug("updating attribute");
        if(extendedCompanyObject.getExtendedAttribute().getName()!=null && extendedCompanyObject.getExtendedAttribute().getValue()!=null){
            if(!extendedCompanyObject.getExtendedAttribute().getName().isEmpty() && !extendedCompanyObject.getExtendedAttribute().getValue().isEmpty()){
                ObjectAttributeTransformer objectAttributeTransformer = new ObjectAttributeTransformer();
                ZettObjectAttribute extendedZettObjectAttribute = objectAttributeTransformer.transform(extendedCompanyObject.getExtendedAttribute());
                //ZettObjectAttribute[] arrayOfZettObjectAttribute = new ZettObjectAttribute[1];
                //arrayOfZettObjectAttribute[0] = extendedZettObjectAttribute;
                log.debug("attribute name "+extendedZettObjectAttribute.getName());
                log.debug("attribute value "+extendedZettObjectAttribute.getValue());
                zettCompanyObject.getAttributes().add(extendedZettObjectAttribute);
            }else{
                log.debug("attribute name and value are empty");
            }
        }

        webserviceAuthentication.authentication(companyServicePortType);

        CompanyObjectResponse companyObjectResponse = companyServicePortType.saveCompany(zettCompanyObject);
        zettCompanyObject = companyObjectResponse.getZettCompanyObject();
        log.debug("new company id = "+zettCompanyObject.getObjectId());
        return zettCompanyObject;
    }

    public ZettCompanyObject update(ExtendedCompanyObject extendedCompanyObject) throws ServiceException_Exception {
        log.debug("update company");
        if(extendedCompanyObject.getObjectId()!=null && extendedCompanyObject.getObjectId()>0){
            XMLGregorianCalendarGenerator xmlGregorianCalendarGenerator = new XMLGregorianCalendarGenerator();
            Date date = new Date();
            webserviceAuthentication.authentication(companyServicePortType);
            CompanyObjectResponse companyObjectResponse = companyServicePortType.loadCompany(extendedCompanyObject.getObjectId());
            ZettCompanyObject zettCompanyObject = companyObjectResponse.getZettCompanyObject();

            zettCompanyObject.setTitle(extendedCompanyObject.getTitle());
            zettCompanyObject.setCategory(extendedCompanyObject.getCategory());
            zettCompanyObject.setStatus(extendedCompanyObject.getStatus().toInteger());
            zettCompanyObject.setAdminstatus(extendedCompanyObject.getAdminStatus().toInteger());
            zettCompanyObject.setDataTable(extendedCompanyObject.getDataTable());
            zettCompanyObject.setCreatedBy(extendedCompanyObject.getCreatedBy());
            //zettCompanyObject.setCreatedTime(AdConverter.getXMLGregorianCalendar(companyObject.getCreatedTime()));

            zettCompanyObject.setModifiedTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar(date));
            zettCompanyObject.setTitle(extendedCompanyObject.getTitle());
            ZettPackageDeal zettPackageDeal = zettCompanyObject.getPackageDeal();
            zettPackageDeal.setPackageDealtype(extendedCompanyObject.getPackageDealTypeValue());
            zettCompanyObject.setPackageDeal(zettPackageDeal);
            zettCompanyObject.setParentId(extendedCompanyObject.getParentId());
            zettCompanyObject.setBillingCompanyId(extendedCompanyObject.getBillingCompanyId());
            AddressUpdate addressUpdate = new AddressUpdate();
            if(zettCompanyObject.getAddress() != null){
                ZettAddress zettAddress = addressUpdate.update(zettCompanyObject.getAddress(),extendedCompanyObject.getAddress());
                zettCompanyObject.setAddress(zettAddress);
            }

            ContactListUpdate contactListUpdate = new ContactListUpdate();
            if(zettCompanyObject.getContacts()!=null){
                ZettContact[] zettContacts = new ZettContact[zettCompanyObject.getContacts().size()];
                ZettContact[] arrayOfZettContact = contactListUpdate.update(zettCompanyObject.getContacts().toArray(zettContacts),extendedCompanyObject.getContacts(),extendedCompanyObject.getExtendedContact());

                zettCompanyObject.getContacts().clear();
                for(ZettContact zettContact:arrayOfZettContact){
                    zettCompanyObject.getContacts().add(zettContact);
                    //zettCompanyObject.setContacts(arrayOfZettContact);
                }
            }

            ObjectAttributeListUpdate objectAttributeListUpdate = new ObjectAttributeListUpdate();
            if(zettCompanyObject.getAttributes()!=null){
                ZettObjectAttribute[] zettObjectAttributes = new ZettObjectAttribute[zettCompanyObject.getAttributes().size()];
                ZettObjectAttribute[] arrayOfZettObjectAttribute =
                        objectAttributeListUpdate.update(zettCompanyObject.getAttributes().toArray(zettObjectAttributes),extendedCompanyObject.getAttributes(),extendedCompanyObject.getExtendedAttribute());

                zettCompanyObject.getAttributes().clear();
                for(ZettObjectAttribute zettObjectAttribute:arrayOfZettObjectAttribute){
                    if(zettObjectAttribute.getValue() != null && !zettObjectAttribute.getValue().isEmpty()){
                        zettCompanyObject.getAttributes().add(zettObjectAttribute);
                    }
                }
            }

            CompanyObjectResponse newCompanyObjectResponse = companyServicePortType.saveCompany(zettCompanyObject);
            zettCompanyObject = newCompanyObjectResponse.getZettCompanyObject();
            return zettCompanyObject;
        }
        return null;
    }

    public ZettCompanyObject deleteContact(int companyId, int contactId) throws ServiceException_Exception {
        if(companyId > 0 && contactId > 0){
            webserviceAuthentication.authentication(companyServicePortType);
            CompanyObjectResponse companyObjectResponse = companyServicePortType.loadCompany(companyId);
            ZettCompanyObject zettCompanyObject = companyObjectResponse.getZettCompanyObject();

            //ZettContact[] arrayOfZettContact = zettCompanyObject.getContacts();
            //List<ZettContact> contactList = Arrays.asList(arrayOfZettContact);
            List<ZettContact> contactList = zettCompanyObject.getContacts();
            log.debug("Size before delete " + contactList.size());
            ZettContact deleteContact = new ZettContact();

            for (ZettContact contact : contactList) {
                if (contactList.size() > 1) {
                    if (contact.getContactId() == (contactId)) {
                        deleteContact = contact;
                        log.debug("#####  Found ######" + deleteContact);
                    }
                } else {
                    log.debug("The company needs at least one contact");
                }
            }
            if ((deleteContact.getEmail() != null) && (deleteContact.getName() != null)){
                zettCompanyObject.getContacts().remove(deleteContact);
            }
            log.debug("Size after delete " + zettCompanyObject.getContacts().size());
            //ZettContact[] arrayOfNewZettContact = new ZettContact[contactList.size()];
            //zettCompanyObject.setContacts(contactList.toArray(arrayOfNewZettContact));

            log.debug("return size : " + zettCompanyObject.getContacts());
            return zettCompanyObject;
        }
        return null;
    }

}
