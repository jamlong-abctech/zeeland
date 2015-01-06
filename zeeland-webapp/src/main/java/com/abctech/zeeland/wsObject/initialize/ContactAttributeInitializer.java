package com.abctech.zeeland.wsObject.initialize;

import no.zett.model.base.ContactAttribute;

public class ContactAttributeInitializer {

    public ContactAttribute initialize(int contactAttributeType){
        ContactAttribute contactAttribute = new ContactAttribute();
        contactAttribute.setAttributeId(null);
        contactAttribute.setSection("DEFAULT");
        contactAttribute.setTypeValue(contactAttributeType);
        contactAttribute.setValue("");
        return contactAttribute;
    }
}
