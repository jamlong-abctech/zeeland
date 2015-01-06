package com.abctech.zeeland.wsObject.transform.companyobject;

import com.abctech.zeeland.form.util.XMLGregorianCalendarGenerator;
import com.abctech.zeeland.wsObject.transform.base.*;
import no.zett.model.CompanyObject;
import no.zett.model.PackageDeal;
import no.zett.model.base.Address;
import no.zett.model.base.Contact;
import no.zett.model.base.ObjectAttribute;
import no.zett.model.base.ObjectMedia;
import no.zett.model.enums.ObjectAdminStatus;
import no.zett.model.enums.ObjectStatus;
import no.zett.service.facade.*;

import java.util.List;

public class CompanyObjectTransformer{

//    private final static Logger log = LoggerFactory.getLogger(CompanyObjectTransformer.class);

    public ZettCompanyObject transform(CompanyObject companyObject){

        ZettCompanyObject zettCompanyObject = new ZettCompanyObject();
        if(companyObject.getObjectId()!=null){
            zettCompanyObject.setObjectId(companyObject.getObjectId());
        }
        zettCompanyObject.setDataTable(companyObject.getDataTable());
        zettCompanyObject.setStatus(companyObject.getStatusValue());
        zettCompanyObject.setAdminstatus(companyObject.getAdminStatusValue());
        zettCompanyObject.setCategory(companyObject.getCategory());
        zettCompanyObject.setTitle(companyObject.getTitle());
        zettCompanyObject.setShortDescription(companyObject.getShortDescription());

        XMLGregorianCalendarGenerator xmlGregorianCalendarGenerator = new XMLGregorianCalendarGenerator();

        if(companyObject.getPublishFromTime()!=null){
            zettCompanyObject.setPublishFromTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar(companyObject.getPublishFromTime()));
        }
        if(companyObject.getPublishToTime()!=null){
            zettCompanyObject.setPublishToTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar(companyObject.getPublishToTime()));
        }
        zettCompanyObject.setModifiedBy(companyObject.getModifiedBy());
        if(companyObject.getModifiedTime()!=null){
            zettCompanyObject.setModifiedTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar(companyObject.getModifiedTime()));
        }
        zettCompanyObject.setDeletedBy(companyObject.getDeletedBy());

        if(zettCompanyObject.getCreatedTime()!=null){
            zettCompanyObject.setCreatedTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar(companyObject.getCreatedTime()));
        }
        zettCompanyObject.setCreatedBy(companyObject.getCreatedBy());

        ContactListTransformer contactListTransformer = new ContactListTransformer();
        ZettContact[] arrayOfZettContact = contactListTransformer.transform(companyObject.getContacts());
        zettCompanyObject.getContacts().clear();
        for(ZettContact zettContact:arrayOfZettContact){
            zettCompanyObject.getContacts().add(zettContact);
        }
        //zettCompanyObject.setContacts(arrayOfZettContact);

        ObjectAttributeListTransformer objectAttributeListTransformer = new ObjectAttributeListTransformer();
        ZettObjectAttribute[] arrayOfZettObjectAttribute = objectAttributeListTransformer.transform(companyObject.getAttributes());
        zettCompanyObject.getAttributes().clear();
        for(ZettObjectAttribute zettObjectAttribute:arrayOfZettObjectAttribute){
            //zettCompanyObject.setAttributes(arrayOfZettObjectAttribute);
            zettCompanyObject.getAttributes().add(zettObjectAttribute);
        }

        ZettObjectMedia[] arrayOfZettObjectMedia = ObjectMediaListTransformer.transform(companyObject.getMedia());
        zettCompanyObject.getMedia().clear();
        for(ZettObjectMedia zettObjectMedia:arrayOfZettObjectMedia){
            //zettCompanyObject.setMedia(arrayOfZettObjectMedia);
            zettCompanyObject.getMedia().add(zettObjectMedia);
        }

        if(companyObject.getParentId()!=null){
            zettCompanyObject.setParentId(companyObject.getParentId());
        }
        if(companyObject.getBillingCompanyId()!=null){
            zettCompanyObject.setBillingCompanyId(companyObject.getBillingCompanyId());
        }

        if(companyObject.getAddress()!=null){
            AddressTransformer addressTransformer = new AddressTransformer();
            ZettAddress zettAddress = addressTransformer.transform(companyObject.getAddress());
            zettCompanyObject.setAddress(zettAddress);
        }

        PackageDealTransformer packageDealTransformer = new PackageDealTransformer();
        ZettPackageDeal zettPackageDeal = packageDealTransformer.transform(companyObject.getPackagedeal());
        zettCompanyObject.setPackageDeal(zettPackageDeal);

        return zettCompanyObject;
    }


    public CompanyObject transform(ZettCompanyObject zettCompanyObject){
        CompanyObject companyObject = new CompanyObject();
        companyObject.setObjectId(zettCompanyObject.getObjectId());
        if(zettCompanyObject.getDataTable() != null) {
            companyObject.setDataTable(zettCompanyObject.getDataTable());
        }
        companyObject.setStatus(ObjectStatus.fromInteger(zettCompanyObject.getStatus()));
        companyObject.setStatusValue(zettCompanyObject.getStatus());
        companyObject.setAdminStatus(ObjectAdminStatus.fromInteger(zettCompanyObject.getAdminstatus()));
        companyObject.setAdminStatusValue(zettCompanyObject.getAdminstatus());
        if(zettCompanyObject.getCategory()!=null){
            companyObject.setCategory(zettCompanyObject.getCategory());
        }
        if(zettCompanyObject.getTitle()!=null) {
            companyObject.setTitle(zettCompanyObject.getTitle());
        }
        if(zettCompanyObject.getShortDescription()!= null) {
            companyObject.setShortDescription(zettCompanyObject.getShortDescription());
        }
        if(zettCompanyObject.getPublishToTime() != null) {
            companyObject.setPublishToTime(zettCompanyObject.getPublishToTime().toGregorianCalendar().getTime());
        }
        if(zettCompanyObject.getPublishFromTime() != null) {
            companyObject.setPublishFromTime(zettCompanyObject.getPublishFromTime().toGregorianCalendar().getTime());
        }
        if (zettCompanyObject.getCreatedBy()!=null){
            companyObject.setCreatedBy(zettCompanyObject.getCreatedBy());
        }
        if(zettCompanyObject.getCreatedTime()!=null){
            companyObject.setCreatedTime(zettCompanyObject.getCreatedTime().toGregorianCalendar().getTime());
        }
        if(zettCompanyObject.getDeletedBy()!=null){
            companyObject.setDeletedBy(zettCompanyObject.getDeletedBy());
        }
        if(zettCompanyObject.getDeletedTime()!=null){
            companyObject.setDeletedTime(zettCompanyObject.getDeletedTime().toGregorianCalendar().getTime());
        }
        if(zettCompanyObject.getModifiedBy()!=null){
            companyObject.setModifiedBy(zettCompanyObject.getModifiedBy());
        }
        if(zettCompanyObject.getModifiedTime()!=null){
            companyObject.setModifiedTime(zettCompanyObject.getModifiedTime().toGregorianCalendar().getTime());
        }
        if(zettCompanyObject.getSystemModifiedTime()!=null){
            companyObject.setSystemModifiedTime(zettCompanyObject.getSystemModifiedTime().toGregorianCalendar().getTime());
        }
        if(zettCompanyObject.getContacts()!=null){
            ContactListTransformer contactListTransformer = new ContactListTransformer();
            ZettContact[] zettContacts = new ZettContact[zettCompanyObject.getContacts().size()];
            List<Contact> contactList = contactListTransformer.transform(zettCompanyObject.getContacts().toArray(zettContacts));
            companyObject.setContacts(contactList);
        }

        if(zettCompanyObject.getAttributes()!=null){
            ObjectAttributeListTransformer objectAttributeListTransformer = new ObjectAttributeListTransformer();
            ZettObjectAttribute[] zettObjectAttributes = new ZettObjectAttribute[zettCompanyObject.getAttributes().size()];
            List<ObjectAttribute> objectAttributeList = objectAttributeListTransformer.transform(zettCompanyObject.getAttributes().toArray(zettObjectAttributes));
            companyObject.setAttributes(objectAttributeList);
        }

        if(zettCompanyObject.getMedia() != null) {
            ZettObjectMedia[] zettObjectMedias = new ZettObjectMedia[zettCompanyObject.getMedia().size()];
            List<ObjectMedia> objectMediaList = ObjectMediaListTransformer.transform(
                    zettCompanyObject.getMedia().toArray(zettObjectMedias));
            companyObject.setMedia(objectMediaList);
        }

        companyObject.setParentId(zettCompanyObject.getParentId());
        companyObject.setBillingCompanyId(zettCompanyObject.getBillingCompanyId());

        if(zettCompanyObject.getAddress()!=null){
            AddressTransformer addressTransformer = new AddressTransformer();
            Address address = addressTransformer.transform(zettCompanyObject.getAddress());
            companyObject.setAddress(address);
        }

        if(zettCompanyObject.getPackageDeal()!=null){
            PackageDealTransformer packageDealTransformer = new PackageDealTransformer();
            PackageDeal packageDeal = packageDealTransformer.transform(zettCompanyObject.getPackageDeal());
            companyObject.setPackagedeal(packageDeal);
        }

        return companyObject;

    }


}
