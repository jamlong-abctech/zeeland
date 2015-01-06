package com.abctech.zeeland.wsObject.transform.userobject;

import com.abctech.zeeland.form.data.ExtendedUserAdObject;
import no.zett.service.facade.ZettUserAdObject;

import java.util.ArrayList;
import java.util.List;

public class UserObjectExtendedAdObjectListTransformer {

    public ZettUserAdObject[] transform(List<ExtendedUserAdObject> extendedUserAdObjectList){
        ZettUserAdObject[] arrayOfZettUserAdObject = new ZettUserAdObject[extendedUserAdObjectList.size()];
        UserObjectExtendedAdObjectTransformer userObjectExtendedAdObjectTransformer = new UserObjectExtendedAdObjectTransformer();
        for(int i=0;i<extendedUserAdObjectList.size();i++){
            ZettUserAdObject zettUserAdObject = userObjectExtendedAdObjectTransformer.transform(extendedUserAdObjectList.get(i));
            arrayOfZettUserAdObject[i] = zettUserAdObject;
        }
        return arrayOfZettUserAdObject;
    }
    
    public List<ExtendedUserAdObject> transform(ZettUserAdObject[] arrayOfZettUserAdObject){
        List<ExtendedUserAdObject> extendedUserAdObjectList = new ArrayList<ExtendedUserAdObject>();
        if(arrayOfZettUserAdObject!=null){
            UserObjectExtendedAdObjectTransformer userObjectExtendedAdObjectTransformer = new UserObjectExtendedAdObjectTransformer();
            for(int i=0;i<arrayOfZettUserAdObject.length;i++){
                ExtendedUserAdObject extendedUserAdObject = userObjectExtendedAdObjectTransformer.transform(arrayOfZettUserAdObject[i]);
                extendedUserAdObjectList.add(extendedUserAdObject);
            }
        }
        return extendedUserAdObjectList;
    }
}
