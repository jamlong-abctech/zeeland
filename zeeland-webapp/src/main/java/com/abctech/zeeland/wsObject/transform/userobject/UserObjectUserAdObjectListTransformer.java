package com.abctech.zeeland.wsObject.transform.userobject;

import no.zett.model.base.UserAdObject;
import no.zett.service.facade.ZettUserAdObject;

import java.util.ArrayList;
import java.util.List;

public class UserObjectUserAdObjectListTransformer {

    public ZettUserAdObject[] transform(List<UserAdObject> userAdObjectList){
        ZettUserAdObject[] arrayOfZettUserAdObject = new ZettUserAdObject[userAdObjectList.size()];
        UserObjectUserAdObjectTransformer userObjectUserAdObjectTransformer = new UserObjectUserAdObjectTransformer();
        for(int i = 0;i<userAdObjectList.size();i++){
            ZettUserAdObject zettUserAdObject = userObjectUserAdObjectTransformer.transform(userAdObjectList.get(i));
            arrayOfZettUserAdObject[i] = zettUserAdObject;
        }
        return arrayOfZettUserAdObject;
    }
    
    public List<UserAdObject> transform(ZettUserAdObject[] arrayOfZettUserAdObject){
        List<UserAdObject> userAdObjectList = new ArrayList<UserAdObject>();
        if(arrayOfZettUserAdObject!=null){
            UserObjectUserAdObjectTransformer userObjectUserAdObjectTransformer = new UserObjectUserAdObjectTransformer();
            for(int i = 0;i<arrayOfZettUserAdObject.length;i++){
                UserAdObject userAdObject = userObjectUserAdObjectTransformer.transform(arrayOfZettUserAdObject[i]);
                userAdObjectList.add(userAdObject);
            }
        }
        return userAdObjectList;
    }
}
