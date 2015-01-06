package com.abctech.zeeland.wsObject.transform.base;

import no.zett.model.base.Address;
import no.zett.service.facade.ZettAddress;

public class AddressTransformer{

    public ZettAddress transform(Address address){
        ZettAddress zettAddress = new ZettAddress();
        if(address.getAddressId()!=null){
            zettAddress.setAddressId(address.getAddressId());
        }
        zettAddress.setGeography(address.getGeography());
        zettAddress.setPostCode(address.getPostCode());
        zettAddress.setPostLocation(address.getPostLocation());
        zettAddress.setPrimaryAddress(address.getPrimaryAddress());
        zettAddress.setSecondaryAddress(address.getSecondaryAddress());
        return zettAddress;
    }

    public Address transform(ZettAddress zettAddress){
        Address address = new Address();
        if(zettAddress.getAddressId()>0){
            address.setAddressId(zettAddress.getAddressId());
        }
        if(zettAddress.getGeography()!=null){
            address.setGeography(zettAddress.getGeography());
        }
        if(zettAddress.getPostCode()!=null){
            address.setPostCode(zettAddress.getPostCode());
        }
        if(zettAddress.getPostLocation()!=null){
            address.setPostLocation(zettAddress.getPostLocation());
        }
        if(zettAddress.getPrimaryAddress()!=null){
            address.setPrimaryAddress(zettAddress.getPrimaryAddress());
        }
        if(zettAddress.getSecondaryAddress()!=null){
            address.setSecondaryAddress(zettAddress.getSecondaryAddress());
        }
        return address;
    }
}
