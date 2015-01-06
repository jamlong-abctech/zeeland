package com.abctech.zeeland.wsObject.transform.base;

import no.zett.model.base.ObjectMedia;
import no.zett.service.facade.ZettObjectMedia;

import java.util.ArrayList;
import java.util.List;

public class ObjectMediaListTransformer{

    public static ZettObjectMedia[] transform(List<ObjectMedia> objectMediaList){
        ZettObjectMedia[] arrayOfZettObjectMedia = new ZettObjectMedia[objectMediaList.size()];
        ObjectMediaTransformer objectMediaTransformer = new ObjectMediaTransformer();
        for(int i = 0;i<objectMediaList.size();i++){
            ZettObjectMedia zettObjectMedia = objectMediaTransformer.transform(objectMediaList.get(i));
            arrayOfZettObjectMedia[i] = zettObjectMedia;
        }

        return arrayOfZettObjectMedia;
    }
    
    public static List<ObjectMedia> transform(ZettObjectMedia[] arrayOfZettObjectMedia){
        List<ObjectMedia> objectMediaList = new ArrayList<ObjectMedia>();

        ObjectMediaTransformer objectMediaTransformer = new ObjectMediaTransformer();
        for(int i = 0;i<arrayOfZettObjectMedia.length;i++){
            ObjectMedia objectMedia = objectMediaTransformer.transform(arrayOfZettObjectMedia[i]);
            objectMediaList.add(objectMedia);
        }

        return objectMediaList;
    }
}
