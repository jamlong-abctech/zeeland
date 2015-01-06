package com.abctech.zeeland.form.data;

import no.zett.service.facade.ZettAdBookingOrderItem;

import java.util.Date;
import java.util.List;


public class BookingOrderDetail implements Comparable <BookingOrderDetail>{
    private String orderDate ;
    private Integer orderId ;
    private float totalCost  ;
    private List<BookingOrderItems> bookingOrderItemses ;
    private String paidBy ;
    private String generatedCode;
    private Float percent;

    public List<BookingOrderItems> getBookingOrderItemses() {
        return bookingOrderItemses;
    }

    public void setBookingOrderItemses(List<BookingOrderItems> bookingOrderItemses) {
        this.bookingOrderItemses = bookingOrderItemses;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    private String currencyType   ;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public String getGeneratedCode() {
        return generatedCode;
    }

    public void setGeneratedCode(String generatedCode) {
        this.generatedCode = generatedCode;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    @Override
    public int compareTo(BookingOrderDetail o) {

        if(this.equals(o)) {
            return 0;
        }
        return orderId.compareTo(o.getOrderId());
    }
}
