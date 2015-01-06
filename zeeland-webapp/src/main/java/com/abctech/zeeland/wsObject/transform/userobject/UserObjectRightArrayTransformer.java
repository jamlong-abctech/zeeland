package com.abctech.zeeland.wsObject.transform.userobject;

import java.util.ArrayList;
import java.util.List;

public class UserObjectRightArrayTransformer {
    
    public int[] transform(String[] rightArray){
        int[] arrayOfInt = new int[rightArray.length];
        for(int i = 0;i<rightArray.length;i++){
            try{
                arrayOfInt[i] = Integer.valueOf(rightArray[i]);
            }catch (NumberFormatException err){

            }
        }
        return arrayOfInt;
    }
    
    public String[] transform(int[] arrayOfInt){
        List<String> rightArray = new ArrayList<String>();
        for(int i = 0;i<arrayOfInt.length;i++){
            rightArray.add(Integer.toString(arrayOfInt[i]));
        }
        String[] s = new String[arrayOfInt.length];
        return rightArray.toArray(s);
    }
}
