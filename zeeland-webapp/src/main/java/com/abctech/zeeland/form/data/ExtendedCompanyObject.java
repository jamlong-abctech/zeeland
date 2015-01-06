package com.abctech.zeeland.form.data;

import no.zett.model.CompanyObject;
import no.zett.model.base.Contact;
import no.zett.model.base.ObjectAttribute;
import no.zett.model.base.ObjectMedia;

import java.util.List;

public class ExtendedCompanyObject extends CompanyObject{

    private Integer packageDealTypeValue;
    private ObjectAttribute extendedAttribute;
    private Contact extendedContact;
    private String createdTimeString;
    private String modifiedTimeString;
    private String systemModifiedTimeString;
    private List<ObjectMedia> objectMediaList;

    public String getSystemModifiedTimeString() {
        return systemModifiedTimeString;
    }

    public void setSystemModifiedTimeString(String systemModifiedTimeString) {
        this.systemModifiedTimeString = systemModifiedTimeString;
    }

    public Integer getPackageDealTypeValue() {
        return packageDealTypeValue;
    }

    public void setPackageDealTypeValue(Integer packageDealTypeValue) {
        this.packageDealTypeValue = packageDealTypeValue;
    }

    public ObjectAttribute getExtendedAttribute() {
        return extendedAttribute;
    }

    public void setExtendedAttribute(ObjectAttribute extendedAttribute) {
        this.extendedAttribute = extendedAttribute;
    }

    public Contact getExtendedContact() {
        return extendedContact;
    }

    public void setExtendedContact(Contact extendedContact) {
        this.extendedContact = extendedContact;
    }

    public String getCreatedTimeString() {
        return createdTimeString;
    }

    public void setCreatedTimeString(String createdTimeString) {
        this.createdTimeString = createdTimeString;
    }

    public String getModifiedTimeString() {
        return modifiedTimeString;
    }

    public void setModifiedTimeString(String modifiedTimeString) {
        this.modifiedTimeString = modifiedTimeString;
    }

    public List<ObjectMedia> getObjectMediaList() {
        return objectMediaList;
    }

    public void setObjectMediaList(List<ObjectMedia> objectMediaList) {
        this.objectMediaList = objectMediaList;
    }
}
