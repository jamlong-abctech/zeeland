package com.abctech.zeeland.form.data;

import no.zett.model.AdObject;
import no.zett.model.base.ObjectAttribute;
import no.zett.model.base.ObjectMedia;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ExtendedAdObject extends AdObject{

    private Integer boost;
    private ObjectAttribute extendedAttribute;
    private String publishFromTimeString;
    private String publishToTimeString;
    private String createdTimeString;
    private String modifiedTimeString;
    private MultipartFile[] adImgs;
    private String imageOrder;
    private String systemModifiedTimeString;
    private List<ObjectMedia> objectMediaList;

    public String getSystemModifiedTimeString() {
        return systemModifiedTimeString;
    }

    public void setSystemModifiedTimeString(String systemModifiedTimeString) {
        this.systemModifiedTimeString = systemModifiedTimeString;
    }

    public String getImageOrder() {
        return imageOrder;
    }

    public void setImageOrder(String imageOrder) {
        this.imageOrder = imageOrder;
    }

    public MultipartFile[] getAdImgs() {
        return adImgs;
    }

    public void setAdImgs(MultipartFile[] adImgs) {
        this.adImgs = adImgs;
    }

    public Integer getBoost() {
        return boost;
    }

    public void setBoost(Integer boost) {
        this.boost = boost;
    }

    public ObjectAttribute getExtendedAttribute() {
        return extendedAttribute;
    }

    public void setExtendedAttribute(ObjectAttribute extendedAttribute) {
        this.extendedAttribute = extendedAttribute;
    }

    public String getPublishFromTimeString() {
        return publishFromTimeString;
    }

    public void setPublishFromTimeString(String publishFromTimeStr) {
        this.publishFromTimeString = publishFromTimeStr;
    }

    public String getPublishToTimeString() {
        return publishToTimeString;
    }

    public void setPublishToTimeString(String publishToTimeStr) {
        this.publishToTimeString = publishToTimeStr;
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
