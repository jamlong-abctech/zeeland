package com.abctech.zeeland.wsObject.transform.userobject;

import com.abctech.zeeland.form.data.ExtendedUserAdObject;
import no.zett.model.base.UserAdObject;
import no.zett.service.facade.ZettUserAdObject;

public class UserObjectExtendedAdObjectTransformer {



    public ZettUserAdObject transform(ExtendedUserAdObject extendedUserAdObject){
        UserObjectUserAdObjectTransformer userObjectUserAdObjectTransformer = new UserObjectUserAdObjectTransformer();
        ZettUserAdObject zettUserAdObject = userObjectUserAdObjectTransformer.transform(extendedUserAdObject);
        return zettUserAdObject;
    }

    public ExtendedUserAdObject transform(ZettUserAdObject zettUserAdObject){
        UserObjectUserAdObjectTransformer userObjectUserAdObjectTransformer = new UserObjectUserAdObjectTransformer();
        UserAdObject userAdObject = userObjectUserAdObjectTransformer.transform(zettUserAdObject);
        ExtendedUserAdObject extendedUserAdObject = new ExtendedUserAdObject();
        extendedUserAdObject.setStatus(userAdObject.getStatus());
        if(userAdObject.getStatus()!=null){
            extendedUserAdObject.setStatusValue(userAdObject.getStatus().toInteger());
        }

        extendedUserAdObject.setAdObjectId(userAdObject.getAdObjectId());
        extendedUserAdObject.setBookingId(userAdObject.getBookingId());
        extendedUserAdObject.setBookingStatus(userAdObject.getBookingStatus());
        extendedUserAdObject.setEditUrl(userAdObject.getEditUrl());
        extendedUserAdObject.setObjectId(userAdObject.getObjectId());
        extendedUserAdObject.setPubFromTime(userAdObject.getPubFromTime());
        extendedUserAdObject.setPubToTime(userAdObject.getPubToTime());
        extendedUserAdObject.setTitle(userAdObject.getTitle());
        extendedUserAdObject.setType(userAdObject.getType());
        extendedUserAdObject.setUser(userAdObject.getUser());
        return extendedUserAdObject;
    }


}
