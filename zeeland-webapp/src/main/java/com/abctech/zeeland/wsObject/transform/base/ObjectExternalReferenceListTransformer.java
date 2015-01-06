package com.abctech.zeeland.wsObject.transform.base;

import no.zett.model.base.ObjectExternalReference;
import no.zett.service.facade.ZettObjectExternalReference;

import java.util.ArrayList;
import java.util.List;

public class ObjectExternalReferenceListTransformer{
    
    public ZettObjectExternalReference[] transform(List<ObjectExternalReference> objectExternalReferenceList){
        ZettObjectExternalReference[] arrayOfZettObjectExternalReference = new ZettObjectExternalReference[objectExternalReferenceList.size()];
        ObjectExternalReferenceTransformer objectExternalReferenceTransformer = new ObjectExternalReferenceTransformer();
        for(int i = 0;i<objectExternalReferenceList.size();i++){
            ZettObjectExternalReference zettObjectExternalReference = objectExternalReferenceTransformer.transform(objectExternalReferenceList.get(i));
            arrayOfZettObjectExternalReference[i] = zettObjectExternalReference;
        }
        return arrayOfZettObjectExternalReference;
    }
    
    public List<ObjectExternalReference> transform(ZettObjectExternalReference[] arrayOfZettObjectExternalReference){
        List<ObjectExternalReference> objectExternalReferenceList = new ArrayList<ObjectExternalReference>();
        ObjectExternalReferenceTransformer objectExternalReferenceTransformer = new ObjectExternalReferenceTransformer();
        for(int i = 0;i<arrayOfZettObjectExternalReference.length;i++){
            ObjectExternalReference objectExternalReference = objectExternalReferenceTransformer.transform(arrayOfZettObjectExternalReference[i]);
            objectExternalReferenceList.add(objectExternalReference);
        }

        return objectExternalReferenceList;
    }
}
