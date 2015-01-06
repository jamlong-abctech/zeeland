package com.abctech.zeeland.webservice;

import com.abctech.mockland.runner.MocklandWebService;
import com.abctech.zeeland.wsAuthentication.WebserviceAuthentication;
import junit.framework.Assert;
import no.zett.model.enums.ContactAttributeType;
import no.zett.model.enums.ObjectAttributeType;
import no.zett.model.enums.UserType;
import no.zett.service.facade.AdObjectResponse;
import no.zett.service.facade.AdService;
import no.zett.service.facade.CompanyObjectResponse;
import no.zett.service.facade.CompanyService;
import no.zett.service.facade.SearchItemResponse;
import no.zett.service.facade.SearchItemService;
import no.zett.service.facade.ServiceException_Exception;
import no.zett.service.facade.UserResponse;
import no.zett.service.facade.UserService;
import no.zett.service.facade.ZettAdObject;
import no.zett.service.facade.ZettCompanyObject;
import no.zett.service.facade.ZettContact;
import no.zett.service.facade.ZettContactAttribute;
import no.zett.service.facade.ZettObjectAttribute;
import no.zett.service.facade.ZettObjectExternalReference;
import no.zett.service.facade.ZettSearchItem;
import no.zett.service.facade.ZettUserAdObject;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:junit/zeeland-appcontext.xml","classpath:zeeland-client-test.xml", "classpath:/META-INF/apiproperties-applicationContext.xml"})

public class WebServiceTest {

    private final Logger log = LoggerFactory.getLogger(WebServiceTest.class);

    @Autowired
    private AdService adService;

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private SearchItemService searchItemService;

    private MocklandWebService mocklandWebService;

    @Before
    public void before() throws Exception {
        mocklandWebService = new MocklandWebService();
        mocklandWebService.start();
      //  System.setProperty("mockland.ws", mocklandWebService.getBaseURI());
       System.setProperty("mockland.ws","http://localhost:9033/mockland-webservice");
//       System.setProperty("mockland.ws","http://services.zett.no");
    }

    @After
    public void after() throws Exception {
        mocklandWebService.stop();
    }

  @Ignore
    @Test
    public void showListAdTest() throws ServiceException_Exception {
        WebserviceAuthentication webserviceAuthentication = new WebserviceAuthentication();
        webserviceAuthentication.authentication(adService);
        AdObjectResponse adObjectResponse = adService.loadObjectsByCustomerEmail("eralfred@online.no");
        List<ZettAdObject> zettAdObjectList =  adObjectResponse.getZettAdObjects();
        log.debug(">>>>>>>>>>>>> Ad Mock <<<<<<<<<<<<<<<<");
        for(ZettAdObject z : zettAdObjectList) {
            log.debug("Ad id : " + z.getObjectId());
            log.debug("Title of ad : " + z.getTitle().trim());
            log.debug("Company id : " + z.getCompany().getObjectId());
            log.debug("Company name : " + z.getCompany().getTitle().trim());
            String importFormat = "";
            for(ZettObjectAttribute zettObjectAttribute : z.getCompany().getAttributes()) {
                if(zettObjectAttribute.getLabel().equals("ImportFormat")) {
                    importFormat = zettObjectAttribute.getValue();
                }
            }
            log.debug("Import format " + importFormat);

            for(ZettObjectAttribute zettObjectAttributes : z.getCompany().getAttributes()) {
                log.debug("Attribute type : " + ObjectAttributeType.fromInteger(zettObjectAttributes.getType()));
            }
            for(ZettObjectExternalReference zettObjectExternalReference : z.getExternalReferences()) {
                log.debug("External referance id :" + zettObjectExternalReference.getExternalReferenceId());
            }
            log.debug("Publiching status :" + z.getPublishingStatus());
            log.debug("========================================");
        }
    }

