package com.abctech.zeeland.wsObject.transform.userobject;

import no.zett.model.base.UserAdObject;
import no.zett.model.enums.ObjectStatus;
import no.zett.service.facade.ZettUserAdObject;

public class UserObjectUserAdObjectTransformer {

    public ZettUserAdObject transform(UserAdObject userAdObject){
        ZettUserAdObject zettUserAdObject = new ZettUserAdObject();
        zettUserAdObject.setAdObjectId(userAdObject.getAdObjectId());
        zettUserAdObject.setEditUrl(userAdObject.getEditUrl());
        zettUserAdObject.setObjectId(zettUserAdObject.getObjectId());
        zettUserAdObject.setTitle(userAdObject.getTitle());
        zettUserAdObject.setStatus(userAdObject.getStatus().toInteger());
        return zettUserAdObject;
    }

    public UserAdObject transform(ZettUserAdObject zettUserAdObject){
        UserAdObject userAdObject = new UserAdObject();
        userAdObject.setAdObjectId(zettUserAdObject.getAdObjectId());
        if(zettUserAdObject.getEditUrl()!=null){
            userAdObject.setEditUrl(zettUserAdObject.getEditUrl());
        }
        userAdObject.setObjectId(zettUserAdObject.getObjectId());
        if(zettUserAdObject.getTitle()!=null){
            userAdObject.setTitle(zettUserAdObject.getTitle());
        }

       userAdObject.setStatus(ObjectStatus.fromInteger(zettUserAdObject.getStatus()));
        return userAdObject;
    }
}
