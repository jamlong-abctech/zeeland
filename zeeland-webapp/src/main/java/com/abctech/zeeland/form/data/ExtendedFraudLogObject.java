package com.abctech.zeeland.form.data;

import no.zett.model.FraudLogObject;

public class ExtendedFraudLogObject extends FraudLogObject{

    private String addDateString;

    public String getAddDateString() {
        return addDateString;
    }

    public void setAddDateString(String addDateString) {
        this.addDateString = addDateString;
    }
}
