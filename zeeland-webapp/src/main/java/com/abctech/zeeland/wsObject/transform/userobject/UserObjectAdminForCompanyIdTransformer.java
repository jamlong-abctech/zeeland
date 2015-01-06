package com.abctech.zeeland.wsObject.transform.userobject;

import java.util.ArrayList;
import java.util.List;

public class UserObjectAdminForCompanyIdTransformer {

    public int[] transform(List<Integer> companyIdList){
        int[] arrayOfInt = new int[companyIdList.size()];
        for(int i = 0;i<companyIdList.size();i++){
            arrayOfInt[i] = companyIdList.get(i);
        }
        return  arrayOfInt;
    }

    public List<Integer> transform(int[] arrayOfInt){
        List<Integer> companyIdList = new ArrayList<Integer>();
        if(arrayOfInt!=null){
            for(int i = 0;i<arrayOfInt.length;i++){
                companyIdList.add(arrayOfInt[i]);
            }
        }
        return companyIdList;
    }

}
