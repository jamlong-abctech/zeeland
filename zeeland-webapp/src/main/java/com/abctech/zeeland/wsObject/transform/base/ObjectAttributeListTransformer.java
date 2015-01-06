package com.abctech.zeeland.wsObject.transform.base;

import no.zett.model.base.ObjectAttribute;
import no.zett.service.facade.ZettObjectAttribute;

import java.util.ArrayList;
import java.util.List;

public class ObjectAttributeListTransformer{

    public ZettObjectAttribute[] transform(List<ObjectAttribute> objectAttributeList){
        ZettObjectAttribute[] arrayOfZettObjectAttribute;
        if(objectAttributeList!=null && objectAttributeList.size()>0){
            arrayOfZettObjectAttribute = new ZettObjectAttribute[objectAttributeList.size()];
            ObjectAttributeTransformer objectAttributeTransformer = new ObjectAttributeTransformer();
            for(int i = 0;i<objectAttributeList.size();i++){
                ZettObjectAttribute zettObjectAttribute = objectAttributeTransformer.transform(objectAttributeList.get(i));
                arrayOfZettObjectAttribute[i] = zettObjectAttribute;
            }
        }else{
            arrayOfZettObjectAttribute = new ZettObjectAttribute[1];
        }

        return arrayOfZettObjectAttribute;
    }
    
    public List<ObjectAttribute> transform(ZettObjectAttribute[] arrayOfZettObjectAttribute){
        List<ObjectAttribute> objectAttributeList = new ArrayList<ObjectAttribute>();
        ObjectAttributeTransformer objectAttributeTransformer = new ObjectAttributeTransformer();

        for(int i = 0;i<arrayOfZettObjectAttribute.length;i++){
            ObjectAttribute objectAttribute = objectAttributeTransformer.transform(arrayOfZettObjectAttribute[i]);
            objectAttributeList.add(objectAttribute);
        }

        return objectAttributeList;
    }
    
}
