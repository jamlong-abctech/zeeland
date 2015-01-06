package com.abctech.zeeland.wsObject.update;

import no.zett.model.base.Address;
import no.zett.service.facade.ZettAddress;

public class AddressUpdate {

    public ZettAddress update(ZettAddress zettAddress,Address address){
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
}
