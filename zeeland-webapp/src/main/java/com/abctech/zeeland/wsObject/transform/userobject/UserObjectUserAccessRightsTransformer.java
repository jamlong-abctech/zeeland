package com.abctech.zeeland.wsObject.transform.userobject;

import no.zett.model.enums.UserAccessRights;

import java.util.ArrayList;
import java.util.List;

public class UserObjectUserAccessRightsTransformer {
    
    public int[] transform(List<UserAccessRights> userAccessRightsList){
        int[] arrayOfInt = new int[userAccessRightsList.size()];
        for (int i = 0;i<userAccessRightsList.size();i++) {
            try{
                arrayOfInt[i] = Integer.valueOf(userAccessRightsList.get(i).toInteger());
            }catch (NumberFormatException err){

            }
        }
        return arrayOfInt;
    }

    public ArrayList<UserAccessRights> transform(int[] arrayOfInt){
        ArrayList<UserAccessRights> rightArray = new ArrayList<UserAccessRights>();
        for(int i = 0;i<arrayOfInt.length;i++){
            rightArray.add(UserAccessRights.fromInteger(arrayOfInt[i]));
        }

        return rightArray;
    }
}
