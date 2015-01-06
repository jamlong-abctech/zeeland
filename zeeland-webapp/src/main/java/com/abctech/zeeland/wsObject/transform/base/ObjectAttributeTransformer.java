package com.abctech.zeeland.wsObject.transform.base;

import no.zett.model.base.ObjectAttribute;
import no.zett.service.facade.ZettObjectAttribute;

public class ObjectAttributeTransformer{

    public ZettObjectAttribute transform(ObjectAttribute objectAttribute){
        ZettObjectAttribute zettObjectAttribute = new ZettObjectAttribute();
        if(objectAttribute.getAttributeId()!=null){
            zettObjectAttribute.setAttributeId(objectAttribute.getAttributeId());
        }
        if(objectAttribute.getTypeValue()!=null){
            zettObjectAttribute.setType(objectAttribute.getTypeValue());
        }
        if(objectAttribute.getLabel()!=null){
            zettObjectAttribute.setLabel(objectAttribute.getLabel());
        }
        if(objectAttribute.getName()!=null){
            zettObjectAttribute.setName(objectAttribute.getName());
        }
        if(objectAttribute.getOrder()!=null){
            zettObjectAttribute.setOrder(objectAttribute.getOrder());
        }
        zettObjectAttribute.setValue(objectAttribute.getValue());
        return zettObjectAttribute;
    }

    public ObjectAttribute transform(ZettObjectAttribute zettObjectAttribute){
        ObjectAttribute objectAttribute = new ObjectAttribute();
        objectAttribute.setAttributeId(zettObjectAttribute.getAttributeId());
        /*
        if(zettObjectAttribute.getType()!=null){
            objectAttribute.setType(ObjectAttributeType.fromInteger(zettObjectAttribute.getType()));
        }*/
        objectAttribute.setTypeValue(zettObjectAttribute.getType());
        if(zettObjectAttribute.getLabel()!=null){
            objectAttribute.setLabel(zettObjectAttribute.getLabel());
        }
        if(zettObjectAttribute.getName()!=null){
            objectAttribute.setName(zettObjectAttribute.getName());
        }
        objectAttribute.setOrder(zettObjectAttribute.getOrder());
        if(zettObjectAttribute.getValue()!=null){
            objectAttribute.setValue(zettObjectAttribute.getValue());
        }

        return objectAttribute;
    }

}
