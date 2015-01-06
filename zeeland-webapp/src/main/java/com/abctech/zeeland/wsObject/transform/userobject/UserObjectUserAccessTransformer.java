package com.abctech.zeeland.wsObject.transform.userobject;

import no.zett.model.base.UserAccess;
import no.zett.model.enums.UserAccessRights;
import no.zett.service.facade.ZettUserAccess;

import java.util.ArrayList;

public class UserObjectUserAccessTransformer {

    public ZettUserAccess transform(UserAccess userAccess){
        ZettUserAccess zettUserAccess = new ZettUserAccess();
        UserObjectUserAccessRightsTransformer userObjectUserAccessRightsTransformer = new UserObjectUserAccessRightsTransformer();
        int[] arrayOfInt = userObjectUserAccessRightsTransformer.transform(userAccess.getUserAccessRights());
        for(int right:arrayOfInt){
            zettUserAccess.getUseraccessrights().add(right);
        }

        return zettUserAccess;
    }
    
    public UserAccess transform(ZettUserAccess zettUserAccess){
        UserAccess userAccess = new UserAccess();
        if(zettUserAccess.getUseraccessrights()!=null){
            UserObjectUserAccessRightsTransformer userObjectUserAccessRightsTransformer = new UserObjectUserAccessRightsTransformer();
            int[] arrayOfRight = new int[zettUserAccess.getUseraccessrights().size()];
            for(int i = 0;i<zettUserAccess.getUseraccessrights().size();i++){
                arrayOfRight[i] = zettUserAccess.getUseraccessrights().get(i);
            }
            ArrayList<UserAccessRights> userAccessRightsList = userObjectUserAccessRightsTransformer.transform(arrayOfRight);
            userAccess.setUserAccessRights(userAccessRightsList);
        }
        return userAccess;
    }
}
