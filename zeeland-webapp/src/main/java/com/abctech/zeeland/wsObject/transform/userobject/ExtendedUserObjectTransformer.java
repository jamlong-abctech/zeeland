package com.abctech.zeeland.wsObject.transform.userobject;

import com.abctech.zeeland.form.data.ExtendedUser;
import com.abctech.zeeland.form.data.ExtendedUserAdObject;
import com.abctech.zeeland.wsAuthentication.WebserviceAuthentication;
import no.zett.model.base.User;
import no.zett.model.base.UserAdObject;
import no.zett.model.enums.UserAccessRights;
import no.zett.model.enums.UserFraudStatus;
import no.zett.service.facade.AdObjectResponse;
import no.zett.service.facade.AdService;
import no.zett.service.facade.ServiceException_Exception;
import no.zett.service.facade.ZettAdObject;
import no.zett.service.facade.ZettUser;
import no.zett.service.facade.ZettUserAdObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExtendedUserObjectTransformer {
    private final static Logger log = LoggerFactory.getLogger(ExtendedUserObjectTransformer.class);

    @Resource(name = "webserviceAuthentication")
    private WebserviceAuthentication webserviceAuthentication;

    @Autowired
    private AdService adServicePortType;
    
    private UserFraudStatus convert(String zettFraudStatus){
        if(zettFraudStatus.equalsIgnoreCase("NOT_SET")){
            return UserFraudStatus.NOT_SET;
        }else if(zettFraudStatus.equalsIgnoreCase("BLACKLISTED")){
            return UserFraudStatus.BLACKLISTED;
        }else if(zettFraudStatus.equalsIgnoreCase("WHITELISTED")){
            return UserFraudStatus.WHITELISTED;
        }else{
            return UserFraudStatus.NOT_SET;
        }
    }

    public ZettUser transform(ExtendedUser extendedUser){

        UserObjectTransformer userObjectTransformer = new UserObjectTransformer();
        ZettUser zettUser = userObjectTransformer.transform(extendedUser);
        zettUser.setUserType(extendedUser.getUserTypeValue());

        // set value from the extended user (form) to zett user
        UserObjectRightArrayTransformer userObjectRightArrayTransformer = new UserObjectRightArrayTransformer();
        int[] rightArray = userObjectRightArrayTransformer.transform(extendedUser.getRightArray());
        for(int right:rightArray){
            zettUser.getUseraccess().getUseraccessrights().add(right);
        }


        UserObjectCompanyAccessTransformer userObjectCompanyAccessTransformer = new UserObjectCompanyAccessTransformer();
        int[] companyAccessArray = userObjectCompanyAccessTransformer.transform(extendedUser.getCompanyAccess());
        //zettUser.setAdminforcompanyids(companyAccessArray);
        for(int company:companyAccessArray){
            zettUser.getAdminforcompanyids().add(company);
        }


        return zettUser;
    }

    public ExtendedUser transform(ZettUser zettUser){
        UserObjectTransformer userObjectTransformer = new UserObjectTransformer();
        User user = userObjectTransformer.transform(zettUser);
        ExtendedUser extendedUser = new ExtendedUser();

        extendedUser.setUserId(user.getUserId());
        extendedUser.setName(user.getName());
        extendedUser.setEmail(user.getEmail());
        extendedUser.setPhone(user.getPhone());
        extendedUser.setFax(user.getFax());
        extendedUser.setMobile(user.getMobile());
        //extendedUser.setFraudstatus(user.getFraudstatus());
        extendedUser.setFraudstatus(convert(zettUser.getFraudstatus().value()));
        extendedUser.setStatusValue(user.getStatusValue());
        extendedUser.setTindeReference(user.getTindeReference());
        extendedUser.setRegisteredTime(user.getRegisteredTime());
        extendedUser.setAddress(user.getAddress());
        extendedUser.setCompany(user.getCompany());
        extendedUser.setUserAccess(user.getUserAccess());
        if(user.getUserType()!=null){
            extendedUser.setUserTypeValue(user.getUserType().toInteger());
        }

        UserObjectUserAdObjectListTransformer userObjectUserAdObjectListTransformer = new UserObjectUserAdObjectListTransformer();
        ZettUserAdObject[] arrayOfZettUserAdObject = userObjectUserAdObjectListTransformer.transform(user.getUserAds());

        UserObjectExtendedAdObjectListTransformer userObjectExtendedAdObjectListTransformer = new UserObjectExtendedAdObjectListTransformer();
        List<ExtendedUserAdObject> extendedUserAdObjectList = userObjectExtendedAdObjectListTransformer.transform(arrayOfZettUserAdObject);

        List<UserAdObject> userAdObjectList = new ArrayList<UserAdObject>();
        for(ExtendedUserAdObject extendedUserAdObject:extendedUserAdObjectList){
            if(extendedUserAdObject.getAdObjectId()!= null && extendedUserAdObject.getAdObjectId()>0){
                try{
                    Integer publishingStatus = getExtendedUserAdPublishingStatusValue(extendedUserAdObject.getAdObjectId());
                    extendedUserAdObject.setStatusValue(publishingStatus);
                }catch (ServiceException_Exception err){
                    log.debug("error while user transformer : "+err.getMessage());
                }
            }
            userAdObjectList.add(extendedUserAdObject);
        }
        extendedUser.setUserAds(userAdObjectList);

        List<String> rightArray = new ArrayList<String>();
        if(user.getUserAccess().getUserAccessRights()!= null && user.getUserAccess().getUserAccessRights().size()>0){
            for(UserAccessRights rights:user.getUserAccess().getUserAccessRights()){
                rightArray.add(Integer.toString(rights.toInteger()));
            }
        }
        String[] s = new String[rightArray.size()];
        extendedUser.setRightArray(rightArray.toArray(s));
        
        if(user.getStatus()!=null){
            extendedUser.setStatusValue(user.getStatus().toInteger());
        }

        if(user.getAdminForCompanyId()!=null){
            //get data for zett user -> user
            UserObjectAdminForCompanyIdTransformer userObjectAdminForCompanyIdTransformer = new UserObjectAdminForCompanyIdTransformer();
            int[] arrayOfAccessCompanyId = userObjectAdminForCompanyIdTransformer.transform(user.getAdminForCompanyId());

            //convert data from user -> extended user
            UserObjectCompanyAccessTransformer userObjectCompanyAccessTransformer = new UserObjectCompanyAccessTransformer();
            String companyAccessList = userObjectCompanyAccessTransformer.transform(arrayOfAccessCompanyId);

            extendedUser.setCompanyAccess(companyAccessList);
        }

        return extendedUser;
    }
    
    private Integer getExtendedUserAdPublishingStatusValue(int adId) throws ServiceException_Exception {
        if(adId>0){
            webserviceAuthentication.authentication(adServicePortType);
            AdObjectResponse adObjectResponse = adServicePortType.loadObject(adId);
            ZettAdObject zettAdObject = adObjectResponse.getZettAdObject();
            return zettAdObject.getPublishingStatus();
        }
        return null;
    }

}
