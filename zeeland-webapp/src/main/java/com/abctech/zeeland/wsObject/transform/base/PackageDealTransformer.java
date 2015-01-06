package com.abctech.zeeland.wsObject.transform.base;

import no.zett.model.PackageDeal;
import no.zett.model.enums.PackageDealType;
import no.zett.model.enums.PackageFeatureType;
import no.zett.service.facade.ZettPackageDeal;

import java.util.ArrayList;

public class PackageDealTransformer{

    public ZettPackageDeal transform(PackageDeal packageDeal){
        ZettPackageDeal zettPackageDeal = new ZettPackageDeal();
        zettPackageDeal.setPackageDealtype(packageDeal.getPackageDealType().toInteger());
        int[] arrayOfInt = new int[packageDeal.getPackagefeatures().size()];
        for(int i = 0;i<packageDeal.getPackagefeatures().size();i++) {
            //arrayOfInt[i] = packageDeal.getPackagefeatures().get(i).toInteger();
            zettPackageDeal.getPackageFeatures().add(packageDeal.getPackagefeatures().get(i).toInteger());
        }
        //zettPackageDeal.setPackageFeatures(arrayOfInt);

        return zettPackageDeal;
    }

    public PackageDeal transform(ZettPackageDeal zettPackageDeal){
        PackageDeal packageDeal = new PackageDeal();
        packageDeal.setPackageDealType(PackageDealType.fromInteger(zettPackageDeal.getPackageDealtype()));
        if(zettPackageDeal.getPackageFeatures()!=null && zettPackageDeal.getPackageFeatures()!=null){
            ArrayList<PackageFeatureType> packageFeatureTypeList = new ArrayList<PackageFeatureType>();
            for(int feature :zettPackageDeal.getPackageFeatures()){
                packageFeatureTypeList.add(PackageFeatureType.fromInteger(feature));
            }
            packageDeal.setPackagefeatures(packageFeatureTypeList);
        }
        return packageDeal;
    }

}
