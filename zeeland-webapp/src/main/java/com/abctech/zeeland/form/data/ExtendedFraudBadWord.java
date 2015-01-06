package com.abctech.zeeland.form.data;

import no.zett.model.FraudBadWord;

public class ExtendedFraudBadWord extends FraudBadWord{
    
    private String newBadWord;
    private String createdTime;

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getNewBadWord() {
        return newBadWord;
    }

    public void setNewBadWord(String newBadWord) {
        this.newBadWord = newBadWord;
    }
}
