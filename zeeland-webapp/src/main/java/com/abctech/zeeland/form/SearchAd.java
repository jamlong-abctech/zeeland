package com.abctech.zeeland.form;

public class SearchAd {
    private String adId;
    private String adIds;
    private String batchAction;
    private String extRef;
    private String clientEmail;
    private String searchKeyword;
    private String printAdId;
    private String orderId ;
    private Integer statusValue = -1 ;

    // private Integer statusValue = ObjectStatus.INACTIVE.toInteger();

    public String getAdIds() {
        return adIds;
    }

    public void setAdIds(String adIds) {
        this.adIds = adIds;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getExtRef() {
        return extRef;
    }

    public void setExtRef(String extRef) {
        this.extRef = extRef;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getBatchAction() {
        return batchAction;
    }

    public void setBatchAction(String batchAction) {
        this.batchAction = batchAction;
    }

    public Integer getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(Integer statusValue) {
        this.statusValue = statusValue;
    }

    public String getPrintAdId() {
        return printAdId;
    }

    public void setPrintAdId(String printAdId) {
        this.printAdId = printAdId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
