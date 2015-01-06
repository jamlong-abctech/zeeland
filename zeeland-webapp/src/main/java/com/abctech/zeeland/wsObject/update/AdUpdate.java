package com.abctech.zeeland.wsObject.update;

import com.abctech.zeeland.form.data.ExtendedAdObject;
import com.abctech.zeeland.form.util.XMLGregorianCalendarGenerator;
import com.abctech.zeeland.wsAuthentication.WebserviceAuthentication;
import no.zett.service.facade.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class AdUpdate{

    private final static Logger log = LoggerFactory.getLogger(AdUpdate.class);

    @Resource(name = "webserviceAuthentication")
    private WebserviceAuthentication webserviceAuthentication;

    @Autowired
    private AdService adServicePortType;

    @Autowired
    private CategoryService categoryServicePortType;

    public ZettAdObject update(ExtendedAdObject extendedAdObject) throws ServiceException_Exception, ParseException {
        if(extendedAdObject.getObjectId() != null && extendedAdObject.getObjectId() > 0){
            webserviceAuthentication.authentication(adServicePortType);
            AdObjectResponse adObjectResponse = adServicePortType.loadObject(extendedAdObject.getObjectId());
            if(adObjectResponse.getZettAdObject()!= null ){
                XMLGregorianCalendarGenerator xmlGregorianCalendarGenerator = new XMLGregorianCalendarGenerator();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                ZettAdObject zettAdObject = adObjectResponse.getZettAdObject();
                zettAdObject.setTitle(extendedAdObject.getTitle());
                if(extendedAdObject.getTransactionStatus() != null ){
                    zettAdObject.setTransactionStatus(extendedAdObject.getTransactionStatus().toInteger());
                }
                zettAdObject.setTransactionType(extendedAdObject.getTransactionType().toInteger());
                Date date = new Date();
                zettAdObject.setSystemModifiedTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar(date));
                zettAdObject.setModifiedTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar(date));
                if(extendedAdObject.getPublishingStatus().name().equalsIgnoreCase("SOLD")){
                    zettAdObject.setPublishingStatus(extendedAdObject.getPublishingStatus().toInteger());
                    zettAdObject.setStatus(extendedAdObject.getStatus().toInteger());
                    date = dateFormat.parse(extendedAdObject.getPublishToTimeString());
                    zettAdObject.setPublishToTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar(date));
                    
                    
                } else {
                    zettAdObject.setPublishingStatus(extendedAdObject.getPublishingStatus().toInteger());
                    zettAdObject.setStatus(extendedAdObject.getStatus().toInteger());
                    if(extendedAdObject.getPublishToTimeString()!=null && !extendedAdObject.getPublishToTimeString().isEmpty()){
                        date = dateFormat.parse(extendedAdObject.getPublishToTimeString());
                        zettAdObject.setPublishToTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar(date));
                    }
                }
                zettAdObject.setModifiedBy("zeeland");
                if(extendedAdObject.getCategoryId()!=null){
                    zettAdObject.setCategoryId(extendedAdObject.getCategoryId());
                }


                if(extendedAdObject.getPublishFromTimeString()!=null && !extendedAdObject.getPublishFromTimeString().isEmpty()){
                    date = dateFormat.parse(extendedAdObject.getPublishFromTimeString());
                    zettAdObject.setPublishFromTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar(date));
                }

                //update address
                AddressUpdate addressUpdate = new AddressUpdate();
                if(zettAdObject.getAddress()!=null){
                    ZettAddress zettAddress = addressUpdate.update(zettAdObject.getAddress(),extendedAdObject.getAddress()) ;
                    zettAdObject.setAddress(zettAddress);
                }

                //update attribute
                ObjectAttributeListUpdate objectAttributeListUpdate = new ObjectAttributeListUpdate();
                if(zettAdObject.getAttributes()!=null){
                    ZettObjectAttribute[] zettObjectAttributes = new ZettObjectAttribute[zettAdObject.getAttributes().size()];
                    ZettObjectAttribute[] arrayOfZettObjectAttribute = objectAttributeListUpdate.update(zettAdObject.getAttributes().toArray(zettObjectAttributes),extendedAdObject.getAttributes(),extendedAdObject.getExtendedAttribute());

                    zettAdObject.getAttributes().clear();
                    for(ZettObjectAttribute zettObjectAttribute:arrayOfZettObjectAttribute){
                        //zettAdObject.setAttributes(arrayOfZettObjectAttribute);
                        if(zettObjectAttribute.getValue() != null && !zettObjectAttribute.getValue().isEmpty()){
                            zettAdObject.getAttributes().add(zettObjectAttribute);
                        }
                    }
                }

                //update contact
                ContactListUpdate contactListUpdate = new ContactListUpdate();
                if(zettAdObject.getContacts()!=null){
                    ZettContact[] zettContacts = new ZettContact[zettAdObject.getContacts().size()];
                    ZettContact[] arrayOfZettContact = contactListUpdate.update(zettAdObject.getContacts().toArray(zettContacts),extendedAdObject.getContacts(),null);
                    //zettAdObject.setContacts(arrayOfZettContact);

                    zettAdObject.getContacts().clear();
                    for(ZettContact zettContact:arrayOfZettContact){
                        zettAdObject.getContacts().add(zettContact);
                    }

                }
                if (extendedAdObject.getAdObjectCategory() != null){
                //update category
                    if (extendedAdObject.getAdObjectCategory().getCategoryId() != null) {
                        webserviceAuthentication.authentication(categoryServicePortType);
                        CategoryResponse categoryResponse = categoryServicePortType.getCategoryById(extendedAdObject.getAdObjectCategory().getCategoryId());
                        if(categoryResponse.getZettcategory()!=null){
                            ZettAdObjectCategory zettAdObjectCategory = categoryResponse.getZettcategory();
                            zettAdObject.setAdObjectcategory(zettAdObjectCategory);
                            zettAdObject.setCategoryId(zettAdObjectCategory.getCategoryId());
                            zettAdObject.setCategory(zettAdObjectCategory.getFullname());
                        }
                    }
                }
                //update all ad data
                AdObjectResponse newAdObjectResponse = adServicePortType.saveObject(zettAdObject);
                zettAdObject = newAdObjectResponse.getZettAdObject();
                return  zettAdObject;
            }
        }

        return null;
    }

    private ZettAdObject setMainMedia(ZettAdObject zettAdObject,int mediaId){
        log.debug("set new main media.");
        if(zettAdObject.getMedia()!=null){
            boolean foundStatus = false;
            for(ZettObjectMedia zettObjectMedia:zettAdObject.getMedia()){
                if(zettObjectMedia.getMediaId() == (mediaId)){
                    zettObjectMedia.setOrder(1);
                    foundStatus = true;
                    log.debug("found media in media list.");
                }
            }

            if(foundStatus){
                for(int i = 0;i<zettAdObject.getMedia().size();i++){
                    if(zettAdObject.getMedia().get(i).getMediaId() != mediaId){
                        zettAdObject.getMedia().get(i).setOrder(i+2);
                    }
                }
            }
        }
        return zettAdObject;
    }

    private ZettAdObject deleteMedia(ZettAdObject zettAdObject,int mediaId){
        log.debug("delete media.");
        if(zettAdObject.getMedia() != null ){
            ZettObjectMedia removeObject = new ZettObjectMedia();
            for(ZettObjectMedia zettObjectMedia:zettAdObject.getMedia()){
                if(zettObjectMedia.getMediaId() == (mediaId)){
                    removeObject = zettObjectMedia;
                    break;
//                    log.debug("found media in media list.");
                }
            }
            zettAdObject.getMedia().remove(removeObject);
        }
        return zettAdObject;
    }

    public ZettAdObject setMainMedia(int adId,int newMainMediaId) throws ServiceException_Exception{
        if(adId > 0 && newMainMediaId > 0){
            webserviceAuthentication.authentication(adServicePortType);
            AdObjectResponse adObjectResponse = adServicePortType.loadObject(adId);
            ZettAdObject zettAdObject = adObjectResponse.getZettAdObject();
            zettAdObject = setMainMedia(zettAdObject,newMainMediaId);

            //update all ad data
            AdObjectResponse newAdObjectResponse = adServicePortType.saveObject(zettAdObject);
            zettAdObject = newAdObjectResponse.getZettAdObject();
            return  zettAdObject;

        }
        return null;
    }

    public ZettAdObject deleteMedia(int adId,int mediaId) throws ServiceException_Exception{
        if(adId > 0){
            webserviceAuthentication.authentication(adServicePortType);
            AdObjectResponse adObjectResponse = adServicePortType.loadObject(adId);
            ZettAdObject zettAdObject = adObjectResponse.getZettAdObject();
            zettAdObject = deleteMedia(zettAdObject, mediaId);

            //update all ad data
            AdObjectResponse newAdObjectResponse = adServicePortType.saveObject(zettAdObject);
            zettAdObject = newAdObjectResponse.getZettAdObject();
            return  zettAdObject;

        }
        return null;
    }
}
