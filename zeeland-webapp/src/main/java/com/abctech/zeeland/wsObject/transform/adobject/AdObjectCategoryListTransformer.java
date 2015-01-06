package com.abctech.zeeland.wsObject.transform.adobject;

import no.zett.model.base.AdObjectCategory;
import no.zett.service.facade.ZettAdObjectCategory;

import java.util.ArrayList;
import java.util.List;

public class AdObjectCategoryListTransformer{

    public ZettAdObjectCategory[] transform(List<AdObjectCategory> adObjectCategoryList){
        ZettAdObjectCategory[] arrayOfZettAdObjectCategory = new ZettAdObjectCategory[adObjectCategoryList.size()];
        AdObjectCategoryTransformer adObjectCategoryTransformer = new AdObjectCategoryTransformer();
        for(int i = 0;i<adObjectCategoryList.size();i++){
            ZettAdObjectCategory zettAdObjectCategory = adObjectCategoryTransformer.transform(adObjectCategoryList.get(i));
            arrayOfZettAdObjectCategory[i] = zettAdObjectCategory;
        }

        return arrayOfZettAdObjectCategory;
    }
    
    public List<AdObjectCategory> transform(ZettAdObjectCategory[] arrayOfZettAdObjectCategory){
        List<AdObjectCategory> adObjectCategoryList = new ArrayList<AdObjectCategory>();
        if(arrayOfZettAdObjectCategory!=null){
            AdObjectCategoryTransformer adObjectCategoryTransformer = new AdObjectCategoryTransformer();
            for(int i = 0;i<arrayOfZettAdObjectCategory.length;i++){
                AdObjectCategory adObjectCategory = adObjectCategoryTransformer.transform(arrayOfZettAdObjectCategory[i]);
                adObjectCategoryList.add(adObjectCategory);
            }
        }

        return adObjectCategoryList;
    }
}
