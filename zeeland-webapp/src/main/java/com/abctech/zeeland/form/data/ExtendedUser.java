package com.abctech.zeeland.form.data;


import no.zett.model.base.User;

import java.util.Arrays;

public class ExtendedUser extends User{

    private String[] rightArray;
    private Integer userTypeValue;
    private Integer statusValue;
    private String companyAccess;

    public String[] getRightArray() {
        return rightArray;
    }

    public void setRightArray(String[] rightArray) {
        if ( rightArray == null ) {
            this.rightArray = null;
            return;
        }
        this.rightArray = Arrays.copyOf(rightArray, rightArray.length);
    }

    public Integer getUserTypeValue() {
        return userTypeValue;
    }

    public void setUserTypeValue(Integer userTypeValue) {
        this.userTypeValue = userTypeValue;
    }

    public String getCompanyAccess() {
        return companyAccess;
    }

    public void setCompanyAccess(String companyAccess) {
        this.companyAccess = companyAccess;
    }

    public Integer getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(Integer statusValue) {
        this.statusValue = statusValue;
    }
}