    //@Ignore
    @Test
    public void showListUserTest() throws ServiceException_Exception {
        WebserviceAuthentication webserviceAuthentication = new WebserviceAuthentication();
        webserviceAuthentication.authentication(userService);
        UserResponse userResponse = userService.loadUserByEmail("an-romb@online.no");     // an-romb@online.no
        log.debug(">>>>>>>>>>>>> User Mock <<<<<<<<<<<<<<<<");
        log.debug("User id : " + userResponse.getZettUser().getUserId());
        log.debug("User name : " + userResponse.getZettUser().getName());
        log.debug("User email : " +userResponse.getZettUser().getEmail());
        log.debug("User type : " + UserType.fromInteger(userResponse.getZettUser().getUserType()));
//            log.debug("User telephone : " + z.getPhone().getValue());
//            log.debug("User mobile : " + z.getMobile().getValue());
//            log.debug("User fax : " + z.getFax().getValue());
//        log.debug("User address : " + userResponse.getZettUser().getValue().getAddress().getValue().getAddressId());
//        log.debug("User address : " + userResponse.getZettUser().getValue().getAddress().getValue().getGeography().getValue());
//        log.debug("User address : " + userResponse.getZettUser().getValue().getAddress().getValue().getPostCode().getValue());
//        log.debug("User address : " + userResponse.getZettUser().getValue().getAddress().getValue().getPostLocation().getValue());
//        log.debug("User address : " + userResponse.getZettUser().getValue().getAddress().getValue().getPrimaryAddress().getValue());
//            log.debug("User company id  : " + z.getUserType());
//            log.debug("User company title  : " + z.getUserType());
//            log.debug("User company access  : " + z.getUserType());
        log.debug("Fraud status : " + userResponse.getZettUser().getFraudstatus().value());
        log.debug("User status : " + userResponse.getZettUser().getStatus());
        log.debug("Register : " +  userResponse.getZettUser().getRegisteredTime().toString());
        log.debug("Modified : " +  userResponse.getZettUser().getModifiedTime().toString());
        List<ZettUserAdObject> zettUserAdObjectList = userResponse.getZettUser().getUserAds() ;

        for(ZettUserAdObject zettUserAdObject : zettUserAdObjectList){
            log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> : " + zettUserAdObject.getAdObjectId());


        }

        /*for(ZettUserAdObject zettUserAdObject : userResponse.getZettUser().getValue().getUserAds()){

        }*/
        DateTime current = new DateTime(userResponse.getZettUser().getModifiedTime().getYear(),userResponse.getZettUser().getModifiedTime().getMonth(), userResponse.getZettUser().getModifiedTime().getDay(), userResponse.getZettUser().getModifiedTime().getHour(), userResponse.getZettUser().getModifiedTime().getMinute(), userResponse.getZettUser().getModifiedTime().getSecond(), userResponse.getZettUser().getModifiedTime().getMillisecond());
        log.debug("Format Time  " + current.toString("EEE MMM dd kk:mm:ss 'CEST' y"));
//            log.debug("User Access : " +  z.getUseraccess().getValue().getUseraccessrights().getValue().getInt().get(0));
        log.debug("Password : " +  userResponse.getZettUser().getPassword());
        for(ZettUserAdObject zettUserAdObject: userResponse.getZettUser().getUserAds()) {
            log.debug("Ad :" + zettUserAdObject.getObjectId());
        }
        log.debug("========================================");

//
//        JAXBElement<ArrayOfZettUser> arrayOfZettUser = userResponse.getZettUsers();
//        List<ZettUser> zettUserList = arrayOfZettUser.getValue().getZettUser();
//        log.debug(">>>>>>>>>>>>> User Mock <<<<<<<<<<<<<<<<");
//        for(ZettUser z : zettUserList) {
//            log.debug("User id : " + z.getUserId());
//            log.debug("User name : " + z.getName().getValue());
//            log.debug("User email : " +z.getEmail().getValue());
//            log.debug("User type : " + UserType.fromInteger(z.getUserType()));
////            log.debug("User telephone : " + z.getPhone().getValue());
////            log.debug("User mobile : " + z.getMobile().getValue());
////            log.debug("User fax : " + z.getFax().getValue());
//            log.debug("User address : " + z.getAddress().getValue().getAddressId());
//            log.debug("User address : " + z.getAddress().getValue().getGeography().getValue());
//            log.debug("User address : " + z.getAddress().getValue().getPostCode().getValue());
//            log.debug("User address : " + z.getAddress().getValue().getPostLocation().getValue());
//            log.debug("User address : " + z.getAddress().getValue().getPrimaryAddress().getValue());
////            log.debug("User company id  : " + z.getUserType());
////            log.debug("User company title  : " + z.getUserType());
////            log.debug("User company access  : " + z.getUserType());
//            log.debug("Fraud status : " + z.getFraudstatus().getValue().value());
//            log.debug("User status : " + z.getStatus());
//            log.debug("Register : " +  z.getRegisteredTime().toString());
//            log.debug("Modified : " +  z.getModifiedTime().toString());
//            DateTime current = new DateTime(z.getModifiedTime().getYear(),z.getModifiedTime().getMonth(), z.getModifiedTime().getDay(), z.getModifiedTime().getHour(), z.getModifiedTime().getMinute(), z.getModifiedTime().getSecond(), z.getModifiedTime().getMillisecond());
//            log.debug("Format Time  " + current.toString("EEE MMM dd kk:mm:ss 'CEST' y"));
////            log.debug("User Access : " +  z.getUseraccess().getValue().getUseraccessrights().getValue().getInt().get(0));
//            log.debug("Password : " +  z.getPassword().getValue());
//            for(ZettUserAdObject zettUserAdObject: z.getUserAds().getValue().getZettUserAdObject()) {
//                log.debug("Ad :" + zettUserAdObject.getObjectId().toString());
//            }
//            log.debug("========================================");
//        }
    }

