package com.abctech.zeeland.wsObject.transform.userobject;

public class UserObjectCompanyAccessTransformer {

    public int[] transform(String companyAccessString){
        if(companyAccessString != null){
            String[] companyAccessArray = companyAccessString.split(",");
            int[] arrayOfInt = new int[companyAccessArray.length];
            for (int i = 0;i<companyAccessArray.length;i++) {
                try{
                    arrayOfInt[i] = Integer.valueOf(companyAccessArray[i]);
                }catch (NumberFormatException e){
    
                }
            }
            return arrayOfInt;
        }else{
            return new int[0];
        }
    }
    
    public String transform(int[] arrayOfInt){
        StringBuffer accessCompanyBuffer = new StringBuffer();
        if(arrayOfInt!=null){
            for(int i = 0; i < arrayOfInt.length; i++){
                accessCompanyBuffer.append(arrayOfInt[i]);
                if (i + 1 < arrayOfInt.length) {
                    accessCompanyBuffer.append(",");
                }
            }
        }

        return accessCompanyBuffer.toString();
    }
}
