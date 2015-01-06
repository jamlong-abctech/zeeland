package com.abctech.zeeland.wsObject.transform.base;

import no.zett.model.base.ObjectExternalReference;
import no.zett.service.facade.ZettObjectExternalReference;

public class ObjectExternalReferenceTransformer{

    public ZettObjectExternalReference transform(ObjectExternalReference objectExternalReference){
        ZettObjectExternalReference zettObjectExternalReference = new ZettObjectExternalReference();
        zettObjectExternalReference.setExternalReferenceId(objectExternalReference.getExternalReferenceId());
        zettObjectExternalReference.setObjectId(objectExternalReference.getObjectId());
        zettObjectExternalReference.setReference(objectExternalReference.getReference());
        zettObjectExternalReference.setSource(objectExternalReference.getSource());
        zettObjectExternalReference.setSystem(objectExternalReference.getSystem());
        zettObjectExternalReference.setSystemVersion(objectExternalReference.getSystemVersion());
        zettObjectExternalReference.setType(objectExternalReference.getTypeValue());
        return zettObjectExternalReference;
    }

    public ObjectExternalReference transform(ZettObjectExternalReference zettObjectExternalReference){
        ObjectExternalReference objectExternalReference = new ObjectExternalReference();
        objectExternalReference.setExternalReferenceId(zettObjectExternalReference.getExternalReferenceId());
        objectExternalReference.setObjectId(zettObjectExternalReference.getObjectId());
        if(zettObjectExternalReference.getReference()!=null){
            objectExternalReference.setReference(zettObjectExternalReference.getReference());
        }
        if(zettObjectExternalReference.getSource()!=null){
            objectExternalReference.setSource(zettObjectExternalReference.getSource());
        }
        if(zettObjectExternalReference.getSystem()!=null){
            objectExternalReference.setSystem(zettObjectExternalReference.getSystem());
        }
        if(zettObjectExternalReference.getSystemVersion()!=null){
            objectExternalReference.setSystemVersion(zettObjectExternalReference.getSystemVersion());
        }
        objectExternalReference.setTypeValue(zettObjectExternalReference.getType());
        return objectExternalReference;
    }

}