   // @Ignore
    @Test
    public void showListCompanyTest() throws ServiceException_Exception {
        WebserviceAuthentication webserviceAuthentication = new WebserviceAuthentication();
        webserviceAuthentication.authentication(companyService);
        CompanyObjectResponse companyObjectResponse = companyService.loadCompany(10021);
        ZettCompanyObject zettCompanyObject = companyObjectResponse.getZettCompanyObject();
         CompanyObjectResponse companyObjectResponsemap = companyService.mapLookupCompany(zettCompanyObject) ;

        log.debug(">>>" +  companyObjectResponsemap);

        String mobile = "75 11 36 30"      ;
        String title = "title" ;
        String telefon = "75 11 36 00" ;
        log.debug(">>>>>>>>>>>>> Company Mock <<<<<<<<<<<<<<<<");
        log.debug("Company Logo : " + companyObjectResponse.getZettCompanyObject().getMedia().get(0).getReference());
        log.debug("Company Id : " + companyObjectResponse.getZettCompanyObject().getObjectId());
        log.debug("Company Title : " + companyObjectResponse.getZettCompanyObject().getTitle());
        log.debug("Company Status : " + companyObjectResponse.getZettCompanyObject().getStatus());
        log.debug("Category : " + companyObjectResponse.getZettCompanyObject().getCategory());
        log.debug("Sale package : " + companyObjectResponse.getZettCompanyObject().getPackageDeal().getPackageDealtype());
        log.debug("Category : " + companyObjectResponse.getZettCompanyObject().getCategory());
        for(ZettObjectAttribute zettObjectAttribute : companyObjectResponse.getZettCompanyObject().getAttributes()) {
            if(zettObjectAttribute.getName().equals("homepage")) {
                log.debug("Homepage : " + zettObjectAttribute.getValue());
            }
        }
        log.debug("Primary Address : " + companyObjectResponse.getZettCompanyObject().getAddress().getPrimaryAddress());
        log.debug("Second Address : " + companyObjectResponse.getZettCompanyObject().getAddress().getSecondaryAddress());
        log.debug("Postcode : " + companyObjectResponse.getZettCompanyObject().getAddress().getPostCode());
        log.debug("City, PostLocation : " + companyObjectResponse.getZettCompanyObject().getAddress().getPostLocation());
        log.debug("Geography : " + companyObjectResponse.getZettCompanyObject().getAddress().getGeography());
        log.debug("Create Time : " + companyObjectResponse.getZettCompanyObject().getCreatedTime().getMillisecond());
        log.debug("Modified Time : " + companyObjectResponse.getZettCompanyObject().getModifiedTime().getMillisecond());
        for(ZettObjectAttribute zettObjectAttribute : companyObjectResponse.getZettCompanyObject().getAttributes()) {
            if(zettObjectAttribute.getName().equals("importformat")) {
                log.debug("Import Format : " + zettObjectAttribute.getValue());
            }
        }
        for(ZettContact zettContact : companyObjectResponse.getZettCompanyObject().getContacts()) {
            log.debug("Contact name : " + zettContact.getName());
            log.debug("Contact email : " + zettContact.getEmail());
            for(ZettContactAttribute zettContactAttribute : zettContact.getAttributesRaw()) {
                if(zettContactAttribute.getType()== 1) {
                    org.junit.Assert.assertEquals(telefon ,zettContactAttribute.getValue());

                }if(zettContactAttribute.getType()==3){
                    Assert.assertEquals(mobile, zettContactAttribute.getValue());
                }if(zettContactAttribute.getType()== ContactAttributeType.TITLE.toInteger()){
                    Assert.assertEquals(title , zettContactAttribute.getValue());

                }
            }



        }
        for(ZettObjectAttribute zettObjectAttribute : companyObjectResponse.getZettCompanyObject().getAttributes()) {
            if(zettObjectAttribute.getName().equals("mappreviewurl")) {
                log.debug("Map : " + zettObjectAttribute.getValue());
            }
        }
        log.debug("========================================");
    }

