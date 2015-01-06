package com.abctech.zeeland.wsObject.transform.adobject;

import com.abctech.zeeland.form.util.XMLGregorianCalendarGenerator;
import com.abctech.zeeland.wsObject.transform.base.AddressTransformer;
import com.abctech.zeeland.wsObject.transform.base.ContactListTransformer;
import com.abctech.zeeland.wsObject.transform.base.ObjectAttributeListTransformer;
import com.abctech.zeeland.wsObject.transform.base.ObjectExternalReferenceListTransformer;
import com.abctech.zeeland.wsObject.transform.base.ObjectMediaListTransformer;
import com.abctech.zeeland.wsObject.transform.companyobject.CompanyObjectTransformer;
import com.thoughtworks.xstream.XStream;
import no.zett.model.AdObject;
import no.zett.model.CompanyObject;
import no.zett.model.base.AdObjectAdditionalCompany;
import no.zett.model.base.AdObjectCategory;
import no.zett.model.base.Address;
import no.zett.model.base.Contact;
import no.zett.model.base.ObjectAttribute;
import no.zett.model.base.ObjectExternalReference;
import no.zett.model.base.ObjectMedia;
import no.zett.model.enums.AdObjectPublishingStatus;
import no.zett.model.enums.AdObjectTransactionType;
import no.zett.model.enums.ContactPrivacyStatus;
import no.zett.service.facade.ZettAdObject;
import no.zett.service.facade.ZettAdObjectAdditionalCompany;
import no.zett.service.facade.ZettAdObjectCategory;
import no.zett.service.facade.ZettAddress;
import no.zett.service.facade.ZettCompanyObject;
import no.zett.service.facade.ZettContact;
import no.zett.service.facade.ZettObjectAttribute;
import no.zett.service.facade.ZettObjectExternalReference;
import no.zett.service.facade.ZettObjectMedia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AdObjectTransformer{

    private final static Logger log = LoggerFactory.getLogger(AdObjectTransformer.class);

    public ZettAdObject transform(AdObject adObject){
        ZettAdObject zettAdObject = new ZettAdObject();
        zettAdObject.setObjectId(adObject.getObjectId());
        zettAdObject.setDataTable(adObject.getDataTable());
        zettAdObject.setStatus(adObject.getStatusValue());
        zettAdObject.setTitle(adObject.getTitle());
        zettAdObject.setShortDescription(adObject.getShortDescription());

        if(adObject.getAdminStatusValue()!=null){
            zettAdObject.setAdminstatus(adObject.getAdminStatusValue());
        }else{
            zettAdObject.setAdminstatus(1);
        }
        zettAdObject.setCategory(adObject.getCategory());

        AdObjectCategoryListTransformer adObjectCategoryListTransformer = new AdObjectCategoryListTransformer();
        ZettAdObjectCategory[] arrayOfZettAdObjectCategory = adObjectCategoryListTransformer.transform(adObject.getAdObjectCategories());
        //zettAdObject.setAdObjectCategories(arrayOfZettAdObjectCategory);
        for(ZettAdObjectCategory zettAdObjectCategory:arrayOfZettAdObjectCategory){
            zettAdObject.getAdObjectCategories().add(zettAdObjectCategory);
        }

        
        if(adObject.getCategoryId()!=null && adObject.getCategoryId()>0){
            zettAdObject.setCategoryId(adObject.getCategoryId());
        }else{
            zettAdObject.setCategoryId(0);
        }

        AdObjectCategoryTransformer adObjectCategoryTransformer = new AdObjectCategoryTransformer();
        if(adObject.getAdObjectCategory()!=null){
            ZettAdObjectCategory zettAdObjectCategory = adObjectCategoryTransformer.transform(adObject.getAdObjectCategory());
            zettAdObject.setAdObjectcategory(zettAdObjectCategory);
        }

        AdObjectAdditionalCompanyListTransformer adObjectAdditionalCompanyListTransformer = new AdObjectAdditionalCompanyListTransformer();
        ZettAdObjectAdditionalCompany[] arrayOfZettAdObjectAdditionalCompany = adObjectAdditionalCompanyListTransformer.transform(adObject.getAdditionalCompanies());
        //zettAdObject.setAdObjectAdditionalCompanies(arrayOfZettAdObjectAdditionalCompany);
        for(ZettAdObjectAdditionalCompany zettAdObjectAdditionalCompany:arrayOfZettAdObjectAdditionalCompany){
            zettAdObject.getAdObjectAdditionalCompanies().add(zettAdObjectAdditionalCompany);
        }

        XMLGregorianCalendarGenerator xmlGregorianCalendarGenerator = new XMLGregorianCalendarGenerator();
        if(adObject.getPublishFromTime()!=null){
            zettAdObject.setPublishFromTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar(adObject.getPublishFromTime()));
        }
        if(adObject.getPublishToTime()!=null){
            zettAdObject.setPublishToTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar(adObject.getPublishToTime()));
        }
        zettAdObject.setModifiedBy(adObject.getModifiedBy());
        if(adObject.getModifiedTime()!=null){
            zettAdObject.setModifiedTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar(adObject.getModifiedTime()));
        }
        zettAdObject.setDeletedBy(adObject.getDeletedBy());

        if(zettAdObject.getCreatedTime()!=null){
            zettAdObject.setCreatedTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar(adObject.getCreatedTime()));
        }
        zettAdObject.setCreatedBy(adObject.getCreatedBy());

        ContactListTransformer contactListTransformer = new ContactListTransformer();
        ZettContact[] arrayOfZettContact = contactListTransformer.transform(adObject.getContacts());
        //zettAdObject.setContacts(arrayOfZettContact);
        for(ZettContact zettContact:arrayOfZettContact){
            zettAdObject.getContacts().add(zettContact);
        }

        ObjectAttributeListTransformer objectAttributeListTransformer = new ObjectAttributeListTransformer();
        ZettObjectAttribute[] arrayOfZettObjectAttribute = objectAttributeListTransformer.transform(adObject.getAttributes());
        //zettAdObject.setAttributes(arrayOfZettObjectAttribute);
        for(ZettObjectAttribute zettObjectAttribute:arrayOfZettObjectAttribute){
            zettAdObject.getAttributes().add(zettObjectAttribute);
        }

        ZettObjectMedia[] arrayOfZettObjectMedia = ObjectMediaListTransformer.transform(adObject.getMedia());
        //zettAdObject.setMedia(arrayOfZettObjectMedia);
        for(ZettObjectMedia zettObjectMedia:arrayOfZettObjectMedia){
            zettAdObject.getMedia().add(zettObjectMedia);
        }

        ObjectExternalReferenceListTransformer objectExternalReferenceListTransformer = new ObjectExternalReferenceListTransformer();
        ZettObjectExternalReference[] arrayOfZettObjectExternalReference = objectExternalReferenceListTransformer.transform(adObject.getExternalReferences());
        //zettAdObject.setExternalReferences(arrayOfZettObjectExternalReference);
        for(ZettObjectExternalReference zettObjectExternalReference:arrayOfZettObjectExternalReference){
            zettAdObject.getExternalReferences().add(zettObjectExternalReference);
        }

        if(adObject.getDataTypeValue()!=null){
            zettAdObject.setDataType(adObject.getDataTypeValue());
        }
        zettAdObject.setTransactionType(adObject.getTransactionTypeValue());

        if(adObject.getTransactionStatusValue()!=null){
            zettAdObject.setTransactionStatus(adObject.getTransactionStatusValue());
        }else{
            zettAdObject.setTransactionStatus(1);
        }

        if(adObject.getTransactionChangedTime()!=null){
            zettAdObject.setTransactionChangedTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar(adObject.getTransactionChangedTime()));
        }

        if(adObject.getPublishingStatusValue()!=null){
            zettAdObject.setPublishingStatus(adObject.getPublishingStatusValue());
        }else{
            zettAdObject.setPublishingStatus(0);
        }

        zettAdObject.setZettCode(adObject.getZettCode());
        zettAdObject.setContactPrivacyStatus(adObject.getContactPrivacyStatusValue());
        
        CompanyObjectTransformer companyObjectTransformer = new CompanyObjectTransformer();
        if(adObject.getCompany()!=null){
            ZettCompanyObject zettCompanyObject = companyObjectTransformer.transform(adObject.getCompany());
            zettAdObject.setCompany(zettCompanyObject);
        }

        AddressTransformer addressTransformer = new AddressTransformer();
        ZettAddress zettAddress = addressTransformer.transform(adObject.getAddress());
        zettAdObject.setAddress(zettAddress);

        return zettAdObject;
    }

    public AdObject transform(ZettAdObject zettAdObject){

        AdObject adObject = new AdObject();
        adObject.setObjectId(zettAdObject.getObjectId());
        adObject.setStatusValue(zettAdObject.getStatus());
        adObject.setAdminStatusValue(zettAdObject.getAdminstatus());
        adObject.setCategoryId(zettAdObject.getCategoryId());

        if (zettAdObject.getTitle()!=null){
            adObject.setTitle(zettAdObject.getTitle());
        }

        if (zettAdObject.getShortDescription()!=null){
            adObject.setShortDescription(zettAdObject.getShortDescription());
        }

        if(zettAdObject.getDataTable()!=null){
            adObject.setDataTable(zettAdObject.getDataTable());
        }

        if(zettAdObject.getCategory()!=null){
            adObject.setCategory(zettAdObject.getCategory());
        }

        if(zettAdObject.getAdObjectAdditionalCompanies()!=null){
            AdObjectAdditionalCompanyListTransformer adObjectAdditionalCompanyListTransformer = new AdObjectAdditionalCompanyListTransformer();
            ZettAdObjectAdditionalCompany[] zettAdObjectAdditionalCompanies = new ZettAdObjectAdditionalCompany[zettAdObject.getAdObjectAdditionalCompanies().size()];
            List<AdObjectAdditionalCompany> adObjectAdditionalCompanyList = adObjectAdditionalCompanyListTransformer.transform(zettAdObject.getAdObjectAdditionalCompanies().toArray(zettAdObjectAdditionalCompanies));
            adObject.setAdditionalCompanies(adObjectAdditionalCompanyList);
        }

        if(zettAdObject.getPublishFromTime()!=null){
            adObject.setPublishFromTime(zettAdObject.getPublishFromTime().toGregorianCalendar().getTime());
        }

        if(zettAdObject.getPublishToTime()!=null){
            adObject.setPublishToTime(zettAdObject.getPublishToTime().toGregorianCalendar().getTime());
        }

        if (zettAdObject.getCreatedBy() != null) {
            adObject.setCreatedBy(zettAdObject.getCreatedBy());
        }

        if(zettAdObject.getCreatedTime() != null) {
            adObject.setCreatedTime(zettAdObject.getCreatedTime().toGregorianCalendar().getTime());
        }

        if (zettAdObject.getCreatedBy() != null){
            adObject.setModifiedBy(zettAdObject.getModifiedBy());
        }

        if (zettAdObject.getModifiedBy() != null){
            adObject.setModifiedBy(zettAdObject.getModifiedBy());
        }

        if(zettAdObject.getModifiedTime() != null) {
            adObject.setModifiedTime(zettAdObject.getModifiedTime().toGregorianCalendar().getTime());
        }

        if(zettAdObject.getSystemModifiedTime() != null){
            adObject.setSystemModifiedTime(zettAdObject.getSystemModifiedTime().toGregorianCalendar().getTime());
        }

        if(zettAdObject.getDeletedBy()!=null){
            adObject.setDeletedBy(zettAdObject.getDeletedBy());
        }
        if (zettAdObject.getDeletedTime()!=null){
            adObject.setDeletedTime(zettAdObject.getDeletedTime().toGregorianCalendar().getTime());
        }
        if (zettAdObject.getPublishFromTime()!=null){
            adObject.setPublishFromTime(zettAdObject.getPublishFromTime().toGregorianCalendar().getTime());
        }
        if(zettAdObject.getPublishToTime()!=null){
            adObject.setPublishToTime(zettAdObject.getPublishToTime().toGregorianCalendar().getTime());
        }

        if(zettAdObject.getContacts()!=null){
            ContactListTransformer contactListTransformer = new ContactListTransformer();
            ZettContact[] zettContacts = new ZettContact[zettAdObject.getContacts().size()];
            List<Contact> contactList = contactListTransformer.transform(zettAdObject.getContacts().toArray(zettContacts));
            adObject.setContacts(contactList);
        }

        if (zettAdObject.getAttributes()!=null){
            ObjectAttributeListTransformer objectAttributeListTransformer = new ObjectAttributeListTransformer();
            ZettObjectAttribute[] zettObjectAttributes = new ZettObjectAttribute[zettAdObject.getAttributes().size()];
            List<ObjectAttribute> objectAttributeList = objectAttributeListTransformer.transform(zettAdObject.getAttributes().toArray(zettObjectAttributes));
            adObject.setAttributes(objectAttributeList);
        }

        if(zettAdObject.getMedia()!=null){
            ZettObjectMedia[] zettObjectMedias = new ZettObjectMedia[zettAdObject.getMedia().size()];
            List<ObjectMedia> objectMediaList = ObjectMediaListTransformer.transform(
                    zettAdObject.getMedia().toArray(zettObjectMedias));
            adObject.setMedia(objectMediaList);
        }

        if(zettAdObject.getExternalReferences() != null) {
            ObjectExternalReferenceListTransformer objectExternalReferenceListTransformer = new ObjectExternalReferenceListTransformer();
            ZettObjectExternalReference[] zettObjectExternalReferences = new ZettObjectExternalReference[zettAdObject.getExternalReferences().size()];
            List<ObjectExternalReference> objectExternalReferenceList = objectExternalReferenceListTransformer.transform(zettAdObject.getExternalReferences().toArray(zettObjectExternalReferences));
            adObject.setExternalReferences(objectExternalReferenceList);
        }

        adObject.setTransactionStatusValue(zettAdObject.getTransactionStatus());
        adObject.setTransactionType(AdObjectTransactionType.fromInteger(zettAdObject.getTransactionType()));

        if (zettAdObject.getTransactionChangedTime()!=null){
            adObject.setTransactionChangedTime(zettAdObject.getTransactionChangedTime().toGregorianCalendar().getTime());
        }

        adObject.setPublishingStatus(AdObjectPublishingStatus.fromInteger(zettAdObject.getPublishingStatus()));
        adObject.setZettCode(zettAdObject.getZettCode());
        adObject.setContactPrivacyStatus(ContactPrivacyStatus.fromInteger(zettAdObject.getContactPrivacyStatus()));

        if(zettAdObject.getCompany()!=null){
            CompanyObjectTransformer companyObjectTransformer = new CompanyObjectTransformer();
            CompanyObject companyObject = companyObjectTransformer.transform(zettAdObject.getCompany());
            adObject.setCompany(companyObject);
        }

        if(zettAdObject.getAddress()!=null){
            AddressTransformer addressTransformer = new AddressTransformer();
            Address address = addressTransformer.transform(zettAdObject.getAddress());
            adObject.setAddress(address);
        }

        if(zettAdObject.getAdObjectcategory()!=null && zettAdObject.getAdObjectcategory()!=null){
            AdObjectCategoryTransformer adObjectCategoryTransformer = new AdObjectCategoryTransformer();
            AdObjectCategory adObjectCategory = adObjectCategoryTransformer.transform(zettAdObject.getAdObjectcategory());
            adObject.setAdObjectCategory(adObjectCategory);
        }

        /*XStream xStream = new XStream();
        log.debug("*********************************************");
        log.debug(xStream.toXML(adObject));
        log.debug("*********************************************");
*/
        return adObject;
    }
}
