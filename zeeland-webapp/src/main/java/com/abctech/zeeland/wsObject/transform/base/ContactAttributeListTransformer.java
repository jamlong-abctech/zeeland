package com.abctech.zeeland.wsObject.transform.base;

import no.zett.model.base.ContactAttribute;
import no.zett.service.facade.ZettContactAttribute;

import java.util.ArrayList;
import java.util.List;

public class ContactAttributeListTransformer{

//    private final static Logger log = LoggerFactory.getLogger( ContactAttributeListTransformer.class);

    public ZettContactAttribute[] transform(List<ContactAttribute> contactAttributeList){
        ZettContactAttribute[] arrayOfZettContactAttribute = new ZettContactAttribute[contactAttributeList.size()];
        ContactAttributeTransformer contactAttributeTransformer = new ContactAttributeTransformer();

        if(contactAttributeList.size()>0){
            for(int i = 0;i<contactAttributeList.size();i++){
                ZettContactAttribute zettContactAttribute = contactAttributeTransformer.transform(contactAttributeList.get(i));
                arrayOfZettContactAttribute[i] = zettContactAttribute;
            }
        }
        return arrayOfZettContactAttribute;
    }
    
    public List<ContactAttribute> transform(ZettContactAttribute[] arrayOfZettContactAttribute){
        List<ContactAttribute> contactAttributeList = new ArrayList<ContactAttribute>();
        ContactAttributeTransformer contactAttributeTransformer = new ContactAttributeTransformer();
        for(int i = 0;i<arrayOfZettContactAttribute.length;i++){
            ContactAttribute contactAttribute = contactAttributeTransformer.transform(arrayOfZettContactAttribute[i]);
            contactAttributeList.add(contactAttribute);
        }
        
        return contactAttributeList;
    }
}
