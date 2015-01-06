package com.abctech.zeeland.wsObject.transform.base;

import no.zett.model.base.ObjectMedia;
import no.zett.model.enums.ObjectMediaType;
import no.zett.service.facade.ZettObjectMedia;

public class ObjectMediaTransformer{
    
    public ZettObjectMedia transform(ObjectMedia objectMedia){
        ZettObjectMedia zettObjectMedia = new ZettObjectMedia();
        zettObjectMedia.setDescription(objectMedia.getDescription());
        zettObjectMedia.setMediaId(objectMedia.getMediaId());
        zettObjectMedia.setOrder(objectMedia.getOrder());
        zettObjectMedia.setReference(objectMedia.getReference());
        zettObjectMedia.setTitle(objectMedia.getTitle());
        zettObjectMedia.setType(objectMedia.getTypeValue());
        return zettObjectMedia;
    }

    public ObjectMedia transform(ZettObjectMedia zettObjectMedia){
        ObjectMedia objectMedia = new ObjectMedia();
        if(zettObjectMedia.getDescription()!=null){
            objectMedia.setDescription(zettObjectMedia.getDescription());
        }
        objectMedia.setMediaId(zettObjectMedia.getMediaId());
        objectMedia.setOrder(zettObjectMedia.getOrder());
        if(zettObjectMedia.getReference()!=null){
            objectMedia.setReference(zettObjectMedia.getReference());
        }
        if(zettObjectMedia.getTitle()!=null){
            objectMedia.setTitle(zettObjectMedia.getTitle());
        }
        objectMedia.setType(ObjectMediaType.fromInteger(zettObjectMedia.getType()));
        objectMedia.setTypeValue(zettObjectMedia.getType());
        return objectMedia;
    }
}
