package com.abctech.zeeland.wsObject.transform.fraudlogobject;

import com.abctech.zeeland.form.data.ExtendedFraudLogObject;
import no.zett.model.FraudLogObject;
import no.zett.service.facade.ZettFraudLogObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtendedFraudLogObjectTransformer{

    public ZettFraudLogObject transform(ExtendedFraudLogObject extendedFraudLogObject){
        FraudLogObjectTransformer fraudLogObjectTransformer = new FraudLogObjectTransformer();
        ZettFraudLogObject zettFraudLogObject = fraudLogObjectTransformer.transform(extendedFraudLogObject);
        return zettFraudLogObject;
    }

    public ExtendedFraudLogObject transform(ZettFraudLogObject zettFraudLogObject){
        ExtendedFraudLogObject extendedFraudLogObject = new ExtendedFraudLogObject();
        FraudLogObjectTransformer fraudLogObjectTransformer = new FraudLogObjectTransformer();
        FraudLogObject fraudLogObject = fraudLogObjectTransformer.transform(zettFraudLogObject);

        extendedFraudLogObject.setTitle(fraudLogObject.getTitle());
        extendedFraudLogObject.setAddedTime(fraudLogObject.getAddedTime());
        extendedFraudLogObject.setAdObjectId(fraudLogObject.getAdObjectId());
        extendedFraudLogObject.setCountry(fraudLogObject.getCountry());
        extendedFraudLogObject.setEmail(fraudLogObject.getEmail());
        extendedFraudLogObject.setFraudMessage(fraudLogObject.getFraudMessage());
        extendedFraudLogObject.setIpAddr(fraudLogObject.getIpAddr());
        extendedFraudLogObject.setLogId(fraudLogObject.getLogId());
        extendedFraudLogObject.setModifiedByIp(fraudLogObject.getModifiedByIp());
        extendedFraudLogObject.setModifiedByUser(fraudLogObject.getModifiedByUser());
        extendedFraudLogObject.setModifiedStatusTime(fraudLogObject.getModifiedStatusTime());
        extendedFraudLogObject.setStatus(fraudLogObject.getStatus());

        if(fraudLogObject.getAddedTime() != null){
            Date date = fraudLogObject.getAddedTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            extendedFraudLogObject.setAddDateString(simpleDateFormat.format(date));
        }

        return extendedFraudLogObject;
    }
}
