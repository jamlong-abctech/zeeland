package com.abctech.zeeland.wsObject.transform.userobject;

import com.abctech.zeeland.form.util.XMLGregorianCalendarGenerator;
import com.abctech.zeeland.wsObject.transform.base.AddressTransformer;
import com.abctech.zeeland.wsObject.transform.companyobject.CompanyObjectTransformer;
import no.zett.crypto.MD5Generator;
import no.zett.model.CompanyObject;
import no.zett.model.base.Address;
import no.zett.model.base.User;
import no.zett.model.base.UserAccess;
import no.zett.model.base.UserAdObject;
import no.zett.model.enums.UserType;
import no.zett.service.facade.UserFraudStatus;
import no.zett.service.facade.ZettAddress;
import no.zett.service.facade.ZettCompanyObject;
import no.zett.service.facade.ZettUser;
import no.zett.service.facade.ZettUserAccess;
import no.zett.service.facade.ZettUserAdObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserObjectTransformer {

    private final static Logger log = LoggerFactory.getLogger(UserObjectTransformer.class);

    private UserFraudStatus convertFraudStatus(String userFraudStatus){
        log.debug(" ---------- "+userFraudStatus);
        if(userFraudStatus.equalsIgnoreCase("Ikke satt")){
            return UserFraudStatus.NOT_SET;
        }else if(userFraudStatus.equalsIgnoreCase("Svartlistet")){
            return UserFraudStatus.BLACKLISTED;
        }else if(userFraudStatus.equalsIgnoreCase("Hvitlistet")){
            return UserFraudStatus.WHITELISTED;
        }else{
            return UserFraudStatus.NOT_SET;
        }
    }
    
    public ZettUser transform(User user){
        ZettUser zettUser = new ZettUser();
        if(user.getUserId()!=null){
            zettUser.setUserId(user.getUserId());
        }
        zettUser.setName(user.getName());
        zettUser.setEmail(user.getEmail());
        zettUser.setPassword(MD5Generator.encrypt(user.getPassword()));
        zettUser.setPhone(user.getPhone());
        zettUser.setFax(user.getFax());
        zettUser.setMobile(user.getMobile());
        zettUser.setFraudstatus(convertFraudStatus(user.getFraudstatus().toTextValue()));
        zettUser.setStatus(user.getStatusValue());
        zettUser.setTindeReference(user.getTindeReference());

        if(user.getUserType()!=null){
            zettUser.setUserType(user.getUserType().toInteger());
        }

        XMLGregorianCalendarGenerator xmlGregorianCalendarGenerator = new XMLGregorianCalendarGenerator();
        
        if(user.getRegisteredTime()!=null){
            zettUser.setRegisteredTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar(user.getRegisteredTime()));
        }

        AddressTransformer addressTransformer = new AddressTransformer();
        if(user.getAddress() != null){
            ZettAddress zettAddress = addressTransformer.transform(user.getAddress());
            zettUser.setAddress(zettAddress);
        }

        if(user.getCompany()!=null){
            CompanyObjectTransformer companyObjectTransformer = new CompanyObjectTransformer();
            ZettCompanyObject zettCompanyObject = companyObjectTransformer.transform(user.getCompany());
            zettUser.setCompany(zettCompanyObject);
        }

        if(user.getUserAccess()!=null){
            UserObjectUserAccessTransformer userObjectUserAccessTransformer = new UserObjectUserAccessTransformer();
            ZettUserAccess zettUserAccess = userObjectUserAccessTransformer.transform(user.getUserAccess());
            zettUser.setUseraccess(zettUserAccess);
        }


        if(user.getUserAds()!=null){
            UserObjectUserAdObjectListTransformer userObjectUserAdObjectListTransformer = new UserObjectUserAdObjectListTransformer();
            ZettUserAdObject[] arrayOfZettUserAdObject = userObjectUserAdObjectListTransformer.transform(user.getUserAds());
            for(ZettUserAdObject zettUserAdObject:arrayOfZettUserAdObject){
                zettUser.getUserAds().add(zettUserAdObject);
            }
        }


        UserObjectAdminForCompanyIdTransformer userObjectAdminForCompanyIdTransformer = new UserObjectAdminForCompanyIdTransformer();
        int[] arrayOfInt = userObjectAdminForCompanyIdTransformer.transform(user.getAdminForCompanyId());
        //zettUser.setAdminforcompanyids(arrayOfInt);
        if(arrayOfInt.length>0){
            for(int company:arrayOfInt){
                zettUser.getAdminforcompanyids().add(company);
            }
        }

        UserObjectUserAdObjectListTransformer userObjectUserAdObjectListTransformer = new UserObjectUserAdObjectListTransformer();
        ZettUserAdObject[] arrayOfZettUserAdObject = userObjectUserAdObjectListTransformer.transform(user.getUserAds());
        if(arrayOfZettUserAdObject.length > 0){
            //zettUser.setUserAds(arrayOfZettUserAdObject);
            for(ZettUserAdObject zettUserAdObject:arrayOfZettUserAdObject){
                zettUser.getUserAds().add(zettUserAdObject);
            }
        }

        return zettUser;
    }
    
    public User transform(ZettUser zettUser){
        User user = new User();
        user.setUserId(zettUser.getUserId());
        if(zettUser.getName()!=null){
            user.setName(zettUser.getName());
        }
        user.setUserType(UserType.fromInteger(zettUser.getUserType()));

        if(zettUser.getEmail()!=null){
            user.setEmail(zettUser.getEmail());
        }
        if(zettUser.getPhone()!=null){
            user.setPhone(zettUser.getPhone());
        }
        if(zettUser.getFax()!=null){
            user.setFax(zettUser.getFax());
        }
        if(zettUser.getMobile()!=null){
            user.setMobile(zettUser.getMobile());
        }


        if(zettUser.getFraudstatus()!=null){
            log.debug("***************** zett fraud  name = "+zettUser.getFraudstatus().name() +" value = "+zettUser.getFraudstatus().value());
            //user.setFraudstatus(convertFraudStatus(zettUser.getFraudstatus().name()));

        }

        user.setStatusValue(zettUser.getStatus());
        if(zettUser.getTindeReference()!=null){
            user.setTindeReference(zettUser.getTindeReference());
        }

        if(zettUser.getRegisteredTime()!=null){
            user.setRegisteredTime(zettUser.getRegisteredTime().toGregorianCalendar().getTime());
        }

        if(zettUser.getAddress()!=null){
            AddressTransformer addressTransformer = new AddressTransformer();
            Address address = addressTransformer.transform(zettUser.getAddress());
            user.setAddress(address);
        }

        if(zettUser.getCompany()!=null && zettUser.getCompany() != null){
            CompanyObjectTransformer companyObjectTransformer = new CompanyObjectTransformer();
            CompanyObject companyObject = companyObjectTransformer.transform(zettUser.getCompany());
            user.setCompany(companyObject);
        }

        if(zettUser.getUseraccess()!=null){
            UserObjectUserAccessTransformer userObjectUserAccessTransformer = new UserObjectUserAccessTransformer();
            UserAccess userAccess = userObjectUserAccessTransformer.transform(zettUser.getUseraccess());
            user.setUserAccess(userAccess);
        }

        if(zettUser.getUserAds()!=null){
            UserObjectUserAdObjectListTransformer userObjectUserAdObjectListTransformer = new UserObjectUserAdObjectListTransformer();
            ZettUserAdObject[] zettUserAdObjects = new ZettUserAdObject[zettUser.getUserAds().size()];
            List<UserAdObject> userAdObjectList = userObjectUserAdObjectListTransformer.transform(zettUser.getUserAds().toArray(zettUserAdObjects));
            user.setUserAds(userAdObjectList);
        }

        if(zettUser.getAdminforcompanyids()!=null){
            UserObjectAdminForCompanyIdTransformer userObjectAdminForCompanyIdTransformer = new UserObjectAdminForCompanyIdTransformer();
            int[] arrayOfCompany = new int[zettUser.getAdminforcompanyids().size()];
            for(int i = 0;i<zettUser.getAdminforcompanyids().size();i++){
                arrayOfCompany[i] = zettUser.getAdminforcompanyids().get(i);
            }
            List<Integer> companyIdList = userObjectAdminForCompanyIdTransformer.transform(arrayOfCompany);
            user.setAdminForCompanyId(companyIdList);
        }

        return user;
    }
}
