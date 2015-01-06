package com.abctech.zeeland.wsObject.transform.base;

import no.zett.model.base.ContactAttribute;
import no.zett.model.enums.ContactAttributeType;
import no.zett.service.facade.ZettContactAttribute;

public class ContactAttributeTransformer{

    public ZettContactAttribute transform(ContactAttribute contactAttribute){
        ZettContactAttribute zettContactAttribute = new ZettContactAttribute();
        if(contactAttribute.getAttributeId()!=null){
            zettContactAttribute.setAttributeId(contactAttribute.getAttributeId());
        }
        if(contactAttribute.getOrder()!=null){
            zettContactAttribute.setOrder(contactAttribute.getOrder());
        }
        zettContactAttribute.setSection(contactAttribute.getSection());
        if(contactAttribute.getTypeValue() != null) {
            zettContactAttribute.setType(contactAttribute.getTypeValue());
        } else {
            zettContactAttribute.setType(0);
        }
        zettContactAttribute.setValue(contactAttribute.getValue());
        return  zettContactAttribute;
    }

    public ContactAttribute transform(ZettContactAttribute zettContactAttribute){
        ContactAttribute contactAttribute = new ContactAttribute();
        contactAttribute.setAttributeId(zettContactAttribute.getAttributeId());
        contactAttribute.setOrder(zettContactAttribute.getOrder());
        if(zettContactAttribute.getSection()!=null){
            contactAttribute.setSection(zettContactAttribute.getSection());
        }

        contactAttribute.setType(ContactAttributeType.fromInteger(zettContactAttribute.getType()));

        contactAttribute.setTypeValue(zettContactAttribute.getType());
        if(zettContactAttribute.getValue()!=null){
            contactAttribute.setValue(zettContactAttribute.getValue());
        }
        return contactAttribute;
    }
}
