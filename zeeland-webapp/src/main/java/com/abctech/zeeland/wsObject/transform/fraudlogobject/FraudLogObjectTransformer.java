package com.abctech.zeeland.wsObject.transform.fraudlogobject;

import com.abctech.zeeland.form.util.XMLGregorianCalendarGenerator;
import no.zett.model.FraudLogObject;
import no.zett.model.enums.FraudLogObjectStatus;
import no.zett.service.facade.ZettFraudLogObject;

public class FraudLogObjectTransformer {

    private String extractFraudMessage(String message){
        if(message.contains("'")){
            String[] token = message.split("\\'");
            if(token.length>=2){
                return token[1];
            }
        }
        return message;
    }
    
    public ZettFraudLogObject transform(FraudLogObject fraudLogObject){
        ZettFraudLogObject zettFraudLogObject = new ZettFraudLogObject();
        zettFraudLogObject.setTitle(fraudLogObject.getTitle());
        XMLGregorianCalendarGenerator xmlGregorianCalendarGenerator = new XMLGregorianCalendarGenerator();
        if(fraudLogObject.getAddedTime()!=null){
            zettFraudLogObject.setAddedTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar(fraudLogObject.getAddedTime()));
        }
        zettFraudLogObject.setAdObjectId(fraudLogObject.getAdObjectId());
        zettFraudLogObject.setCountry(fraudLogObject.getCountry());
        zettFraudLogObject.setEmail(fraudLogObject.getEmail());
        zettFraudLogObject.setFraudMessage(fraudLogObject.getFraudMessage());
        zettFraudLogObject.setIpAddr(fraudLogObject.getIpAddr());
        zettFraudLogObject.setLogId(fraudLogObject.getLogId());
        zettFraudLogObject.setModifiedByIp(fraudLogObject.getModifiedByIp());
        zettFraudLogObject.setModifiedByUser(fraudLogObject.getModifiedByUser());
        if(fraudLogObject.getModifiedStatusTime()!=null){
            zettFraudLogObject.setModifiedStatusTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar(fraudLogObject.getModifiedStatusTime()));
        }
        zettFraudLogObject.setFraudLogObjectStatusValue(fraudLogObject.getStatus().toInteger());
        return zettFraudLogObject;
    }

    public FraudLogObject transform(ZettFraudLogObject zettFraudLogObject){
        FraudLogObject fraudLogObject = new FraudLogObject();
        fraudLogObject.setTitle(zettFraudLogObject.getTitle());
        if(zettFraudLogObject.getAddedTime()!=null){
            fraudLogObject.setAddedTime(zettFraudLogObject.getAddedTime().toGregorianCalendar().getTime());
        }
        fraudLogObject.setAdObjectId(zettFraudLogObject.getAdObjectId());
        fraudLogObject.setCountry(zettFraudLogObject.getCountry());
        fraudLogObject.setEmail(zettFraudLogObject.getEmail());
        if(zettFraudLogObject.getFraudMessage()!=null){
            String message = extractFraudMessage(zettFraudLogObject.getFraudMessage());
            fraudLogObject.setFraudMessage(message);
        }
        fraudLogObject.setIpAddr(zettFraudLogObject.getIpAddr());
        fraudLogObject.setLogId(zettFraudLogObject.getLogId());
        fraudLogObject.setModifiedByIp(zettFraudLogObject.getModifiedByIp());
        fraudLogObject.setModifiedByUser(zettFraudLogObject.getModifiedByUser());
        if(zettFraudLogObject.getModifiedStatusTime()!=null){
            fraudLogObject.setModifiedStatusTime(zettFraudLogObject.getModifiedStatusTime().toGregorianCalendar().getTime());
        }
        fraudLogObject.setStatus(FraudLogObjectStatus.fromInteger(zettFraudLogObject.getFraudLogObjectStatusValue()));
        return fraudLogObject;
    }
}
