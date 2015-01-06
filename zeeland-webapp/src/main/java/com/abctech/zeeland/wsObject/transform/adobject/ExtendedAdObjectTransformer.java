package com.abctech.zeeland.wsObject.transform.adobject;

import com.abctech.zeeland.form.data.ExtendedAdObject;
import com.abctech.zeeland.wsObject.transform.base.ObjectMediaListTransformer;
import no.zett.model.AdObject;
import no.zett.model.base.ObjectAttribute;
import no.zett.model.base.ObjectMedia;
import no.zett.service.facade.ZettAdObject;
import no.zett.service.facade.ZettObjectMedia;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtendedAdObjectTransformer{

    public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    public ZettAdObject transform(ExtendedAdObject extendedAdObject){
        AdObjectTransformer adObjectTransformer = new AdObjectTransformer();
        ZettAdObject zettAdObject = adObjectTransformer.transform(extendedAdObject);
        return zettAdObject;
    }

    public ExtendedAdObject transform(ZettAdObject zettAdObject){
        AdObjectTransformer adObjectTransformer = new AdObjectTransformer();
        AdObject adObject = adObjectTransformer.transform(zettAdObject);
        ExtendedAdObject extendedAdObject = new ExtendedAdObject();

        extendedAdObject.setObjectId(adObject.getObjectId());
        extendedAdObject.setStatusValue(adObject.getStatusValue());
        extendedAdObject.setAdminStatusValue(adObject.getAdminStatusValue());
        extendedAdObject.setCategoryId(adObject.getCategoryId());
        extendedAdObject.setTitle(adObject.getTitle());
        extendedAdObject.setShortDescription(adObject.getShortDescription());
        extendedAdObject.setDataTable(adObject.getDataTable());
        extendedAdObject.setCategory(adObject.getCategory());
        extendedAdObject.setAdditionalCompanies(adObject.getAdditionalCompanies());
        extendedAdObject.setCreatedBy(adObject.getCreatedBy());
        extendedAdObject.setCreatedTime(adObject.getCreatedTime());
        if(adObject.getCreatedTime() != null){
            Date date = adObject.getCreatedTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
            extendedAdObject.setCreatedTimeString(simpleDateFormat.format(date));
        }

        if(adObject.getModifiedBy() != null) {
            extendedAdObject.setModifiedBy(adObject.getModifiedBy());
        }
        if(adObject.getModifiedTime() != null){
            extendedAdObject.setModifiedTime(adObject.getModifiedTime());
            Date date = adObject.getModifiedTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
            extendedAdObject.setModifiedTimeString(simpleDateFormat.format(date));
        }
        if(adObject.getSystemModifiedTime() != null){
            extendedAdObject.setSystemModifiedTime(adObject.getSystemModifiedTime());
            Date date = adObject.getSystemModifiedTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
            extendedAdObject.setSystemModifiedTimeString(simpleDateFormat.format(date));
        }

        extendedAdObject.setDeletedBy(adObject.getDeletedBy());
        extendedAdObject.setDeletedTime(adObject.getDeletedTime());
        extendedAdObject.setPublishFromTime(adObject.getPublishFromTime());
        if(adObject.getPublishFromTime()!=null){
            Date date = adObject.getPublishFromTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
            extendedAdObject.setPublishFromTimeString(simpleDateFormat.format(date));
        }
        extendedAdObject.setPublishToTime(adObject.getPublishToTime());
        if(adObject.getPublishToTime() != null){
            Date date = adObject.getPublishToTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
            extendedAdObject.setPublishToTimeString(simpleDateFormat.format(date));
        }
        extendedAdObject.setContacts(adObject.getContacts());
        extendedAdObject.setAttributes(adObject.getAttributes());
        extendedAdObject.setMedia(adObject.getMedia());

        ZettObjectMedia[] zettObjectMedias = zettAdObject.getMedia().toArray(
                new ZettObjectMedia[zettAdObject.getMedia().size()]);
        List<ObjectMedia> objectMediaList = ObjectMediaListTransformer.transform(zettObjectMedias);
        extendedAdObject.setObjectMediaList(objectMediaList);
        
        extendedAdObject.setExternalReferences(adObject.getExternalReferences());
        extendedAdObject.setTransactionStatusValue(adObject.getTransactionStatusValue());
        extendedAdObject.setTransactionType(adObject.getTransactionType());
        extendedAdObject.setTransactionChangedTime(adObject.getTransactionChangedTime());
        extendedAdObject.setPublishingStatus(adObject.getPublishingStatus());
        extendedAdObject.setZettCode(adObject.getZettCode());
        extendedAdObject.setContactPrivacyStatus(adObject.getContactPrivacyStatus());
        extendedAdObject.setCompany(adObject.getCompany());
        extendedAdObject.setAddress(adObject.getAddress());
        extendedAdObject.setAdObjectCategory(adObject.getAdObjectCategory());
        //add new attribute
        ObjectAttribute objectAttribute = new ObjectAttribute();
        extendedAdObject.setExtendedAttribute(objectAttribute);
        return extendedAdObject;
    }
}