    @Ignore
    @Test
    public void showListSearchItemTest() throws ServiceException_Exception {
        WebserviceAuthentication webserviceAuthentication = new WebserviceAuthentication();
        webserviceAuthentication.authentication(searchItemService);
        SearchItemResponse searchItemResponse = searchItemService.titleSearchCompany("Ringerikes Blad",null,0);
        log.debug(">>>>>>>>>>>>> SearchItem Mock <<<<<<<<<<<<<<<<");
        for(ZettSearchItem zettSearchItem : searchItemResponse.getZettSearchItems()) {
            log.debug("Title : " + zettSearchItem.getValues().get(0));
            log.debug("Id : " + zettSearchItem.getValues().get(1));

        }
        log.debug("========================================");
    }


    @Test
    public void  mapServiceTest() throws ServiceException_Exception{


        WebserviceAuthentication webserviceAuthentication = new WebserviceAuthentication();
        webserviceAuthentication.authentication(companyService);
        CompanyObjectResponse companyObjectResponse = companyService.loadCompany(10021);
        ZettCompanyObject zettCompanyObject = companyObjectResponse.getZettCompanyObject();

        org.junit.Assert.assertNotNull(zettCompanyObject);


        List<ZettObjectAttribute> zettObjectAttributeList = zettCompanyObject.getAttributes();

        for(ZettObjectAttribute attribute : zettObjectAttributeList){
            log.debug("########## For Loop ###########");
            if(attribute.getName().equals("mappreviewurl")) {
                  log.debug("Map>>>>>>>>>>> : " + attribute.getValue());
                  break;
            }
        }

          log.debug("========================================");

        }






        /*Assert.assertNotNull("Expecting to be able to read the company for which I have created test data.",companyObjectResponse);
        Assert.assertNotNull("Expecting to be able to read the value for which I have created test data." , companyObjectResponsemap.getZettCompanyObject().getValue());

            Assert.assertNotNull("Expecting to be able to read the value for which I have created test data.", companyObjectResponsemap.getStatusCode().toString());
            Assert.assertNotNull("Expecting to be able to read the value for which I have created test data.", companyObjectResponsemap.getZettCompanyObject().getValue());
            Assert.assertNotNull("Expecting to be able to read the value for which I have created test data." , companyObjectResponse.getSystemException().getValue());
*/
    }





