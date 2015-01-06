package com.abctech.zeeland.form.data;

import no.zett.model.base.AdObjectCategory;
import no.zett.model.base.Address;
import no.zett.model.base.Contact;
import no.zett.model.base.ObjectAttribute;
import no.zett.model.base.ObjectMedia;
import no.zett.model.enums.AdObjectTransactionType;
import no.zett.model.newspaper.PrintAdObject;

import java.util.Date;
import java.util.List;

public class ExtendedPrintAdObject extends PrintAdObject {

    private Integer objectId  ;
    private String dataTable ;
    private Integer statusValue ;
    private Integer adminStatusValue ;
    private String category ;
    private String title ;
    private String shortDescription ;
    private Address address ;
    private Date publishFromTime ;
    private Date publishToTime ;
    private String createdBy ;
    private Date createdTime ;
    private String modifiedBy  ;
    private Date modifiedTime ;
    private String deletedBy ;
    private Date deletedTime ;
    private Date systemCreatedTime ;
    private Date systemModifiedTime  ;
    private Integer newspaperId ;
    private AdObjectTransactionType transactionType ;
    private AdObjectCategory adObjectCategory ;
    private List<ObjectAttribute> attributes ;
    private List<Contact>  contacts ;
    private List<ObjectMedia> media ;

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getDataTable() {
        return dataTable;
    }

    public void setDataTable(String dataTable) {
        this.dataTable = dataTable;
    }

    public Integer getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(Integer statusValue) {
        this.statusValue = statusValue;
    }

    public Integer getAdminStatusValue() {
        return adminStatusValue;
    }

    public void setAdminStatusValue(Integer adminStatusValue) {
        this.adminStatusValue = adminStatusValue;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getPublishFromTime() {
        return publishFromTime;
    }

    public void setPublishFromTime(Date publishFromTime) {
        this.publishFromTime = publishFromTime;
    }

    public Date getPublishToTime() {
        return publishToTime;
    }

    public void setPublishToTime(Date publishToTime) {
        this.publishToTime = publishToTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getDeletedTime() {
        return deletedTime;
    }

    public void setDeletedTime(Date deletedTime) {
        this.deletedTime = deletedTime;
    }

    public Date getSystemCreatedTime() {
        return systemCreatedTime;
    }

    public void setSystemCreatedTime(Date systemCreatedTime) {
        this.systemCreatedTime = systemCreatedTime;
    }

    public Date getSystemModifiedTime() {
        return systemModifiedTime;
    }

    public void setSystemModifiedTime(Date systemModifiedTime) {
        this.systemModifiedTime = systemModifiedTime;
    }

    public Integer getNewspaperId() {
        return newspaperId;
    }

    public void setNewspaperId(Integer newspaperId) {
        this.newspaperId = newspaperId;
    }

    public AdObjectTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(AdObjectTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public AdObjectCategory getAdObjectCategory() {
        return adObjectCategory;
    }

    public void setAdObjectCategory(AdObjectCategory adObjectCategory) {
        this.adObjectCategory = adObjectCategory;
    }

    public List<ObjectAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ObjectAttribute> attributes) {
        this.attributes = attributes;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<ObjectMedia> getMedia() {
        return media;
    }

    public void setMedia(List<ObjectMedia> media) {
        this.media = media;
    }
}
