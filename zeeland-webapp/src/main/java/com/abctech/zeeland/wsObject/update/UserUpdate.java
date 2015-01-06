package com.abctech.zeeland.wsObject.update;

import com.abctech.zeeland.form.data.ExtendedUser;
import com.abctech.zeeland.form.util.XMLGregorianCalendarGenerator;
import com.abctech.zeeland.wsAuthentication.WebserviceAuthentication;
import com.abctech.zeeland.wsObject.transform.userobject.ExtendedUserObjectTransformer;
import com.abctech.zeeland.wsObject.transform.userobject.UserObjectCompanyAccessTransformer;
import com.abctech.zeeland.wsObject.transform.userobject.UserObjectRightArrayTransformer;
import no.zett.crypto.MD5Generator;
import no.zett.service.facade.CompanyObjectResponse;
import no.zett.service.facade.CompanyService;
import no.zett.service.facade.ServiceException_Exception;
import no.zett.service.facade.UserFraudStatus;
import no.zett.service.facade.UserResponse;
import no.zett.service.facade.UserService;
import no.zett.service.facade.ZettCompanyObject;
import no.zett.service.facade.ZettUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserUpdate {

    private final static Logger log = LoggerFactory.getLogger(UserUpdate.class);

    @Resource(name = "webserviceAuthentication")
    private WebserviceAuthentication webserviceAuthentication;

    @Autowired
    private CompanyService companyServicePortType;

    @Autowired
    private UserService userServicePortType;

    @Autowired
    private ExtendedUserObjectTransformer extendedUserObjectTransformer;

    private UserFraudStatus convertFraudStatus(String userFraudStatus){
        log.debug(" **************************** status = "+userFraudStatus);
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

    public ZettUser add(ExtendedUser extendedUser) throws ServiceException_Exception {
        ZettUser zettUser = extendedUserObjectTransformer.transform(extendedUser);
        XMLGregorianCalendarGenerator xmlGregorianCalendarGenerator = new XMLGregorianCalendarGenerator();
        zettUser.setRegisteredTime(xmlGregorianCalendarGenerator.generateXMLGregorianCalendar());
        webserviceAuthentication.authentication(userServicePortType);
        UserResponse userResponse = userServicePortType.saveUser(zettUser);
        zettUser = userResponse.getZettUser();
        return zettUser;
    }

    public ZettUser update(ExtendedUser extendedUser) throws ServiceException_Exception {

        if(extendedUser.getUserId()!=null && extendedUser.getUserId()>0){
            webserviceAuthentication.authentication(userServicePortType);
            UserResponse userResponse = userServicePortType.loadUser(extendedUser.getUserId());
            ZettUser zettUser = userResponse.getZettUser();
            zettUser.setName(extendedUser.getName());
            zettUser.setEmail(extendedUser.getEmail());
            zettUser.setPhone(extendedUser.getPhone());
            zettUser.setMobile(extendedUser.getMobile());
            zettUser.setFax(extendedUser.getFax());
            zettUser.setFraudstatus(convertFraudStatus(extendedUser.getFraudstatus().toTextValue()));
            zettUser.setStatus(extendedUser.getStatusValue());
            zettUser.setUserType(extendedUser.getUserTypeValue());

            if(extendedUser.getPassword()!=null && !extendedUser.getPassword().isEmpty()) {
                zettUser.setPassword(MD5Generator.encrypt(extendedUser.getPassword()));
                log.debug("Password in UserUpdate Class>>>>>>>>>>>>>" + zettUser.getPassword());

            }
            zettUser.setTindeReference(null);

            if (extendedUser.getCompany() != null && extendedUser.getCompany().getObjectId() != null) {
                try {
                    webserviceAuthentication.authentication(companyServicePortType);
                    CompanyObjectResponse companyObjectResponse = companyServicePortType.loadCompany(extendedUser.getCompany().getObjectId());
                    ZettCompanyObject zettCompanyObject = companyObjectResponse.getZettCompanyObject();
                    zettUser.setCompany(zettCompanyObject);
                } catch (ServiceException_Exception err) {
                    log.trace("found error when try to update company in user " + err.getMessage());
                }
            }


            if (extendedUser.getCompanyAccess() != null && !extendedUser.getCompanyAccess().isEmpty()) {
                //convert from "... ,..." from form to zett
                UserObjectCompanyAccessTransformer userObjectCompanyAccessTransformer = new UserObjectCompanyAccessTransformer();
                int[] companyAccessArray = userObjectCompanyAccessTransformer.transform(extendedUser.getCompanyAccess());
                zettUser.getAdminforcompanyids().clear();
                for(int company:companyAccessArray){
                    zettUser.getAdminforcompanyids().add(company);
                }
                //zettUser.setAdminforcompanyids(companyAccessArray);
            }

            if (extendedUser.getUserAccess()!=null) {
                //convert from list box to zett
                UserObjectRightArrayTransformer userObjectRightArrayTransformer = new UserObjectRightArrayTransformer();
                int[] rightArray = userObjectRightArrayTransformer.transform(extendedUser.getRightArray());
                zettUser.getUseraccess().getUseraccessrights().clear();
                for(int right:rightArray){
                    zettUser.getUseraccess().getUseraccessrights().add(right);
                }
                //zettUser.getUseraccess().setUseraccessrights(rightArray);
            }

            UserResponse newUserResponse = userServicePortType.saveUser(zettUser);
            zettUser = newUserResponse.getZettUser();

            return zettUser;
        }

        return null;
    }

        // Need to Waiting The zett DAO complete to impleted . Before impletment this functionality
    /*public ZettUser updateAdOwner(ZettUser zettUser, ZettAdObject zettAdObject) {

        Integer adId = zettAdObject.getObjectId();

      if (zettUser.getUserAds().getValue() == null ) {

            ObjectFactory objectFactory = new ObjectFactory();

            List<ZettUserAdObject> zettUserAdObjectList = new ArrayList<ZettUserAdObject>();
            ArrayOfZettUserAdObject arrayOfZettUserAdObject = new ArrayOfZettUserAdObject();

            ZettUserAdObject zettUserAdObject = objectFactory.createZettUserAdObject();
            zettUserAdObject.setAdObjectId(adId);
            zettUserAdObject.setObjectId(zettAdObject.getCompany().getValue().getObjectId());
            zettUserAdObject.setTitle(objectFactory.createZettUserAdObjectTitle(zettAdObject.getTitle().getValue()));

            arrayOfZettUserAdObject.getZettUserAdObject().add(zettUserAdObject);
            zettUserAdObjectList.add(zettUserAdObject);
            zettUser.setUserAds(objectFactory.createZettUserUserAds(arrayOfZettUserAdObject));



        } else {

            List<ZettUserAdObject> zettUserAdObjectList = zettUser.getUserAds().getValue().getZettUserAdObject();
            ArrayOfZettUserAdObject arrayOfZettUserAdObject = zettUser.getUserAds().getValue();

            boolean found = false;
            for (ZettUserAdObject zettUserAdObject : zettUserAdObjectList) {
                if (zettUserAdObject.getAdObjectId().equals(adId)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                ObjectFactory objectFactory = new ObjectFactory();
                ZettUserAdObject zettUserAdObject = objectFactory.createZettUserAdObject();
                zettUserAdObject.setAdObjectId(adId);
                zettUserAdObject.setObjectId(zettAdObject.getCompany().getValue().getObjectId());
                zettUserAdObject.setTitle(objectFactory.createZettUserAdObjectTitle(zettAdObject.getTitle().getValue()));

                arrayOfZettUserAdObject.getZettUserAdObject().add(zettUserAdObject);
                zettUser.setUserAds(objectFactory.createZettUserUserAds(arrayOfZettUserAdObject));

            }


        }


        return zettUser;
    }

*/
}
