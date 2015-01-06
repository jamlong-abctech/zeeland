package com.abctech.zeeland.wsObject.transform.adobject;

import no.zett.model.base.AdObjectAdditionalCompany;
import no.zett.service.facade.ZettAdObjectAdditionalCompany;

public class AdObjectAdditionalCompanyTransformer{

    public ZettAdObjectAdditionalCompany transform(AdObjectAdditionalCompany adObjectAdditionalCompany){
        ZettAdObjectAdditionalCompany zettAdObjectAdditionalCompany = new ZettAdObjectAdditionalCompany();
        zettAdObjectAdditionalCompany.setCompanyId(adObjectAdditionalCompany.getCompany_id());
        zettAdObjectAdditionalCompany.setObjectId(adObjectAdditionalCompany.getObject_id());
        return zettAdObjectAdditionalCompany;
    }

    public AdObjectAdditionalCompany transform(ZettAdObjectAdditionalCompany zettAdObjectAdditionalCompany){
        AdObjectAdditionalCompany adObjectAdditionalCompany = new AdObjectAdditionalCompany();
        adObjectAdditionalCompany.setCompany_id(zettAdObjectAdditionalCompany.getCompanyId());
        adObjectAdditionalCompany.setObject_id(zettAdObjectAdditionalCompany.getObjectId());
        return adObjectAdditionalCompany;
    }
}
