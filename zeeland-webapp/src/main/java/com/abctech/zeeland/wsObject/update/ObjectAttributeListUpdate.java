package com.abctech.zeeland.wsObject.update;

import com.abctech.zeeland.wsObject.transform.base.ObjectAttributeTransformer;
import no.zett.model.base.ObjectAttribute;
import no.zett.service.facade.ZettObjectAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectAttributeListUpdate {

    private final static Logger log = LoggerFactory.getLogger(ObjectAttributeListUpdate.class);

    public ZettObjectAttribute[] update(ZettObjectAttribute[] arrayOfZettObjectAttributeParam
                                            ,List<ObjectAttribute> objectAttributeList
                                            ,ObjectAttribute extendedAttribute){

        List<ZettObjectAttribute> zettObjectAttributeList;
        if(arrayOfZettObjectAttributeParam.length > 0){
            zettObjectAttributeList = Arrays.asList(arrayOfZettObjectAttributeParam);
        }else{
            zettObjectAttributeList = new ArrayList<ZettObjectAttribute>();
        }

        if(zettObjectAttributeList.size()>0){
            for(ZettObjectAttribute zettObjectAttribute:zettObjectAttributeList){
                for(ObjectAttribute objectAttribute:objectAttributeList){

                    if(objectAttribute.getAttributeId() != null && objectAttribute.getAttributeId().equals(zettObjectAttribute.getAttributeId())){
                        zettObjectAttribute.setValue(objectAttribute.getValue());
                    }
                }
            }

            if(extendedAttribute.getName() != null && extendedAttribute.getValue() != null){
                if(!extendedAttribute.getName().isEmpty() && !extendedAttribute.getValue().isEmpty()){
                    ObjectAttributeTransformer objectAttributeTransformer = new ObjectAttributeTransformer();
                    ZettObjectAttribute extendedZettObjectAttribute = objectAttributeTransformer.transform(extendedAttribute);
                    log.debug("zett object attribute list size = "+zettObjectAttributeList.size());
                    
                    List<ZettObjectAttribute> newZettObjectAttributeList = new ArrayList<ZettObjectAttribute>();
                    for(ZettObjectAttribute zettObjectAttribute:zettObjectAttributeList){
                        newZettObjectAttributeList.add(zettObjectAttribute);
                    }

                    newZettObjectAttributeList.add(extendedZettObjectAttribute);
                    zettObjectAttributeList = newZettObjectAttributeList;
                   
                    //zettObjectAttributeList.add(zettObjectAttribute);
                }
            }
        }else{
            if(extendedAttribute.getName() != null && extendedAttribute.getValue() != null){
                //zettObjectAttributeList = new ArrayList<ZettObjectAttribute>();
                if(!extendedAttribute.getName().isEmpty() && !extendedAttribute.getValue().isEmpty()){
                    ObjectAttributeTransformer objectAttributeTransformer = new ObjectAttributeTransformer();
                    ZettObjectAttribute zettObjectAttribute = objectAttributeTransformer.transform(extendedAttribute);
                    zettObjectAttributeList.add(zettObjectAttribute);
                }
            }
        }

        ZettObjectAttribute[] arrayOfZettObjectAttribute = new ZettObjectAttribute[zettObjectAttributeList.size()];
        return zettObjectAttributeList.toArray(arrayOfZettObjectAttribute);
    }
}
