package com.abctech.zeeland.wsObject.transform.fraudlogobject;

import com.abctech.zeeland.form.data.ExtendedFraudLogObject;
import no.zett.service.facade.ZettFraudLogObject;

import java.util.ArrayList;
import java.util.List;

public class ExtendedFraudLogObjectListTransformer {
    
    public List<ExtendedFraudLogObject> transform(ZettFraudLogObject[] zettFraudLogObjects){
        ExtendedFraudLogObjectTransformer extendedFraudLogObjectTransformer = new ExtendedFraudLogObjectTransformer();
        List<ExtendedFraudLogObject> extendedFraudLogObjectList = new ArrayList<ExtendedFraudLogObject>();
        for(ZettFraudLogObject zettFraudLogObject:zettFraudLogObjects){
            ExtendedFraudLogObject extendedFraudLogObject = extendedFraudLogObjectTransformer.transform(zettFraudLogObject);
            extendedFraudLogObjectList.add(extendedFraudLogObject);
        }
        return extendedFraudLogObjectList;
    }

    public ZettFraudLogObject[] transform(List<ExtendedFraudLogObject> extendedFraudLogObjectList){
        ExtendedFraudLogObjectTransformer extendedFraudLogObjectTransformer = new ExtendedFraudLogObjectTransformer();
        List<ZettFraudLogObject> zettFraudLogObjectList = new ArrayList<ZettFraudLogObject>();
        for(ExtendedFraudLogObject extendedFraudLogObject:extendedFraudLogObjectList){
            ZettFraudLogObject zettFraudLogObject = extendedFraudLogObjectTransformer.transform(extendedFraudLogObject);
            zettFraudLogObjectList.add(zettFraudLogObject);
        }
        ZettFraudLogObject[] zettFraudLogObjects = new ZettFraudLogObject[zettFraudLogObjectList.size()];
        return zettFraudLogObjectList.toArray(zettFraudLogObjects);
    }
}
