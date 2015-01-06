package com.abctech.zeeland.wsObject.transform.adobject;

import no.zett.model.base.AdObjectAdditionalCompany;
import no.zett.service.facade.ZettAdObjectAdditionalCompany;

import java.util.ArrayList;
import java.util.List;

public class AdObjectAdditionalCompanyListTransformer {

    public ZettAdObjectAdditionalCompany[] transform(List<AdObjectAdditionalCompany> adObjectAdditionalCompanyList){
        ZettAdObjectAdditionalCompany[] arrayOfZettAdObjectAdditionalCompany = new ZettAdObjectAdditionalCompany[adObjectAdditionalCompanyList.size()];
        AdObjectAdditionalCompanyTransformer adObjectAdditionalCompanyTransformer = new AdObjectAdditionalCompanyTransformer();
        for(int i = 0;i<adObjectAdditionalCompanyList.size();i++){
            ZettAdObjectAdditionalCompany zettAdObjectAdditionalCompany = adObjectAdditionalCompanyTransformer.transform(adObjectAdditionalCompanyList.get(i));
            arrayOfZettAdObjectAdditionalCompany[i] = zettAdObjectAdditionalCompany;
        }
        return arrayOfZettAdObjectAdditionalCompany;
    }

    public List<AdObjectAdditionalCompany> transform(ZettAdObjectAdditionalCompany[] arrayOfZettAdObjectAdditionalCompany){
        List<AdObjectAdditionalCompany> adObjectAdditionalCompanyList = new ArrayList<AdObjectAdditionalCompany>();

        for(int i = 0;i<arrayOfZettAdObjectAdditionalCompany.length;i++){
            AdObjectAdditionalCompanyTransformer adObjectAdditionalCompanyTransformer = new AdObjectAdditionalCompanyTransformer();
            AdObjectAdditionalCompany adObjectAdditionalCompany = adObjectAdditionalCompanyTransformer.transform(arrayOfZettAdObjectAdditionalCompany[i]);
            adObjectAdditionalCompanyList.add(adObjectAdditionalCompany);
        }

        return adObjectAdditionalCompanyList;
    }

}
