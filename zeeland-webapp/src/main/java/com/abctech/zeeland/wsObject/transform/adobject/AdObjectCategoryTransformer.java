package com.abctech.zeeland.wsObject.transform.adobject;

import no.zett.model.base.AdObjectCategory;
import no.zett.service.facade.ZettAdObjectCategory;

public class AdObjectCategoryTransformer{
    
    public ZettAdObjectCategory transform(AdObjectCategory adObjectCategory){
        ZettAdObjectCategory zettAdObjectCategory = new ZettAdObjectCategory();
        zettAdObjectCategory.setCategoryId(adObjectCategory.getCategoryId());
        zettAdObjectCategory.setFullname(adObjectCategory.getFullname());
        zettAdObjectCategory.setLink(adObjectCategory.getLink());
        zettAdObjectCategory.setName(adObjectCategory.getName());
        zettAdObjectCategory.setObjectId(adObjectCategory.getObject_id());
        zettAdObjectCategory.setParent(adObjectCategory.getParent());
        zettAdObjectCategory.setSeqId(adObjectCategory.getSeq_id());
        zettAdObjectCategory.setType(adObjectCategory.getType());
        return zettAdObjectCategory;
    }

    public AdObjectCategory transform(ZettAdObjectCategory zettAdObjectCategory){
        AdObjectCategory adObjectCategory = new AdObjectCategory();
        adObjectCategory.setCategoryId(zettAdObjectCategory.getCategoryId());
        if(zettAdObjectCategory.getFullname()!=null){
            adObjectCategory.setFullname(zettAdObjectCategory.getFullname());
        }
        adObjectCategory.setLink(zettAdObjectCategory.getLink());
        if(zettAdObjectCategory.getName()!=null){
            adObjectCategory.setName(zettAdObjectCategory.getName());
        }
        adObjectCategory.setObject_id(zettAdObjectCategory.getObjectId());
        adObjectCategory.setParent(zettAdObjectCategory.getParent());
        adObjectCategory.setSeq_id(adObjectCategory.getSeq_id());
        if(zettAdObjectCategory.getType()!=null){
            adObjectCategory.setType(zettAdObjectCategory.getType());
        }
        return adObjectCategory;
    }
}
